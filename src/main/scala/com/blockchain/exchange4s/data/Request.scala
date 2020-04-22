package com.blockchain.exchange4s.data

sealed trait Request

object Request {
  case class Subscription(
      action: String,
      channel: String,
      symbol: Option[String] = None,
      granularity: Option[Long] = None,
      token: Option[String] = None
  ) extends Request

  case class CreateOrder(
      action: String = Predef.ACTION_CREATE_ORDER,
      channel: String = Predef.CHANNEL_TRADING,
      clOrderId: String,
      symbol: String,
      ordType: String,
      timeInForce: Option[String] = None,
      side: String,
      orderQty: Double,
      price: Option[Double] = None,
      minQty: Option[Double] = None,
      stopPx: Option[Double] = None,
      expireDate: Option[Long] = None,
      execInst: Option[String] = None
  ) extends Request

  case class CancelOrder(
      action: String = Predef.ACTION_CANCEL_ORDER,
      channel: String = Predef.CHANNEL_TRADING,
      orderID: String
  ) extends Request
}
