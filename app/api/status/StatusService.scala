package api.status

import javax.inject.Inject
import api.http.ClientWrapper
import play.api.libs.ws.{WSResponse, WS}
import scala.concurrent.{Await, Future}
import scala.concurrent.duration._

import scala.concurrent.ExecutionContext.Implicits.global
import play.api.Play.current

class StatusService @Inject()(client: ClientWrapper) extends BaseStatusService {

  def getTweets(token: String): String = {
    println("get tweets")
    val authString = "Bearer " + token
    val holder = WS.url("https://api.twitter.com/1.1/statuses/user_timeline.json")
    val complexHolder = holder
      .withHeaders("Authorization" -> authString)
      .withMethod("GET")
      .withQueryString("screen_name" -> "timreid", "count" -> "10")

    val response = client.execute(complexHolder)
    handleTweetsResponse(response)
  }

  def handleTweetsResponse(response: Future[WSResponse]): String = {
    Await.result(
      response.map {
        resp =>
          resp.body
      }
      , 10 seconds
    )
  }

}
