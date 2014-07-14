package api.auth

import play.api.libs.ws.WSResponse
import scala.concurrent.Await
import javax.inject.Inject
import api.http.ClientWrapper
import api.auth.TokenManager.{InvalidToken, BearerToken}

object TokenManager {

  sealed trait Token

  case object InvalidToken extends Token

  case class BearerToken(value: String) extends Token

  private var bearerToken: Token = InvalidToken

}

class TokenManager @Inject()(credentials: Credentials, client: ClientWrapper) {

  implicit val context = play.api.libs.concurrent.Execution.Implicits.defaultContext
  val service = new TokenManagerService(client)

  def obtainAccessToken = retrieveToken(service.requestNewAccessToken)

  def readToken = ???

  def invalidateToken = {
    TokenManager.bearerToken = TokenManager.InvalidToken
  }

  def retrieveToken(fetchToken: String => String): String = {
    TokenManager.bearerToken match {
      case InvalidToken => {
        val value = fetchToken("Basic " + base64EncodedCredentials.filter(_ >= ' '))
        setAccessToken(value)
        value
      }
      case token: BearerToken => token.value
    }
  }

  private def setAccessToken(value: String) {
    TokenManager.bearerToken = BearerToken(value)
  }

  def base64EncodedCredentials: String = new sun.misc.BASE64Encoder().encode(credentials.retrieve.getBytes)

}
