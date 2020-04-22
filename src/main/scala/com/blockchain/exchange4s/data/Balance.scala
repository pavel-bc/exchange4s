package com.blockchain.exchange4s.data

case class Balance(
    val currency: String,
    val balance: Double,
    val available: Double,
    val balance_local: Double,
    val available_local: Double,
    val rate: Double
)
