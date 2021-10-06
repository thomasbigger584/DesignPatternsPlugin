package com.twb.designpatternsplugin.actions;

import com.intellij.notification.NotificationGroupManager;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.twb.designpatternsplugin.services.github.GithubFile;
import com.twb.designpatternsplugin.tasks.DownloadBackgroundTask;
import com.twb.designpatternsplugin.tasks.callback.BasicEventCallback;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public class DownloadDesignPatternsAction extends AnAction implements BasicEventCallback {
    private static final String NOTIFICATION_GROUP = "Download Design Patterns";
    private Project project;
    private DownloadBackgroundTask downloadBackgroundTask;

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        this.project = event.getProject();
        downloadBackgroundTask = new DownloadBackgroundTask(project, this);
        downloadBackgroundTask.queue();
    }

    @Override
    public void onSuccess() {
        Map<String, List<GithubFile>> githubFiles = downloadBackgroundTask.getGithubFiles();
        String message = String.format("Downloaded %s design patterns", githubFiles.size());
        NotificationGroupManager.getInstance().getNotificationGroup(NOTIFICATION_GROUP)
                .createNotification(message, NotificationType.INFORMATION)
                .notify(project);
    }

    @Override
    public void onFailure(Throwable throwable) {
        NotificationGroupManager.getInstance().getNotificationGroup(NOTIFICATION_GROUP)
                .createNotification(throwable.getLocalizedMessage(), NotificationType.ERROR)
                .notify(project);
    }
}
