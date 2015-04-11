package controllers

import play.api._
import play.api.mvc._
import play.api.Play.current
object Application extends Controller {

  def index = Action {
    val errorRate = Play.application.configuration.getDouble("service.errorRate").get
    val minDelay = Play.application.configuration.getInt("service.minDelay").get
    val maxDelay = Play.application.configuration.getInt("service.maxDelay").get
    Ok(views.html.index(errorRate, minDelay, maxDelay))
  }

}