package controllers

import java.lang.Exception

import play.api.libs.json.Writes
import play.api.mvc.{Action, Controller}
import play.api.libs.json._

import scala.concurrent.Future
import play.api.libs.concurrent.Execution.Implicits.defaultContext

import scala.util.parsing.json.JSONArray
import scala.util.{Failure, Success, Try, Random}

/**
 * Created with IntelliJ IDEA.
 * Date: 15-04-13
 * Time: 6:18 PM
 * To change this template use File | Settings | File Templates.
 */
object Inventory extends Controller{

  def retrieveStoreInventory(id:String) = Action.async{
    val timeout = Application.minDelay + Random.nextInt(Application.maxDelay)

    val result = for {
      x <- delay(timeout)
      f <- fetchInventory(id)
    } yield f

    result map {
      case Success(availability) => Ok(Json.toJson(availability))
      case Failure(err) => BadRequest("ruh-roh")
    }
  }

  private def fetchInventory(id:String):Future[Try[Seq[StoreAvailability]]] = Future {
    Random.nextFloat() > Application.errorRate match {
      case true => Success(     Seq(
        StoreAvailability("Cambie", 100),
        StoreAvailability("Downtown", 100),
        StoreAvailability("Broadway", 100),
        StoreAvailability("Burnaby", 100)
      ))
      case _ => Failure(new Exception())
    }
  }

  private def delay(timeout:Int) = Future {
    Thread.sleep(timeout)
  }
  implicit val resultWrite = new Writes[StoreAvailability] {
    override def writes(o: StoreAvailability) = Json.obj(
      "storeName" -> o.storeName,
      "count" -> o.count
    )
  }

}

case class StoreAvailability(storeName:String, count:Int)
