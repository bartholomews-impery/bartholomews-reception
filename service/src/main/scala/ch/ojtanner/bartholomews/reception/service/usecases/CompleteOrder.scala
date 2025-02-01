package ch.ojtanner.bartholomews.reception.service.usecases

import ch.ojtanner.bartholomews.reception.common.{CompleteOrderRequest, OrderCompletedResponse}
import ch.ojtanner.bartholomews.reception.service.adapter.usecases.CompleteOrderUseCase
import ch.ojtanner.bartholomews.reception.service.domain.Order
import ch.ojtanner.bartholomews.reception.service.usecases.ports.OrderRepository

class CompleteOrder(orderRepository: OrderRepository) extends CompleteOrderUseCase:
  override def execute(completeOrderRequest: CompleteOrderRequest): Either[Throwable, OrderCompletedResponse] =
    val maybeOrder: Option[Order] = orderRepository.findById(completeOrderRequest.orderId)

    maybeOrder match
      case None =>
        Left(Throwable(s"Order of Id ${completeOrderRequest.orderId} does not exist"))
        
      case Some(order) =>
        val eitherOrder: Either[Throwable, Order] = Order.asCompleted(order)
        
        eitherOrder match
          case Left(throwable: Throwable) =>
            Left(throwable)
            
          case Right(completedOrder: Order) =>
            orderRepository.save(completedOrder)
            Right(OrderCompletedResponse(completedOrder.orderId))
            