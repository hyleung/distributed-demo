package controllers

import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json.{Writes, _}
import play.api.mvc.{Action, Controller}

import scala.concurrent.Future
import scala.util.{Failure, Random, Success, Try}

/**
 * Created with IntelliJ IDEA.
 * Date: 15-04-13
 * Time: 6:18 PM
 * To change this template use File | Settings | File Templates.
 */
object Inventory extends Controller {

	def retrieveStoreInventory(id: String) = Action.async {
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

	private def fetchInventory(id: String): Future[Try[Seq[StoreAvailability]]] = Future {
		Random.nextFloat() > Application.errorRate match {
			case true => Success(Seq(
				StoreAvailability("Cambie", 100),
				StoreAvailability("Downtown", 100),
				StoreAvailability("Broadway", 100),
				StoreAvailability("Burnaby", 100)
			))
			case _ => Failure(new Exception())
		}
	}

	private def delay(timeout: Int) = Future {
		Thread.sleep(timeout)
	}

	implicit val resultWrite = new Writes[StoreAvailability] {
		override def writes(o: StoreAvailability) = Json.obj(
			"storeName" -> o.storeName,
			"count" -> o.count
		)
	}

}

case class StoreAvailability(storeName: String, count: Int)
