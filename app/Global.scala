import api.auth.{ProductionCredentials, Credentials}
import com.google.inject.{AbstractModule, Guice}
import play.api.GlobalSettings

object Global extends GlobalSettings {

  val injector = Guice.createInjector(new AbstractModule {
    override def configure(): Unit = {
      bind(classOf[Credentials]).to(classOf[ProductionCredentials])
    }
  })

  override def getControllerInstance[A](controllerClass: Class[A]): A = injector.getInstance(controllerClass)


}
