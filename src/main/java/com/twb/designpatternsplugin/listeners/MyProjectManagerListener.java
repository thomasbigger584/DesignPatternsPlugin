package com.twb.designpatternsplugin.listeners;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManagerListener;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.jetbrains.annotations.NotNull;

public class MyProjectManagerListener implements ProjectManagerListener {

    @Override
    public void projectOpened(@NotNull Project project) {
        System.out.println("project = " + ReflectionToStringBuilder.toString(project));

    }
}
