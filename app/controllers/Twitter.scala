package controllers

import play.api.mvc.{Action, Controller}
import auth.Token

object Twitter extends Controller {

  def auth = Action {
    val encodedCredentials = Token.base64EncodedCredentials
    val accessToken = Token.obtainAccessToken("Basic " + encodedCredentials.filter(_ >= ' '))

    Ok(views.html.token(accessToken))
  }

  def index = TODO()

}