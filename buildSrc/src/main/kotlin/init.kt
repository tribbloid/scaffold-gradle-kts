import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.*

import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.plugins.ide.idea.IdeaPlugin

import org.gradle.plugin.use.PluginDependenciesSpec

/**
 * Configures the current project as a Kotlin project by adding the Kotlin `stdlib` as a dependency.
 */
fun Project.versions(): Versions {

    return Versions(this)
}

//fun Project.dummy2(): Unit {
//
//
//    apply(plugin = "java")
//    apply(plugin = "java-library")
//    apply(plugin = "java-test-fixtures")
//
//    // apply(plugin = "bloop")
//    // DO NOT enable! In VSCode it will cause the conflict:
//    // Cannot add extension with name 'bloop', as there is an extension already registered with that name
//
//    apply(plugin = "scala")
//    apply(plugin = "kotlin")
//
//    apply(plugin = "idea")
//
//    apply(plugin = "maven-publish")
//
//    dependencies {
//        implementation("")
//    }
//
//}
//
//
//fun PluginDependenciesSpec.dummy3(): Unit {
//
//    idea.apply {
//
//    }
//}