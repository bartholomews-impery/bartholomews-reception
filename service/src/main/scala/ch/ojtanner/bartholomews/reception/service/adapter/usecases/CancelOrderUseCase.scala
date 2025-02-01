package ch.ojtanner.bartholomews.reception.service.adapter.usecases

import ch.ojtanner.bartholomews.reception.common.{CancelOrderRequest, OrderCanceledResponse}

import java.util.UUID

trait CancelOrderUseCase:
  def execute(cancelOrderRequest: CancelOrderRequest): Either[Throwable, OrderCanceledResponse]
