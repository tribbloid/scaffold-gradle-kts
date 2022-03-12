package org.tribbloid.scaffold.js

import org.scalajs.linker.interface.{ModuleInitializer, Report, StandardConfig}
import org.scalajs.linker.{PathIRContainer, PathOutputDirectory, StandardImpl}
import org.scalajs.logging.{Level, ScalaConsoleLogger}

import java.nio.file.{Files, Path, Paths}
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, ExecutionContextExecutor}

object JSLinker {

  implicit def gec: ExecutionContextExecutor = ExecutionContext.global

  val logger = new ScalaConsoleLogger(Level.Info) // TODO: cannot be lazy val, why?

  lazy val linkerConf: StandardConfig = {
    StandardConfig()
  } // look at the API of this, lots of options.

  def link(classpath: Seq[Path], outputDir: Path): Report = {
    val linker = StandardImpl.linker(linkerConf)

    // Same as scalaJSModuleInitializers in sbt, add if needed.
    val moduleInitializers = Seq(
      ModuleInitializer.mainMethodWithArgs(SlinkyHelloWorld.getClass.getName.stripSuffix("$"), "main")
    )

    Files.createDirectories(outputDir)

    val cache = StandardImpl.irFileCache().newCache
    val result = PathIRContainer
      .fromClasspath(classpath)
      .map(_._1)
      .flatMap(cache.cached _)
      .flatMap { v =>
        linker.link(v, moduleInitializers, PathOutputDirectory(outputDir), logger)
      }

    Await.result(result, Duration.Inf)
  }

  def linkClasses(outputDir: Path = Paths.get("./ui/build/js")): Report = {

    val rList = getClassPaths

    link(rList, outputDir)
  }

  def getClassPaths: Seq[Path] = {

    val str = System.getProperty("java.class.path")
    val paths = str.split(':').map { v =>
      Paths.get(v)
    }

    val rr = Thread.currentThread().getContextClassLoader.getResources(".")

    paths

  }

  lazy val linkOnce: Report = {

    val report = linkClasses()
    logger.info(
      s"""
         |=== [Linked] ===
         |${report.toString()}
         |""".stripMargin
    )
    report
  }
}
