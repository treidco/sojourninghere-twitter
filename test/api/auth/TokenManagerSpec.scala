package api.auth

import org.specs2.mock.Mockito
import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._

import play.api.test._
import api.http.ClientWrapper
import org.specs2.specification.Scope

@RunWith(classOf[JUnitRunner])
class TokenManagerSpec extends Specification with Mockito {

  "TokenManager" should {

    "base64 encode credentials" in new WithApplication {
      val creds = mock[Credentials]
      val client = mock[ClientWrapper]
      val tokenManager = new TokenManager(creds, client)

      creds.retrieveConsumerCredentials returns "user:pass"
      tokenManager.base64EncodedCredentials must beEqualTo("dXNlcjpwYXNz")
    }

    "retrieve token" in new WithApplication {
      val creds = mock[Credentials]
      val client = mock[ClientWrapper]
      val tokenManager = new TokenManager(creds, client)

      creds.retrieveConsumerCredentials returns "user:pass"

      val mockResponseBody = (x: String) => "valid_token"
      tokenManager.retrieveToken(mockResponseBody) must beEqualTo("valid_token")
    }

  }

}
