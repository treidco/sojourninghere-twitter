package controllers

import play.api.mvc.{Action, Controller}
import api.auth.TokenManager
import javax.inject.Inject
import api.status.StatusService
import play.api.Logger

class Twitter @Inject()(tokenManager: TokenManager, tweetService: StatusService) extends Controller {



  def auth = Action {
    Logger("Twitter Controller").info("auth action")
    val accessToken = tokenManager.obtainAccessToken
    Ok(views.html.token(""))
  }

  def invalidateToken = Action {
    Logger("Twitter Controller").info("invalidate token action")
    tokenManager.invalidateToken()
    Ok(views.html.token("Token Invalidated"))
  }

  def index = Action {
    Logger("Twitter Controller").info("index action")
    val token = tokenManager.obtainAccessToken
    val tweets = tweetService.getTweets(token)

    Ok(views.html.index(tweets))

  }

}