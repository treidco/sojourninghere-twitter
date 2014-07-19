package api.auth

import play.api.Play
import play.api.Play.current

abstract class Credentials {
  def retrieveConsumerCredentials: String
}

class ProductionCredentials extends Credentials {
  //TODO handle failure scenarios

  val consumerKey = Play.configuration.getString("twitter.consumer.key").getOrElse("key")
  val consumerSecret = Play.configuration.getString("twitter.consumer.secret").getOrElse("secret")

  def retrieveConsumerCredentials = consumerKey + ":" + consumerSecret

}
