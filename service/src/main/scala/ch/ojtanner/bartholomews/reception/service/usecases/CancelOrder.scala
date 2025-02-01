package ch.ojtanner.bartholomews.reception.service.usecases

import ch.ojtanner.bartholomews.reception.common.{CancelOrderRequest, OrderCanceledResponse}
import ch.ojtanner.bartholomews.reception.service.adapter.usecases.CancelOrderUseCase
import ch.ojtanner.bartholomews.reception.service.domain.Order
import ch.ojtanner.bartholomews.reception.service.usecases.ports.OrderRepository

class CancelOrder(orderRepository: OrderRepository) extends CancelOrderUseCase:
  override def execute(cancelOrderRequest: CancelOrderRequest): Either[Throwable, OrderCanceledResponse] =
    val maybeOrder: Option[Order] = orderRepository.findById(cancelOrderRequest.orderId)

    maybeOrder match
      case None =>
        Left(Throwable(s"Order of id ${cancelOrderRequest.orderId} does not exist."))

      case Some(order) =>
        val eitherOrder: Either[Throwable, Order] = Order.asCancelled(order)

        eitherOrder match
          case Left(throwable: Throwable) =>
            Left(throwable)

          case Right(cancelledOrder: Order) =>
            orderRepository.save(cancelledOrder)
            Right(OrderCanceledResponse(cancelledOrder.orderId))
