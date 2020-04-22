package com.blockchain.exchange4s

import com.blockchain.exchange4s.data.Predef._
import com.blockchain.exchange4s.data.{Request, Response}
import com.github.andyglow.websocket.{Websocket, WebsocketClient, WebsocketHandler}
import com.github.andyglow.websocket.util.Uri
import io.circe._
import io.circe.generic.auto._
import io.circe.syntax._
import io.circe.{Decoder, Printer}
import io.circe.parser._

/**
  * Creates an instance of Blockchain.com Exchange API client
  * @param url Websocket URL, default: "wss://ws.prod.blockchain.info/mercury-gateway/v1/ws"
  * @param origin Exchange URL, default: "https://exchange.blockchain.com"
  * @param onEvent Event handling function (Response => Unit) for incoming messages
  * @param onMessage Tracing function (String, Boolean => Unit) for incoming/outgoing messages
  * @param onError Error handling function (Throwable => Unit) for any errors occured
  */
class ExchangeClient(
    url: String = WS_URL,
    origin: String = ORIGIN_URL,
    onEvent: Response => Unit = dummyFunc,
    onMessage: (String, Boolean) => Unit = tupleFunc,
    onError: Throwable => Unit = errorFunc
) {
  private val printer: Printer = Printer.noSpaces.copy(dropNullValues = true)

  private val handler: WebsocketHandler[String] =
    new WebsocketHandler[String]() {
      def receive: PartialFunction[String, Unit] = {
        case str => parseMessage(str)
      }
    }

  private def parseMessage(input: String): Unit = {
    onMessage(input, true)

    val doc: Json = parse(input).getOrElse(Json.Null)
    val result: Decoder.Result[String] =
      doc.hcursor.downField("channel").as[String]
    val channel = result.getOrElse("invalid")

    val response: Either[Error, Response] = channel match {
      case CHANNEL_HEARTBEAT => decode[Response.Heartbeat](input)
      case CHANNEL_AUTH      => decode[Response.Auth](input)
      case CHANNEL_BALANCES  => decode[Response.Balances](input)
      case CHANNEL_TRADING   => decode[Response.Order](input)
      case CHANNEL_L2        => decode[Response.OrderBook](input)
      case CHANNEL_L3        => decode[Response.OrderBook](input)
      case CHANNEL_PRICES    => decode[Response.Prices](input)
      case CHANNEL_SYMBOLS   => decode[Response.Symbols](input)
      case CHANNEL_TICKER    => decode[Response.Ticker](input)
      case CHANNEL_TRADES    => decode[Response.Trades](input)
      case _                 => decode[Response.Error](input)
    }

    response match {
      case Left(error)  => onError(error)
      case Right(value) => onEvent(value)
    }
  }

  private val client: WebsocketClient[String] =
    WebsocketClient(Uri(url), handler, Map("origin" -> origin))

  private val ws: Websocket = client.open()

  private def send(message: String): Unit = {
    onMessage(message, false)
    ws ! message
  }

  /**
    * Authenticates current web socket connection
    * @param token API token
    */
  def authenticate(token: String): Unit = {
    val request = Request.Subscription(
      action = ACTION_SUBSCRIBE,
      channel = CHANNEL_AUTH,
      token = Some(token)
    )

    send(printer.print(request.asJson))
  }

  /**
    * Creates an order
    * @param request Order paramaters
    */
  def createOrder(request: Request.CreateOrder): Unit = {
    send(printer.print(request.asJson))
  }

  /**
    * Cancels an order
    * @param orderID Order ID
    */
  def cancelOrder(orderID: String): Unit = {
    val request = Request.CancelOrder(orderID = orderID)
    send(printer.print(request.asJson))
  }

  /**
    * Subscribes to balances channel
    */
  def subscribeBalances(): Unit = {
    val request = Request.Subscription(
      action = ACTION_SUBSCRIBE,
      channel = CHANNEL_BALANCES
    )

    send(printer.print(request.asJson))
  }

  /**
    * Subscribes to heartbeat channel
    */
  def subscribeHeartbeat(): Unit = {
    val request = Request.Subscription(
      action = ACTION_SUBSCRIBE,
      channel = CHANNEL_HEARTBEAT
    )

    send(printer.print(request.asJson))
  }

  /**
    * Subscribes to Level 2 order book
    * @param symbol Symbol to subscribe on, e.g. "BTC-USD"
    */
  def subscribeL2(symbol: String): Unit = {
    val request = Request.Subscription(
      action = ACTION_SUBSCRIBE,
      channel = CHANNEL_L2,
      symbol = Some(symbol)
    )

    send(printer.print(request.asJson))
  }

  /**
    * Subscribes to Level 3 order book
    * @param symbol Symbol to subscribe on, e.g. "BTC-USD"
    */
  def subscribeL3(symbol: String): Unit = {
    val request = Request.Subscription(
      action = ACTION_SUBSCRIBE,
      channel = CHANNEL_L3,
      symbol = Some(symbol)
    )

    send(printer.print(request.asJson))
  }

  /**
    * Subscribes to candlestick market data
    * @param symbol Symbol to subscribe on, e.g. "BTC-USD"
    * @param granularity Granularity in seconds, supported values: 60, 300, 900, 3600, 21600, 86400
    */
  def subscribePrices(symbol: String, granularity: Long): Unit = {
    val request = Request.Subscription(
      action = ACTION_SUBSCRIBE,
      channel = CHANNEL_PRICES,
      symbol = Some(symbol),
      granularity = Some(granularity)
    )

    send(printer.print(request.asJson))
  }

  /**
    * Subscribes to symbol updates
    */
  def subscribeSymbols(): Unit = {
    val request = Request.Subscription(
      action = ACTION_SUBSCRIBE,
      channel = CHANNEL_SYMBOLS
    )

    send(printer.print(request.asJson))
  }

  /**
    * Subscribes to ticker data
    * @param symbol Symbol to subscribe on, e.g. "BTC-USD"
    */
  def subscribeTicker(symbol: String): Unit = {
    val request = Request.Subscription(
      action = ACTION_SUBSCRIBE,
      channel = CHANNEL_TICKER,
      symbol = Some(symbol)
    )

    send(printer.print(request.asJson))
  }

  /**
    * Subscribes to trade updates on executions
    * @param symbol Symbol to subscribe on, e.g. "BTC-USD"
    */
  def subscribeTrades(symbol: String): Unit = {
    val request = Request.Subscription(
      action = ACTION_SUBSCRIBE,
      channel = CHANNEL_TRADES,
      symbol = Some(symbol)
    )

    send(printer.print(request.asJson))
  }

  /**
    * Unsubscribes from a channel / symbol
    * @param channel Channel name
    * @param symbol Symbol name (if applicable)
    */
  def unsubscribe(channel: String, symbol: Option[String]): Unit = {
    val request = Request.Subscription(
      action = ACTION_UNSUBSCRIBE,
      channel = channel,
      symbol = symbol
    )

    send(printer.print(request.asJson))
  }

  /**
    * Closes websocket connection
    */
  def stop(): Unit = {
    client.shutdownSync()
  }
}
