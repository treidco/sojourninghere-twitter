package auth

import play.api.libs.ws.{WSResponse, WS}
import scala.concurrent.{Future, Await}
import scala.concurrent.duration._
import play.api.Play.current


object Token {

  implicit val context = play.api.libs.concurrent.Execution.Implicits.defaultContext


  def base64EncodeCredentials(credentials: String): String = {
    new sun.misc.BASE64Encoder().encode(credentials.getBytes)
  }

  def obtainAccessToken(encodedCredentials: String): String = {

    val holder = WS.url("https://api.twitter.com/oauth2/token")
    val complexHolder = holder.withHeaders("Authorization" ->  encodedCredentials, "Content-Type" -> "application/x-www-form-urlencoded;charset=UTF-8")

    val response: Future[WSResponse] = complexHolder.post(Map("grant_type" -> Seq("client_credentials")))

    Await.result(response.map {
      resp => resp.body
    }, 5 seconds)

  }

}
