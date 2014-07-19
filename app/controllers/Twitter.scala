package controllers

import play.api.mvc.{Action, Controller}
import api.auth.TokenManager
import javax.inject.Inject
import api.status.StatusService

class Twitter @Inject()(tokenManager: TokenManager, tweetService: StatusService) extends Controller {

  def auth = Action {
    val accessToken = tokenManager.obtainAccessToken
    Ok(views.html.token(""))
  }

  def index = Action {

    val token = tokenManager.obtainAccessToken
    val tweets = tweetService.getTweets(token)

    Ok(views.html.index(tweets))

  }

}