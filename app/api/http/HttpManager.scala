package api.http

import play.api.libs.ws.{WSRequestHolder, WSResponse}
import scala.concurrent.Future
import javax.inject.Inject

class HttpManager @Inject() (client: ClientWrapper){


  def executeRequest(requestHolder: WSRequestHolder): Future[WSResponse] = ???


}
