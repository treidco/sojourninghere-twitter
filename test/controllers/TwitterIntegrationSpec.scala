package controllers

import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._

import play.api.test._
import play.api.test.Helpers._

class TwitterIntegrationSpec extends Specification {

  "Application" should {
    "run in a  browser" in {
      running(TestServer(3333), HTMLUNIT) {
        browser =>
          browser.goTo("http://localhost:3333/")
          browser.$("#title").getTexts().get(0) must equalTo("Tweets")
      }
    }
  }

}
