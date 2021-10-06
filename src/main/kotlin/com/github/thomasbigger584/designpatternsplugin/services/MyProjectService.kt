package com.github.thomasbigger584.designpatternsplugin.services

import com.intellij.openapi.project.Project
import com.github.thomasbigger584.designpatternsplugin.MyBundle

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
