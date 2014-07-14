package api.auth

import org.specs2.mock.Mockito
import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._

import play.api.test._
import api.http.MockClientWrapper

@RunWith(classOf[JUnitRunner])
class TokenManagerSpec extends Specification with Mockito {

  "TokenManager" should {

    "base64 encode credentials" in new WithApplication {
      val creds = mock[Credentials]
      val client = new MockClientWrapper
      val tokenManager = new TokenManager(creds, client)

      creds.retrieve returns "user:pass"
      tokenManager.base64EncodedCredentials must beEqualTo("dXNlcjpwYXNz")
    }

    "retrieve token" in new WithApplication {
      val creds = mock[Credentials]
      val client = new MockClientWrapper
      val tokenManager = new TokenManager(creds, client)

      creds.retrieve returns "user:pass"
      client.body = "{\"token_type\":\"bearer\",\"access_token\":\"valid_token\"}"


      val mockResponseBody = (x: String) => "valid_token"
      tokenManager.retrieveToken(mockResponseBody) must beEqualTo("valid_token")
    }

//        "invalidate token" in new WithApplication {
//          tokenManager.invalidateToken
//        }

  }
}
