package com.twb.designpatternsplugin.services.github;

public class GithubFile {
    public final String patternName;
    public final String fileName;
    public final byte[] content;
    public final long size;

    public GithubFile(String patternName, String fileName, byte[] bytes, long size) {
        this.patternName = patternName;
        this.fileName = fileName;
        this.content = bytes;
        this.size = size;
    }

    public String getPatternName() {
        return patternName;
    }

    public String getFileName() {
        return fileName;
    }

    public byte[] getContent() {
        return content;
    }

    public long getSize() {
        return size;
    }

    @Override
    public String toString() {
        return "GithubFile{" +
                "patternName='" + patternName + '\'' +
                ", fileName='" + fileName + '\'' +
                ", content.length=" + content.length +
                ", size=" + size +
                '}';
    }
}
