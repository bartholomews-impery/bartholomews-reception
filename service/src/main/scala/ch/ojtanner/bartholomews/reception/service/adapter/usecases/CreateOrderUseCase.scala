package ch.ojtanner.bartholomews.reception.service.adapter.usecases

import ch.ojtanner.bartholomews.reception.common.{CreateOrderRequest, OrderCreatedResponse}
import ch.ojtanner.bartholomews.reception.service.domain.Order

import java.util.UUID

trait CreateOrderUseCase:
  def execute(createOrderRequest: CreateOrderRequest): OrderCreatedResponse
