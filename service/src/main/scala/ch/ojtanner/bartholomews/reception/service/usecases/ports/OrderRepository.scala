package ch.ojtanner.bartholomews.reception.service.usecases.ports

import ch.ojtanner.bartholomews.reception.service.domain.Order

import java.util.UUID

trait OrderRepository:
  def save(order: Order): Unit
  def findAll(): List[Order]
  def findById(orderId: UUID): Option[Order]
  