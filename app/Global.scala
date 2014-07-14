import api.auth.{ProductionCredentials, Credentials}
import api.http.{WSClientWrapper, ClientWrapper}
import com.google.inject.{AbstractModule, Guice}
import play.api.GlobalSettings

object Global extends GlobalSettings {

  val injector = Guice.createInjector(new AbstractModule {
    override def configure(): Unit = {
      bind(classOf[Credentials]).to(classOf[ProductionCredentials])
      bind(classOf[ClientWrapper]).to(classOf[WSClientWrapper])
    }
  })

  override def getControllerInstance[A](controllerClass: Class[A]): A = injector.getInstance(controllerClass)


}
