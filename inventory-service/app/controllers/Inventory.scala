package controllers

import play.api.libs.json.Writes
import play.api.mvc.{Action, Controller}
import play.api.libs.json._

import scala.concurrent.Future
import play.api.libs.concurrent.Execution.Implicits.defaultContext

import scala.util.Random

/**
 * Created with IntelliJ IDEA.
 * Date: 15-04-13
 * Time: 6:18 PM
 * To change this template use File | Settings | File Templates.
 */
object Inventory extends Controller{

  def retrieveStoreInventory(id:String) = Action.async {
    val timeout = Application.minDelay + Random.nextInt(Application.maxDelay)

    val delay:Future[Boolean] = Future{
      Thread.sleep(timeout)
      !(Random.nextFloat() <= Application.errorRate)
    }
     delay.map {
       case true =>
         val json = Json.toJson(
           List(
             StoreAvailability("Cambie", 100),
             StoreAvailability("Downtown", 100),
             StoreAvailability("Broadway", 100),
             StoreAvailability("Burnaby", 100)
           )
         )
         Ok(json)
       case _ => InternalServerError
     }

  }
  implicit val resultWrite = new Writes[StoreAvailability] {
    override def writes(o: StoreAvailability) = Json.obj(
      "storeName" -> o.storeName,
      "count" -> o.count
    )

  }
}



case class StoreAvailability(storeName:String, count:Int)
