package auth

import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._

import play.api.test._

@RunWith(classOf[JUnitRunner])
class TokenSpec extends Specification {

  "Token" should {

    "base64 encode credentials" in new WithApplication {
      val creds = "user:pass"
      Token.base64EncodedCredentials must beEqualTo("dXNlcjpwYXNz")
    }

//    "credentials should be read" in {
//
//    }

  }
}
