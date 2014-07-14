package api.http

import play.api.libs.ws.{WSResponse, WSRequestHolder}
import scala.concurrent.Future
import org.specs2.mock.Mockito

import scala.concurrent.ExecutionContext.Implicits.global

class MockClientWrapper(
                         var status: Int = 200,
                         var body: String = "default"
                         )
  extends ClientWrapper with Mockito {

  override def execute(holder: WSRequestHolder): Future[WSResponse] = {

    val response = mock[WSResponse]

    val result = mock[Future[WSResponse]]
    result.map returns response
    scala.concurrent.Future {
      response.header("Content-Type") returns Option("application/json; charset=utf-8")
      response.status returns status
      response.body returns body
    }

  }
}
