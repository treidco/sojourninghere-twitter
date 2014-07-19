package api.status

abstract class BaseStatusService {

  def getTweets(token: String): String

}
