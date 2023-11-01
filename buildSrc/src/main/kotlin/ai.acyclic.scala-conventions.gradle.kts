import gradle.kotlin.dsl.accessors._4a36e2684fb0add6d136eaed132fc1f4.idea
import gradle.kotlin.dsl.accessors._4a36e2684fb0add6d136eaed132fc1f4.implementation
import gradle.kotlin.dsl.accessors._4a36e2684fb0add6d136eaed132fc1f4.testFixturesImplementation
import org.gradle.api.JavaVersion
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.tasks.scala.ScalaCompile
import org.gradle.kotlin.dsl.*


plugins {

    id("ai.acyclic.java-conventions")
    scala
    id("io.github.cosmicsilence.scalafix")
}

val vs = versions()

// see https://github.com/gradle/gradle/issues/13067
// TODO: how do I move it into upstream plugins?
fun DependencyHandler.bothImpl(dependencyNotation: Any): Unit {
    implementation(dependencyNotation)
    testFixturesImplementation(dependencyNotation)
}

allprojects {

    // apply(plugin = "bloop")
    // DO NOT enable! In VSCode it will cause the conflict:
    // Cannot add extension with name 'bloop', as there is an extension already registered with that name

    apply(plugin = "scala")

    dependencies {

        bothImpl("${vs.scala.group}:scala-library:${vs.scala.v}")
        bothImpl("${vs.scala.group}:scala-reflect:${vs.scala.v}")
//        bothImpl("${vs.scala.group}:scala-compiler:${vs.scala.v}") // enable if low-level mutli-stage programming is required

        testFixturesApi("org.scalatest:scalatest_${vs.scala.binaryV}:${vs.scalaTestV}")
        testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")
        testRuntimeOnly("co.helmethair:scalatest-junit-runner:0.2.0")
    }

    scala {

    }

    tasks {

        withType<ScalaCompile> {

            val jvmTarget = vs.jvmTarget.toString()

            sourceCompatibility = jvmTarget
            targetCompatibility = jvmTarget

            scalaCompileOptions.apply {

//                    isForce = true

                loggingLevel = "verbose"

                val compilerOptions = mutableListOf(
                    "-encoding", "UTF-8",

                    "-g:vars", // demand by json4s

                    "-deprecation",
                    "-unchecked",
                    "-feature",
                    "-language:higherKinds",
                    "-language:existentials",

                    "-Ywarn-value-discard",
                    "-Ywarn-unused:imports",
                    "-Ywarn-unused:implicits",
                    "-Ywarn-unused:params",
                    "-Ywarn-unused:patvars",

                    "-Xlint:poly-implicit-overload",
                    "-Xlint:option-implicit",
//                    ,
//                    "-Xlog-implicits",
//                    "-Xlog-implicit-conversions",
//                    "-Xlint:implicit-not-found",
//                    "-Xlint:implicit-recursion"

                )

                if (!vs.splainV.isEmpty()) {
                    compilerOptions.addAll(
                        listOf(
                            "-Vimplicits",
                            "-Vtype-diffs",
                            "-P:splain:tree",
                            "-P:splain:bounds:true",
                            "-P:splain:boundsimplicits:true"
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

        apply(plugin = "io.github.cosmicsilence.scalafix")
        scalafix {
            semanticdb.autoConfigure.set(true)
            semanticdb.version.set("4.8.11")
        }
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
}
