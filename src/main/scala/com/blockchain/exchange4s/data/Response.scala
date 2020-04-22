package com.blockchain.exchange4s.data

sealed trait Response

object Response {
  case class Error(event: String, text: String) extends Response

  case class Auth(
      seqnum: Long,
      event: String,
      text: Option[String]
  ) extends Response

  case class Balances(
      seqnum: Long,
      event: String,
      balances: Option[List[Balance]],
      total_available_local: Option[Double],
      total_balance_local: Option[Double]
  ) extends Response

  case class Order(
      seqnum: Long,
      event: String,
      orderID: Option[String],
      clOrderID: Option[String],
      symbol: Option[String],
      side: Option[String],
      ordType: Option[String],
      leavesQty: Option[Double],
      cumQty: Option[Double],
      avgPx: Option[Double],
      ordStatus: Option[String],
      timeInForce: Option[String],
      text: Option[String],
      execType: Option[String],
      execID: Option[String],
      transactTime: Option[String],
      msgType: Option[Int],
      lastPx: Option[Double],
      lastShares: Option[Double],
      tradeId: Option[String],
      price: Option[Double],
      fee: Option[Double],
      orders: Option[List[OrderInner]]
  ) extends Response

  case class Heartbeat(
      seqnum: Long,
      event: String,
      timestamp: Option[String]
  ) extends Response

  case class OrderBook(
      seqnum: Long,
      event: String,
      channel: String,
      symbol: String,
      bids: Option[List[Bid]],
      asks: Option[List[Ask]]
  ) extends Response

  case class Prices(
      seqnum: Long,
      event: String,
      symbol: String,
      price: Option[List[Double]]
  ) extends Response

  case class Symbols(
      seqnum: Long,
      event: String,
      symbols: Option[Map[String, SymbolUpdate]]
  ) extends Response

  case class Ticker(
      seqnum: Long,
      event: String,
      symbol: String,
      price_24h: Option[Double],
      volume_24h: Option[Double],
      last_trade_price: Option[Double]
  ) extends Response

  case class Trades(
      seqnum: Long,
      event: String,
      symbol: String,
      timestamp: Option[String],
      side: Option[String],
      qty: Option[Double],
      price: Option[Double],
      trade_id: Option[String]
  ) extends Response
}
