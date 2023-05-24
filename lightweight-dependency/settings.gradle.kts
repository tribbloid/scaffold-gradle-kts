//val versions = gradle.rootProject.versions()


include(":lightweight-dependency")
project(":lightweight-dependency").projectDir = file("module")
include(":lightweight-dependency:core")
include(":lightweight-dependency:extra")

pluginManagement.repositories {
    gradlePluginPortal()
    mavenCentral()
    // maven("https://dl.bintray.com/kotlin/kotlin-dev")
}
