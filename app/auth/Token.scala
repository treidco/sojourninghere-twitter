package auth

import play.api.libs.ws.{WSResponse, WS}
import scala.concurrent.{Future, Await}
import scala.concurrent.duration._
import javax.inject.Inject
import play.api.Play
import play.api.Play.current

class Token @Inject()(credentials: Credentials) {

  implicit val context = play.api.libs.concurrent.Execution.Implicits.defaultContext
  val api_url = Play.configuration.getString("twitter.api.url").getOrElse("https://api.twitter.com/oauth2/token")


  def base64EncodedCredentials: String = {
    new sun.misc.BASE64Encoder().encode(credentials.retrieve.getBytes)
  }

  def obtainAccessToken(encodedCredentials: String): String = {
    val holder = WS.url(api_url)
    val complexHolder = holder.withHeaders("Authorization" -> encodedCredentials, "Content-Type" -> "application/x-www-form-urlencoded;charset=UTF-8")

    val response: Future[WSResponse] = complexHolder.post(Map("grant_type" -> Seq("client_credentials")))

    Await.result(response.map {
      resp => resp.body
    }, 5 seconds)

  }

}
