buildscript {
    repositories {
        // Add here whatever repositories you're already using
        mavenCentral()
    }

    dependencies {
        classpath("ch.epfl.scala:gradle-bloop_2.12:1.4.13") // suffix is always 2.12, weird
    }
}


apply(plugin = "ai.acyclic.java-conventions")
apply(plugin = "ai.acyclic.scala-conventions")
apply(plugin = "ai.acyclic.publish-conventions")