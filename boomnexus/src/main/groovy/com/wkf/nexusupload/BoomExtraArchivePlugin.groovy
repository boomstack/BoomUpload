package com.wkf.nexusupload

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.plugins.BasePlugin
import org.gradle.api.plugins.GroovyPlugin
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.tasks.bundling.Jar

class BoomExtraArchivePlugin implements Plugin<Project> {
    static final String JAR_TASK_GROUP = BasePlugin.BUILD_GROUP
    static final String SOURCES_JAR_TASK_NAME = 'sourcesJar'
    static final String TESTS_JAR_TASK_NAME = 'testsJar'
    static final String JAVADOC_JAR_TASK_NAME = 'javadocJar'

    @Override
    void apply(Project project) {
        BoomExtraArchivePluginExtension extension = project.extensions.create('extraArchive', BoomExtraArchivePluginExtension)
        configureTasks(project, extension)
    }

    private void configureTasks(Project project, BoomExtraArchivePluginExtension extension) {
        project.afterEvaluate {
            project.plugins.withType(JavaPlugin) {
                configureSourcesJarTask(project, extension)
                configureTestsJarTask(project, extension)
                configureJavadocJarTask(project, extension)
            }
        }
    }

    private void configureSourcesJarTask(Project project, BoomExtraArchivePluginExtension extension) {
        if(extension.sources) {
            project.task(SOURCES_JAR_TASK_NAME, type: Jar) {
                classifier = 'sources'
                group = JAR_TASK_GROUP
                description = 'Assembles a jar archive containing the main sources of this project.'
                from project.sourceSets.main.allSource
            }
        }
    }

    private void configureTestsJarTask(Project project, BoomExtraArchivePluginExtension extension) {
        if(extension.tests) {
            project.task(TESTS_JAR_TASK_NAME, type: Jar) {
                classifier = 'tests'
                group = JAR_TASK_GROUP
                description = 'Assembles a jar archive containing the test sources of this project.'
                from project.sourceSets.test.output
            }
        }
    }

    private void configureJavadocJarTask(Project project, BoomExtraArchivePluginExtension extension) {
        if(extension.javadoc) {
            project.task(JAVADOC_JAR_TASK_NAME, type: Jar) {
                classifier = 'javadoc'
                group = JAR_TASK_GROUP
                description = 'Assembles a jar archive containing the generated Javadoc API documentation of this project.'
                from getDocTask(project)
            }
        }
    }

    /**
     * Checks to see if Groovy plugin got applied to project.
     *
     * @param project Project
     * @return Flag
     */
    private boolean hasGroovyPlugin(Project project) {
        hasPlugin(project, GroovyPlugin)
    }

    private boolean hasPlugin(Project project, Class<? extends Plugin> pluginClass) {
        project.plugins.hasPlugin(pluginClass)
    }

    private Task getDocTask(Project project) {
        hasGroovyPlugin(project) ? project.tasks.getByName(GroovyPlugin.GROOVYDOC_TASK_NAME) : project.tasks.getByName(JavaPlugin.JAVADOC_TASK_NAME)
    }
}
