package org.tribbloid.scaffold.js

import slinky.web.ReactDOM

object SlinkyHelloWorld {

  import org.scalajs.dom
  import slinky.web.html._

  def main(args: Array[String]): Unit = {
    println("sample log message") // goes to the browser console
    System.err.println("sample error message") // goes to the browser console

    ReactDOM.render(
      div(
        h1("Hello, Slinky World!"),
        h2("(rendered by slinky)")
      ),
      dom.document.getElementById("root")
    )

  }
}
