package controllers

import play.api.mvc.{Action, Controller}
import api.auth.TokenManager
import javax.inject.Inject

class Twitter @Inject() (tokenManager: TokenManager) extends Controller {

  def auth = Action {
    val accessToken = tokenManager.obtainAccessToken
    println("access token: " + accessToken)
    Ok(views.html.token(""))
  }

  def index = Action {

//    val token = tokenManager.retrieveToken


    Ok(views.html.index("Tweets"))
  }

}