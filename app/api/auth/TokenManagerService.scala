package api.auth

import play.api.libs.ws.{WSResponse, WS}
import scala.concurrent.{Await, Future}
import play.api.libs.json.Json
import scala.concurrent.duration._
import api.http.ClientWrapper
import play.api.Play

import scala.concurrent.ExecutionContext.Implicits.global
import play.api.Play.current

class TokenManagerService(client: ClientWrapper) {

  val api_url = Play.configuration.getString("twitter.api.url").getOrElse("https://api.twitter.com/oauth2/token")


  def requestNewAccessToken(encodedCredentials: String): String = {
    println("obtainAccessToken")
    val holder = WS.url(api_url)
    val complexHolder = holder
      .withHeaders("Authorization" -> encodedCredentials, "Content-Type" -> "application/x-www-form-urlencoded;charset=UTF-8")
      .withMethod("POST")
      .withBody(Map("grant_type" -> Seq("client_credentials")))

    val response = client.execute(complexHolder)
    handleAccessTokenResponse(response)
  }

  def handleAccessTokenResponse(response: Future[WSResponse]) = {
    Await.result(response.map {
      resp =>
        if (resp.status == 200) {
          val body = Json.parse(resp.body)
          val accessToken = (body \ "access_token").asOpt[String]
          accessToken match {
            case Some(value) => {
              value
            }
            case None => handleError()
          }
        }
        else {
          handleError(resp.body)
        }
    }, 5 seconds)
  }


  def handleError(respBody: String = "") = {
    if (!respBody.isEmpty) println("Response body: " + respBody)
    println("Obtaining Access Token Failed")
    "todo"
  } //TODO
}
