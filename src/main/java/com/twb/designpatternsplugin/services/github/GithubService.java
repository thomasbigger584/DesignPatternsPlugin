package com.twb.designpatternsplugin.services.github;

import com.twb.designpatternsplugin.services.github.retrofit.BaseHttpService;
import com.twb.designpatternsplugin.services.github.retrofit.GitHubAPI;
import okhttp3.ResponseBody;
import org.apache.commons.io.IOUtils;
import retrofit2.Response;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class GithubService extends BaseHttpService {
    private static final String BASE_GITHUB_ENDPOINT = "https://codeload.github.com";

    public Map<String, List<GithubFile>> getGithubFiles() throws IOException {
        GitHubAPI gitHubAPI = getApi(BASE_GITHUB_ENDPOINT, GitHubAPI.class);
        Response<ResponseBody> response = gitHubAPI.downloadLatestZip().execute();

        if (response.isSuccessful()) {
            ResponseBody body = response.body();
            if (body == null) {
                throw new IOException("Successful but body is null");
            }
            return processBody(body);
        }
        throw new IOException("Error occurred fetching zip file: " + response.toString());
    }

    private Map<String, List<GithubFile>> processBody(ResponseBody body) throws IOException {
        Map<String, List<GithubFile>> patternMap = new HashMap<>();
        try (ZipInputStream zis = new ZipInputStream(body.byteStream())) {

            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                if (shouldSkipEntry(entry)) {
                    continue;
                }
                int length = (int) entry.getSize() - 1;
                ByteArrayOutputStream os = new ByteArrayOutputStream(length);
                IOUtils.copyLarge(zis, os, 0, length, new byte[length]);

                byte[] bytes = os.toByteArray();
                os.close();

                String entryName = entry.getName();
                String name = entryName.replace("java-design-patterns-master/", "");
                String[] nameSplit = name.split("/");
                String patternName = nameSplit[0];
                String fileName = nameSplit[nameSplit.length - 1];

                GithubFile githubFile = new GithubFile(patternName, fileName, bytes, entry.getSize());
                if (patternMap.containsKey(patternName)) {
                    patternMap.get(patternName).add(githubFile);
                } else {
                    List<GithubFile> githubFileList = new ArrayList<>();
                    githubFileList.add(githubFile);
                    patternMap.put(patternName, githubFileList);
                }
            }
        }
        return patternMap;
    }

    private boolean shouldSkipEntry(ZipEntry entry) {
        Pattern pattern = Pattern.compile("java-design-patterns-master\\/.*\\/src" +
                "\\/main\\/java\\/com\\/iluwatar\\/.*\\.java", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(entry.getName());
        return entry.isDirectory() || !matcher.matches();
    }
}
