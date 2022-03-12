package org.tribbloid.scaffold.js

import org.scalajs.linker.interface.{Report, StandardConfig}
import org.scalajs.linker.{PathIRContainer, PathOutputDirectory, StandardImpl}
import org.scalajs.logging.{Level, ScalaConsoleLogger}

import java.nio.file.{Path, Paths}
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext}

object JSLinker {

  implicit def gec = ExecutionContext.global

  def link(classpath: Seq[Path], outputDir: Path): Report = {
    val logger = new ScalaConsoleLogger(Level.Warn)
    val linkerConfig = StandardConfig() // look at the API of this, lots of options.
    val linker = StandardImpl.linker(linkerConfig)

    // Same as scalaJSModuleInitializers in sbt, add if needed.
    val moduleInitializers = Seq()

    val cache = StandardImpl.irFileCache().newCache
    val result = PathIRContainer
      .fromClasspath(classpath)
      .map(_._1)
      .flatMap(cache.cached _)
      .flatMap(linker.link(_, moduleInitializers, PathOutputDirectory(outputDir), logger))

    Await.result(result, Duration.Inf)
  }

  def linkClasses(outputDir: Path = Paths.get("./")): Report = {

    val rList = getClassPaths()

    link(rList, outputDir)
  }

  def getClassPaths(): Seq[Path] = {

    val str = System.getProperty("java.class.path")
    val paths = str.split(':').map { v =>
      Paths.get(v)
    }

    paths
  }

  lazy val linkOnce = {

    linkClasses()
  }
}
