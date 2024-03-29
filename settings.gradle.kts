//val versions = gradle.rootProject.versions()


include(":lightweight-dependency")
project(":lightweight-dependency").projectDir = file("lightweight-dependency/module")
include(":lightweight-dependency:core")
include(":lightweight-dependency:extra")

include(":core")
include(":extra")

pluginManagement.repositories {
    gradlePluginPortal()
    mavenCentral()
    // maven("https://dl.bintray.com/kotlin/kotlin-dev")
}
