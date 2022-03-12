package org.tribbloid.scaffold

import org.scalatest.BeforeAndAfterAll
import org.tribbloid.scaffold.js.JSLinker

class HelloSpec extends BaseSpec with BeforeAndAfterAll {

  it("test1") {
    JSLinker.linkOnce
  }
}
