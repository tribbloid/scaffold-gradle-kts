import org.gradle.api.Project
import org.gradle.kotlin.dsl.KotlinBuildScript

/**
 * Configures the current project as a Kotlin project by adding the Kotlin `stdlib` as a dependency.
 */
fun Project.versions(): Versions {

    return Versions(this)
}


fun KotlinBuildScript.dummy(): Unit {}