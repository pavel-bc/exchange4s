import com.blockchain.exchange4s.ExchangeClient
import com.blockchain.exchange4s.data.Predef._
import com.blockchain.exchange4s.data.{Request, Response}
import com.blockchain.exchange4s.data.Response._

object AuthenticatedChannels extends ExampleRunner {
  def run(): Unit = {
    // Handle necessary types of incoming messages
    val handler = (response: Response) =>
      response match {
        case Error(_, text) =>
          println(s"❌ $text")
        case Auth(_, ev, _) =>
          println(s"🔑 $ev")
        case Balances(_, ev, balances, _, _) if ev == EVENT_SNAPSHOT =>
          println(s"💰 $balances")
        case _ =>
      }

    // Instantiate client
    val token    = "YOUR_API_TOKEN"
    val exchange = new ExchangeClient(onEvent = handler, onMessage = logger)

    // Authenticate & subscribe to private channels
    exchange.authenticate(token)
    exchange.subscribeBalances()

    // Place an order
    exchange.createOrder(
      Request.CreateOrder(
        clOrderId = "ABC",
        symbol = "BTC-USD",
        ordType = "limit",
        timeInForce = Some("GTC"),
        side = "sell",
        orderQty = 10.0,
        price = Some(3400.0),
        execInst = Some("ALO")
      )
    )

    // Cancel an order
    exchange.cancelOrder("ORDER_ID")

    // Close connection after 30s
    Thread.sleep(30000)
    exchange.stop()
  }
}
