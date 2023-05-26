import org.gradle.api.Project

class Versions(private val self: Project) {

    // TODO : how to group them?
    val projectGroup = "ai.acyclic"
    val projectRootID = "scaffold"

    val projectV = "0.1.0-SNAPSHOT"
    val projectVMajor = projectV.removeSuffix("-SNAPSHOT")
//    val projectVComposition = projectV.split('-')

    inner class Scala {
        val group: String = self.properties["scalaGroup"].toString()

        val v: String = self.properties["scalaVersion"].toString()
        protected val vParts: List<String> = v.split('.')

        val binaryV: String = vParts.subList(0, 2).joinToString(".")
        val minorV: String = vParts[2]
    }
    val scala = Scala()

    val scalaTestV = "3.2.12"
    val splainV: String = self.properties["splainVersion"]?.toString() ?: ""

    val scalajsV: String? = self.properties.get("scalaJSVersion")?.toString()
}
