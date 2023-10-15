package ch.ojtanner.bartholomews.reception.service.domain

import java.util.UUID

sealed trait OrderState
case class Created() extends OrderState
case class Completed() extends OrderState
case class Cancelled() extends OrderState

case class Order(orderId: UUID, state: OrderState)

object Order:
  def create(orderId: UUID): Order = Order(orderId, Created())