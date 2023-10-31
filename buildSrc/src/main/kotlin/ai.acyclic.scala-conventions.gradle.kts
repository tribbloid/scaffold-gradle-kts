import gradle.kotlin.dsl.accessors._4a36e2684fb0add6d136eaed132fc1f4.idea
import org.gradle.api.JavaVersion
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.tasks.scala.ScalaCompile
import org.gradle.kotlin.dsl.*

val vs = versions()

plugins {

    id("ai.acyclic.java-conventions")
    scala
}


idea {

    module {

        excludeDirs = excludeDirs + listOf(
            file(".bloop"),
            file(".bsp"),
            file(".metals"),
            file(".ammonite"),
        )
    }
}

allprojects {

    // apply(plugin = "bloop")
    // DO NOT enable! In VSCode it will cause the conflict:
    // Cannot add extension with name 'bloop', as there is an extension already registered with that name

    apply(plugin = "scala")

    dependencies {

        // see https://github.com/gradle/gradle/issues/13067
        fun bothImpl(constraintNotation: Any) {
            implementation(constraintNotation)
            testFixturesImplementation(constraintNotation)
        }

        constraints {

            bothImpl("${vs.scala.group}:scala-compiler:${vs.scala.v}")
        }

        testFixturesApi("org.scalatest:scalatest_${vs.scala.binaryV}:${vs.scalaTestV}")
        testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")
    }

    val jvmTarget = JavaVersion.VERSION_1_8.toString()

    tasks {

        withType<ScalaCompile> {

            targetCompatibility = jvmTarget

            scalaCompileOptions.apply {

//                    isForce = true

                loggingLevel = "verbose"

                val compilerOptions = mutableListOf(
                    "-encoding", "UTF-8",
                    "-unchecked",
                    "-deprecation",
                    "-feature",

                    "-language:higherKinds",
//                            "-Xfatal-warnings",

                    "-Xlint:poly-implicit-overload",
                    "-Xlint:option-implicit",

//                        "-Ydebug",
                    "-Yissue-debug"
//                    ,
//                    "-Ytyper-debug",
//                    "-Vtyper"

//                    ,
//                    "-Xlog-implicits",
//                    "-Xlog-implicit-conversions",
//                    "-Xlint:implicit-not-found",
//                    "-Xlint:implicit-recursion"

                )

                if (!vs.splainV.isEmpty()) {
                    compilerOptions.addAll(
                        listOf(
                            //splain
                            "-P:splain:tree",
                            "-P:splain:breakinfix:200",
                            "-P:splain:bounds:true",
                            "-P:splain:boundsimplicits:true",
                            "-P:splain:keepmodules:2"
                        )
                    )
                }

                additionalParameters = compilerOptions

                forkOptions.apply {

                    memoryInitialSize = "1g"
                    memoryMaximumSize = "4g"

                    // this may be over the top but the test code in macro & core frequently run implicit search on church encoded Nat type
                    jvmArgs = listOf(
                        "-Xss256m"
                    )
                }
            }
        }

        test {

            minHeapSize = "1024m"
            maxHeapSize = "4096m"

            useJUnitPlatform {
                includeEngines("scalatest")
                testLogging {
                    events("passed", "skipped", "failed")
                }
            }

            testLogging {
//                events = setOf(org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED, org.gradle.api.tasks.testing.logging.TestLogEvent.PASSED, org.gradle.api.tasks.testing.logging.TestLogEvent.SKIPPED, org.gradle.api.tasks.testing.logging.TestLogEvent.STANDARD_OUT)
//                exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
                showExceptions = true
                showCauses = true
                showStackTraces = true

                // stdout is used for occasional manual verification
                showStandardStreams = true
            }
        }
    }
}