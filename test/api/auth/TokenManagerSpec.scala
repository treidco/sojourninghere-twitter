package api.auth

import org.specs2.mock.Mockito
import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._

import play.api.test._
import api.http.ClientWrapper

@RunWith(classOf[JUnitRunner])
class TokenManagerSpec extends Specification with Mockito {

  "TokenManager" should {

    val creds = mock[Credentials]
    val client = mock[ClientWrapper]
    val token = new TokenManager(creds, client)

    "base64 encode credentials" in new WithApplication {
      creds.retrieve returns "user:pass"
      token.base64EncodedCredentials must beEqualTo("dXNlcjpwYXNz")
    }

    "retrieve token" in new WithApplication {
      client.execute returns
    }

    "invalidate token" in new WithApplication {
      token.invalidateToken
    }

  }
}
