package auth
import org.specs2.mock.Mockito
import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._

import play.api.test._

@RunWith(classOf[JUnitRunner])
class TokenSpec extends Specification with Mockito {

  "Token" should {

    "base64 encode credentials" in new WithApplication {
      val creds = mock[Credentials]
      creds.retrieve returns "user:pass"

      val token = new Token(creds)
      token.base64EncodedCredentials must beEqualTo("dXNlcjpwYXNz")
    }

  }
}
