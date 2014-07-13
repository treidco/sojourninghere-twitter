package controllers

import play.api.mvc.{Action, Controller}
import auth.Token

object Twitter extends Controller {

  def index = Action {
    val encodedCredentials = Token.base64EncodeCredentials("???")
    val accessToken = Token.obtainAccessToken("Basic " + encodedCredentials.filter(_ >= ' '))
    Ok(views.html.index(accessToken))
  }

}