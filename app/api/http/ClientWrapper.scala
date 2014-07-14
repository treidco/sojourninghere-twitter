package api.http

import play.api.libs.ws.{WSResponse, WSRequestHolder}
import scala.concurrent.Future

abstract class ClientWrapper {
  def execute(holder: WSRequestHolder): Future[WSResponse]
}

class WSClientWrapper extends ClientWrapper {


  override def execute(holder: WSRequestHolder): Future[WSResponse] = {
    holder.execute()
  }
}