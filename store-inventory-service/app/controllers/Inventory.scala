package controllers

import play.api.libs.json.{Writes, _}
import play.api.mvc.{Action, Controller}

import scala.util.{Failure, Random, Success, Try}

/**
 * Created with IntelliJ IDEA.
 * Date: 15-04-13
 * Time: 6:18 PM
 * To change this template use File | Settings | File Templates.
 */
object Inventory extends Controller {

	def retrieveStoreInventory(id: String) = Action {
		val timeout = Application.minDelay + Random.nextInt(Application.maxDelay)

		rollDice match {
			case Success(_) =>
				Thread.sleep(timeout)
				val availability = fetchInventory(id)
				Ok(Json.toJson(availability))
			case Failure(e) =>
				Thread.sleep(Application.errorDelay)
				InternalServerError("ruh-roh!")
		}
	}

	private def rollDice:Try[Boolean] = {
		Random.nextFloat() > Application.errorRate match {
			case true => Success(true)
			case _ => Failure(new Exception())
		}
	}

	private def fetchInventory(id: String): Seq[StoreAvailability] =  {
		Seq(
				StoreAvailability(1, "Cambie", randomCount),
				StoreAvailability(2, "Downtown", randomCount),
				StoreAvailability(3, "Broadway", randomCount),
				StoreAvailability(4, "Burnaby", randomCount)
			)
	}

	private def randomCount:Int = {
		Random.shuffle(
			Range(0, 200, 10).toList).head
	}

	implicit val resultWrite = new Writes[StoreAvailability] {
		override def writes(o: StoreAvailability) = Json.obj(
			"id" -> o.id,
			"storeName" -> o.storeName,
			"count" -> o.count
		)
	}

}

case class StoreAvailability(id: Int, storeName: String, count: Int)
