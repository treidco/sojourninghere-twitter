package controllers

import play.api.mvc.{Action, Controller}
import api.auth.TokenManager
import javax.inject.Inject

class Twitter @Inject() (tokenManager: TokenManager) extends Controller {

  def auth = Action {
    //TODO store access token if appropriate
    val encodedCredentials = tokenManager.base64EncodedCredentials
    val accessToken = tokenManager.obtainNewAccessToken("Basic " + encodedCredentials.filter(_ >= ' '))
    println("access token: " + accessToken)
    Ok(views.html.token(""))
  }

  def index = Action {

    val token = tokenManager.retrieveToken


    Ok(views.html.index("Tweets"))
  }

}