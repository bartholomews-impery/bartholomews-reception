package ch.ojtanner.bartholomews.reception.service.domain

import java.util.UUID

sealed trait OrderState
case class Created() extends OrderState
case class Completed() extends OrderState
case class Cancelled() extends OrderState

case class Order(orderId: UUID, state: OrderState)

object Order:
  def asCreated(orderId: UUID): Order = Order(orderId, Created())
  def asCancelled(order: Order): Either[Throwable, Order] =
    order.state match
      case Created() => Right(Order(order.orderId, Cancelled()))
      case Completed() => Left(Throwable("Order is completed, can not be cancelled."))
      case Cancelled() => Left(Throwable("Order is already cancelled."))

  def asCompleted(order: Order): Either[Throwable, Order] =
    order.state match
      case Created() => Right(Order(order.orderId, Completed()))
      case Completed() => Left(Throwable("Order is already completed."))
      case Cancelled() => Left(Throwable("Order is cancelled, can not be completed."))
      