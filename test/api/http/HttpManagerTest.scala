package api.http

import org.specs2.mutable.Specification
import play.api.test.WithApplication
import org.specs2.mock.Mockito
import play.api.libs.ws.{WS, WSResponse}
import scala.concurrent.Future

class HttpManagerTest extends Specification with Mockito {


  "HttpManager" should {

    "execute api.http requests" in new WithApplication {
      val client = mock[ClientWrapper]
      val response = mock[Future[WSResponse]]

      val manager = new HttpManager(client)

      val request = WS.url("api.http://www.google.com")

      client.execute(request) returns response

      manager.executeRequest(request) must beEqualTo(response)

    }

  }


}



