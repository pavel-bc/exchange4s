package com.blockchain.exchange4s.data

sealed trait OrderBookItem
case class Bid(px: Double, qty: Double, num: Option[Long]) extends OrderBookItem
case class Ask(px: Double, qty: Double, num: Option[Long]) extends OrderBookItem
