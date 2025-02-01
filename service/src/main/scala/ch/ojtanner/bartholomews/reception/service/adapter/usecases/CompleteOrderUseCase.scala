package ch.ojtanner.bartholomews.reception.service.adapter.usecases

import ch.ojtanner.bartholomews.reception.common.{CompleteOrderRequest, OrderCompletedResponse}

trait CompleteOrderUseCase:
  def execute(completeOrderRequest: CompleteOrderRequest): Either[Throwable, OrderCompletedResponse]
