package ch.ojtanner.bartholomews.reception.common

import java.util.UUID

sealed trait ReceptionApiData
sealed trait ReceptionApiRequest extends ReceptionApiData
sealed trait ReceptionApiResponse extends ReceptionApiData

case class CreateOrderRequest(orderId: UUID) extends ReceptionApiRequest
case class OrderCreatedResponse(oderId: UUID) extends ReceptionApiResponse
case class CompleteOrderRequest(orderId: UUID) extends ReceptionApiRequest
case class CancelOrderRequest(orderId: UUID) extends ReceptionApiResponse
case class OrderCanceledResponse(orderId: UUID) extends ReceptionApiResponse
