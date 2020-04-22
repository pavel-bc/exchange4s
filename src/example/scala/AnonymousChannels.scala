import com.blockchain.exchange4s.ExchangeClient
import com.blockchain.exchange4s.data.Predef._
import com.blockchain.exchange4s.data.Response

object AnonymousChannels extends ExampleRunner {
  def run: Unit = {
    // Handle necessary types of incoming messages
    val handler = (response: Response) =>
      response match {
        case Response.Error(_, text) =>
          println(s"❌ $text")
        case Response.Heartbeat(seq, _, ts) =>
          println(s"💓 [seq: $seq, timestamp: $ts]")
        case Response.OrderBook(_, ev, lvl, sym, bids, asks) if ev == EVENT_SNAPSHOT =>
          println(s"📚 $lvl / $sym [bids: ${bids.size}, asks: ${asks.size}]")
        case Response.OrderBook(_, ev, lvl, sym, bids, asks) if ev == EVENT_UPDATED =>
          println(s"📖 $lvl / $sym [bids: $bids, asks: $asks]")
        case Response.Symbols(_, ev, updates) if ev == EVENT_UPDATED =>
          println(s"🔣 Updates: $updates")
        case Response.Ticker(_, ev, sym, price, vol, last) if DATA_EVENTS.contains(ev) =>
          println(s"🕒 $sym [price: $price, vol: $vol, last: $last]")
        case _ =>
      }

    val exchange = new ExchangeClient(onEvent = handler, onMessage = logger)

    // Subscribe to anonymous channels
    exchange.subscribeHeartbeat()
    exchange.subscribeSymbols()
    exchange.subscribeL2("BTC-USD")
    exchange.subscribeTicker("XLM-USD")

    // Do some work
    Thread.sleep(15000)

    // Close connection
    exchange.stop()
  }
}
