package api.auth

import play.api.libs.ws.{WSResponse, WS}
import scala.concurrent.{Future, Await}
import scala.concurrent.duration._
import javax.inject.Inject
import play.api.Play
import play.api.Play.current
import play.api.libs.json.Json
import api.http.{ClientWrapper, WSClientWrapper}

class TokenManager @Inject()(credentials: Credentials, client: ClientWrapper) {

  sealed trait Token

  case object InvalidToken extends Token

  case class BearerToken(value: String) extends Token

  implicit val context = play.api.libs.concurrent.Execution.Implicits.defaultContext
  val api_url = Play.configuration.getString("twitter.api.url").getOrElse("https://api.twitter.com/oauth2/token")

  private var bearerToken: Token = InvalidToken

  def invalidateToken = bearerToken = InvalidToken

  def retrieveToken: String = {
    bearerToken match {
      case InvalidToken => refreshToken
      case token: BearerToken => token.value
    }
  }

  def refreshToken = {
    val value = obtainNewAccessToken(base64EncodedCredentials) //TODO curry
    bearerToken = BearerToken(value)
    value
  }

  def base64EncodedCredentials: String = new sun.misc.BASE64Encoder().encode(credentials.retrieve.getBytes)

  def obtainNewAccessToken(encodedCredentials: String): String = {
    val holder = WS.url(api_url)
    val complexHolder = holder
      .withHeaders("Authorization" -> encodedCredentials, "Content-Type" -> "application/x-www-form-urlencoded;charset=UTF-8")
      .withMethod("POST")
      .withBody(Map("grant_type" -> Seq("client_credentials")))

    val response: Future[WSResponse] = client.execute(complexHolder)

    //TODO does it make sense to use await here
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
