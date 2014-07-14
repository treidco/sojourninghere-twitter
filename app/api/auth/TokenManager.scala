package api.auth

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

  def invalidateToken() = TokenManager.bearerToken = TokenManager.InvalidToken

  def retrieveToken(fetchToken: String => String): String = {
    println("retrieveToken")
    TokenManager.bearerToken match {
      case InvalidToken => {
        setAccessToken(fetchToken("Basic " + base64EncodedCredentials.filter(_ >= ' ')))
      }
      case token: BearerToken => token.value
    }
  }

  def base64EncodedCredentials: String = new sun.misc.BASE64Encoder().encode(credentials.retrieve.getBytes)

  private def setAccessToken(value: String): String = {
    TokenManager.bearerToken = BearerToken(value)
    value
  }


}
