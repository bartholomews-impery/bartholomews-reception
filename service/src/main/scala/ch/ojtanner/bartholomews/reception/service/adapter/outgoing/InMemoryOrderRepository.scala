package ch.ojtanner.bartholomews.reception.service.adapter.outgoing

import ch.ojtanner.bartholomews.reception.service.domain.Order
import ch.ojtanner.bartholomews.reception.service.usecases.ports.OrderRepository

import java.util.UUID

object InMemoryOrderRepository extends OrderRepository:
  private val orderMap: Map[UUID, Order] = Map.empty

  override def findAll(): List[Order] =
    orderMap.values.toList

  override def findById(orderId: UUID): Option[Order] =
    orderMap.get(orderId)

  override def save(order: Order): Unit =
    orderMap + (order.orderId -> order)
