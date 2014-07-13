package controllers

import play.api.mvc.{Action, Controller}
import auth.Token
import javax.inject.Inject

class Twitter @Inject() (token: Token) extends Controller {

  def auth = Action {
    val encodedCredentials = token.base64EncodedCredentials
    val accessToken = token.obtainAccessToken("Basic " + encodedCredentials.filter(_ >= ' '))

    Ok(views.html.token(""))
  }

  def index = Action {
    Ok(views.html.index("Tweets"))
  }

}