package com.blockchain.exchange4s.data

case class OrderInner(
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
    fee: Option[Double]
)
