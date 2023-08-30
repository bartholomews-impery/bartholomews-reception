package ch.ojtanner.bartholomews.reception.api

import ch.ojtanner.bartholomews.reception.common.CreateOrderRequest

import java.util.UUID

@main def test(): Unit =
  ReceptionApi.onOrderCreated(println)

  val createOrderRequest: CreateOrderRequest = CreateOrderRequest(UUID.randomUUID())
  ReceptionApi.createOrder(createOrderRequest)
