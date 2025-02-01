package ch.ojtanner.bartholomews.reception.service.usecases

import ch.ojtanner.bartholomews.reception.common.{CreateOrderRequest, OrderCreatedResponse}
import ch.ojtanner.bartholomews.reception.service.adapter.usecases.CreateOrderUseCase
import ch.ojtanner.bartholomews.reception.service.domain.Order
import ch.ojtanner.bartholomews.reception.service.usecases.ports.OrderRepository

import java.util.UUID

class CreateOrder(orderRepository: OrderRepository) extends CreateOrderUseCase:
  override def execute(createOrderRequest: CreateOrderRequest): OrderCreatedResponse =
    val order: Order = Order.asCreated(createOrderRequest.orderId)
    orderRepository.save(order)
    
    OrderCreatedResponse(order.orderId)