package controllers

import play.api.mvc.{Action, Controller}
import api.auth.TokenManager
import javax.inject.Inject

class Twitter @Inject() (tokenManager: TokenManager) extends Controller {

  def auth = Action {
    //TODO store access token if appropriate
    val encodedCredentials = tokenManager.base64EncodedCredentials
    val accessToken = tokenManager.obtainAccessToken("Basic " + encodedCredentials.filter(_ >= ' '))

    Ok(views.html.token(""))
  }

  def index = Action {
    Ok(views.html.index("Tweets"))
  }

}