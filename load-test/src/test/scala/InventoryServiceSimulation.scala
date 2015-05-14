import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._
import scala.language.postfixOps
/**
 * Created with IntelliJ IDEA.
 * Date: 15-04-18
 * Time: 11:42 AM
 * To change this template use File | Settings | File Templates.
 */
class InventoryServiceSimulation extends Simulation{

	val httpConf = http
		.baseURL("http://localhost:9000")

	val scn = scenario("Scenario Name")
		.exec(http("inventory")
			.get("/inventory/1234"))
			.pause(1)

	setUp(scn.inject(rampUsers(1000) over (60 seconds)).protocols(httpConf))
}
