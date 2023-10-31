import org.gradle.api.JavaVersion
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.tasks.scala.ScalaCompile
import org.gradle.kotlin.dsl.*

val vs = versions()

plugins {
//    base
    java
    `java-test-fixtures`

    idea

    id("com.github.ben-manes.versions" )
}

val rootID = vs.projectRootID

idea {

    targetVersion = "2020"

    module {

        excludeDirs = excludeDirs + listOf(
            file(".gradle"),
            file(".github"),

            file ("target"),
//                        file ("out"),

            file(".idea"),
            file(".vscode"),
            file(".bloop"),
            file(".bsp"),
            file(".metals"),
            file(".ammonite"),

            file("logs"),

            file("lightweight-dependency")
        )

        isDownloadJavadoc = true
        isDownloadSources = true
    }
}

allprojects {

    apply(plugin = "java")
    apply(plugin = "java-library")
    apply(plugin = "java-test-fixtures")

    // apply(plugin = "bloop")
    // DO NOT enable! In VSCode it will cause the conflict:
    // Cannot add extension with name 'bloop', as there is an extension already registered with that name

    apply(plugin = "idea")

    group = vs.projectGroup
    version = vs.projectV

    repositories {
        mavenLocal()
        mavenCentral()
//        jcenter()
        maven("https://dl.bintray.com/kotlin/kotlin-dev")
    }

    task("dependencyTree") {

        dependsOn("dependencies")
    }

    java {
        withSourcesJar()
        withJavadocJar()
    }
}