import com.blockchain.exchange4s.ExchangeClient
import com.blockchain.exchange4s.data.Predef._
import com.blockchain.exchange4s.data.Response
import com.blockchain.exchange4s.data.Response._

object AnonymousChannels extends ExampleRunner {
  def run(): Unit = {
    // Handle necessary types of incoming messages
    val handler = (response: Response) =>
      response match {
        case Error(_, text) =>
          println(s"❌ $text")
        case Heartbeat(seq, _, ts) =>
          println(s"💓 [seq: $seq, timestamp: $ts]")
        case OrderBook(_, ev, lvl, sym, bids, asks) if ev == EVENT_SNAPSHOT =>
          println(s"📚 $lvl / $sym [bids: ${bids.size}, asks: ${asks.size}]")
        case OrderBook(_, ev, lvl, sym, bids, asks) if ev == EVENT_UPDATED =>
          println(s"📖 $lvl / $sym [bids: $bids, asks: $asks]")
        case Symbols(_, ev, updates) if ev == EVENT_UPDATED =>
          println(s"🔣 Updates: $updates")
        case Ticker(_, ev, sym, price, vol, last) if DATA_EVENTS.contains(ev) =>
          println(s"🕒 $sym [price: $price, vol: $vol, last: $last]")
        case _ =>
      }

    val exchange = new ExchangeClient(onEvent = handler, onMessage = logger)

    // Subscribe to anonymous channels
    exchange.subscribeHeartbeat()
    exchange.subscribeSymbols()
    exchange.subscribeL2("BTC-USD")
    exchange.subscribeTicker("XLM-USD")

    // Close connection after 30s
    Thread.sleep(30000)
    exchange.stop()
  }
}
