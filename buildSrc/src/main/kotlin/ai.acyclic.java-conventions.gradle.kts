import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import org.gradle.api.JavaVersion
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.tasks.scala.ScalaCompile
import org.gradle.kotlin.dsl.*

plugins {
//    base
    java
    `java-test-fixtures`

    idea

    id("com.github.ben-manes.versions" )
}

val vs = versions()
val rootID = vs.projectRootID

// TODO: remove after https://github.com/ben-manes/gradle-versions-plugin/issues/816 resolved
tasks.named<DependencyUpdatesTask>("dependencyUpdates").configure {
    filterConfigurations = Spec<Configuration> {
        !it.name.startsWith("incrementalScalaAnalysis")
    }
}

idea {

    targetVersion = "2023"

    module {

        excludeDirs = excludeDirs + listOf(
            file(".gradle"),
            file(".github"),

            file ("target"),
//                        file ("out"),

            file(".idea"),
            file(".vscode"),

            file("logs")
        )

        isDownloadJavadoc = true
        isDownloadSources = true
    }
}

allprojects {

    apply(plugin = "java")
    apply(plugin = "java-library")
    apply(plugin = "java-test-fixtures")


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

    val jvmTarget = JavaVersion.VERSION_1_8

    java {

        withSourcesJar()
        withJavadocJar()

        sourceCompatibility = jvmTarget
        targetCompatibility = jvmTarget
    }
}