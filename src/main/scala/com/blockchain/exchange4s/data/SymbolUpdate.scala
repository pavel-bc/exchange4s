package com.blockchain.exchange4s.data

case class SymbolUpdate(
    val id: Int,
    val status: String,
    val base_currency: String,
    val base_currency_scale: Int,
    val counter_currency: String,
    val counter_currency_scale: Int,
    val min_price_increment: Int,
    val min_price_increment_scale: Int,
    val min_order_size: Int,
    val min_order_size_scale: Int,
    val max_order_size: Int,
    val max_order_size_scale: Int,
    val lot_size: Int,
    val lot_size_scale: Int,
    val auction_price: Double,
    val auction_size: Double,
    val auction_time: String,
    val imbalance: Double
)
