package auth

import org.junit.runner.RunWith
import org.specs2.runner.JUnitRunner
import org.specs2.mutable.Specification
import play.api.test.WithBrowser

@RunWith(classOf[JUnitRunner])
class TokenIntegrationSpec extends Specification {

  "Application" should {

    "work from within a browser" in new WithBrowser {

      browser.goTo("http://localhost:" + port)

      browser.pageSource must contain("Your new application is ready.")
    }
  }
}
