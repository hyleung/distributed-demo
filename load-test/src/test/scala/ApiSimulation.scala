import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._
import scala.language.postfixOps
import scala.util.Random

/**
 * Created with IntelliJ IDEA.
 * Date: 15-04-18
 * Time: 11:42 AM
 * To change this template use File | Settings | File Templates.
 */
class ApiSimulation extends Simulation{

	val httpConf = http
		.baseURL("http://localhost:8080")

	val scn = scenario("Browse")
		.exec(http("Fetch product list")
			.get("/api/catalog"))
			.pause(1)
		.randomSwitch(
			75.0 -> repeat(5) {
				exec(http("Product detail")
					.get("/api/product/50"))
					.pause(1)},
			25.0 -> repeat(5) {
				exec(http("Product detail w/availability")
					.get("/api/product/50?storeAvailability=true"))
					.pause(1)}
		)

	setUp(scn.inject(rampUsers(250) over (60 seconds)).protocols(httpConf))
}
