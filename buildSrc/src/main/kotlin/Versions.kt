import org.gradle.api.Project

class Versions(self: Project) {

    // TODO : how to group them?
    val projectGroup = "org.shapesafe"
    val projectRootID = "shapesafe"

    val projectV = "0.1.0-SNAPSHOT"
    val projectVBody = projectV.removeSuffix("-SNAPSHOT")
//    val projectVComposition = projectV.split('-')

    val scalaGroup: String = self.properties.get("scalaGroup").toString()

    val scalaV: String = self.properties.get("scalaVersion").toString()

    protected val scalaVParts = scalaV.split('.')

    val scalaBinaryV: String = scalaVParts.subList(0, 2).joinToString(".")
    val scalaMinorV: String = scalaVParts[2]

    val scalatestV: String = "3.2.3"

    val splainV: String? = self.properties.get("splainVersion")?.toString()

    val scalaJSV: String = "1.10.0"
    val scalaJSVParts = scalaJSV.split('.')

    val scalaJSVMajor: String = scalaJSVParts[0]
    val scalaJSSuffix: String = "sjs${scalaJSVMajor}_${scalaBinaryV}"

}
