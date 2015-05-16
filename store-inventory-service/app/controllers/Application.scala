package controllers

import play.api.Play.current
import play.api._
import play.api.libs.functional.syntax._
import play.api.libs.json.{Writes, Json, JsPath, Reads}
import play.api.mvc._

import scala.language.postfixOps

object Application extends Controller {
	var errorRate = Play.application.configuration.getDouble("service.errorRate").get
	var minDelay = Play.application.configuration.getInt("service.minDelay").get
	var maxDelay = Play.application.configuration.getInt("service.maxDelay").get
	var errorDelay = Play.application.configuration.getInt("service.errorDelay").get

	def index = Action { implicit request =>
		Ok(views.html.index(request, errorRate, minDelay, maxDelay, errorDelay))
	}

	def retrieveSettings = Action {
		Ok(Json.toJson(ServiceSettings(errorRate, minDelay, maxDelay, errorDelay)))
	}
	def updateSettings = Action(parse.json) { implicit request =>
		request.body.asOpt[ServiceSettings] match {
			case Some(settings) =>
				errorRate = settings.errorRate
				minDelay = settings.minDelay
				maxDelay = settings.maxDelay
				errorDelay = settings.errorDelay
				Ok(s"settings: $settings")
			case None =>
				BadRequest(request.body)
		}
	}

	implicit val serviceSettingsRead: Reads[ServiceSettings] = (
		(JsPath \ "errorRate").read[Double] and
			(JsPath \ "minDelay").read[Int] and
			(JsPath \ "maxDelay").read[Int] and
			(JsPath \ "errorDelay").read[Int]
		)(ServiceSettings.apply _)

	implicit val resultWrite = new Writes[ServiceSettings] {
		override def writes(o: ServiceSettings) = Json.obj(
			"errorRate" -> o.errorRate,
			"minDelay" -> o.minDelay,
			"maxDelay" -> o.maxDelay,
			"errorDelay" -> o.errorDelay
		)
	}
}

case class ServiceSettings(errorRate: Double, minDelay: Int, maxDelay: Int, errorDelay: Int)