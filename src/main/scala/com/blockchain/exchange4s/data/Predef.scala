package com.blockchain.exchange4s.data

object Predef {
  // Default URLs
  val WS_URL     = "wss://ws.prod.blockchain.info/mercury-gateway/v1/ws"
  val ORIGIN_URL = "https://exchange.blockchain.com"

  // Actions
  val ACTION_SUBSCRIBE    = "subscribe"
  val ACTION_UNSUBSCRIBE  = "unsubscribe"
  val ACTION_CANCEL_ORDER = "CancelOrderRequest"
  val ACTION_CREATE_ORDER = "NewOrderSingle"

  // Channels
  val CHANNEL_HEARTBEAT = "heartbeat"
  val CHANNEL_L2        = "l2"
  val CHANNEL_L3        = "l3"
  val CHANNEL_PRICES    = "prices"
  val CHANNEL_SYMBOLS   = "symbols"
  val CHANNEL_TRADES    = "trades"
  val CHANNEL_TICKER    = "ticker"
  val CHANNEL_AUTH      = "auth"
  val CHANNEL_BALANCES  = "balances"
  val CHANNEL_TRADING   = "trading"

  // Event Types
  val EVENT_SUBSCRIBED   = "subscribed"
  val EVENT_UNSUBSCRIBED = "unsubscribed"
  val EVENT_REJECTED     = "rejected"
  val EVENT_SNAPSHOT     = "snapshot"
  val EVENT_UPDATED      = "updated"

  // Event categories
  val META_EVENTS = List(EVENT_SUBSCRIBED, EVENT_UNSUBSCRIBED, EVENT_REJECTED)
  val DATA_EVENTS = List(EVENT_SNAPSHOT, EVENT_UPDATED)

  // Dummy callback functions
  val dummyFunc: Any => Unit               = _ => ()
  val tupleFunc: (String, Boolean) => Unit = (_, _) => ()
  val errorFunc: Throwable => Unit         = err => throw err
}
