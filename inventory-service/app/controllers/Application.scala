package controllers

import play.api._
import play.api.libs.functional.syntax._
import play.api.libs.json.{JsPath, Reads}
import play.api.mvc._
import play.api.Play.current
import scala.language.postfixOps
object Application extends Controller {

  def index = Action {
    val errorRate = Play.application.configuration.getDouble("service.errorRate").get
    val minDelay = Play.application.configuration.getInt("service.minDelay").get
    val maxDelay = Play.application.configuration.getInt("service.maxDelay").get
    Ok(views.html.index(errorRate, minDelay, maxDelay))
  }

  def submit = Action(parse.json) { implicit request =>
    request.body.asOpt[ServiceSettings] match {
      case Some(settings) =>
        Ok(s"settings: $settings")
      case None =>
        BadRequest(request.body)
    }
  }

  implicit val serviceSettingsRead:Reads[ServiceSettings] = (
    (JsPath \ "errorRate").read[Double] and
    (JsPath \ "minDelay").read[Int] and
    (JsPath \ "maxDelay").read[Int]
    )(ServiceSettings.apply _)

}

case class ServiceSettings(errorRate:Double, minDelay:Int, maxDelay:Int)