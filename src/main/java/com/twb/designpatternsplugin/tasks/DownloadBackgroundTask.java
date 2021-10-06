package com.twb.designpatternsplugin.tasks;

import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import com.twb.designpatternsplugin.services.github.GithubFile;
import com.twb.designpatternsplugin.services.github.GithubService;
import com.twb.designpatternsplugin.tasks.callback.BasicEventCallback;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class DownloadBackgroundTask extends Task.Backgroundable {
    private static final String DOWNLOADING_DESIGN_PATTERNS = "Downloading Design Patterns";
    private final BasicEventCallback callback;
    private Map<String, List<GithubFile>> githubFiles;
    private boolean finished;

    public DownloadBackgroundTask(@Nullable Project project, BasicEventCallback callback) {
        super(project, DOWNLOADING_DESIGN_PATTERNS, false);
        this.callback = callback;
    }

    @Override
    public void run(@NotNull ProgressIndicator indicator) {
        indicator.setText(DOWNLOADING_DESIGN_PATTERNS);
        indicator.setIndeterminate(true);

        GithubService githubService = new GithubService();
        try {
            githubFiles = githubService.getGithubFiles();
        } catch (IOException e) {
            onThrowable(e);
        }
    }

    @Override
    public void onSuccess() {
        callback.onSuccess();
    }

    @Override
    public void onThrowable(@NotNull Throwable error) {
        callback.onFailure(error);
    }


    public Map<String, List<GithubFile>> getGithubFiles() {
        return githubFiles;
    }
}
