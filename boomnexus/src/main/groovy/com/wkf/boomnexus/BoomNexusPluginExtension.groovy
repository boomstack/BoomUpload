package com.wkf.boomnexus

import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration
import org.gradle.api.artifacts.Dependency
import org.gradle.api.plugins.MavenPlugin


class BoomNexusPluginExtension {
    String configuration = Dependency.ARCHIVES_CONFIGURATION
    Boolean sign = true
    String repositoryUrl = 'https://oss.sonatype.org/service/local/staging/deploy/maven2/'
    String snapshotRepositoryUrl = 'https://oss.sonatype.org/content/repositories/snapshots/'

    String getUploadTaskName() {
        "upload${configuration.capitalize()}"
    }

    String getUploadTaskPath(Project project) {
        isRootProject(project) ? ":$uploadTaskName" : "$project.path:$uploadTaskName"
    }

    String getInstallTaskPath(Project project) {
        isRootProject(project) ? ":$MavenPlugin.INSTALL_TASK_NAME" : "$project.path:$MavenPlugin.INSTALL_TASK_NAME"
    }

    private boolean isRootProject(Project project) {
        project.rootProject == project
    }

    void setConfiguration(config) {
        configuration = config instanceof Configuration ? config.name : config
    }

    boolean usesStandardConfiguration() {
        configuration == Dependency.ARCHIVES_CONFIGURATION
    }
}
