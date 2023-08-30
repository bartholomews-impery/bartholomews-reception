package ch.ojtanner.bartholomews.reception.service.adapter.incomming

import ch.ojtanner.bartholomews.reception.common.{CreateOrderRequest, OrderCreatedResponse}
import ch.ojtanner.bartholomews.reception.service.adapter.usecases.CreateOrderUseCase
import com.google.gson.{Gson, GsonBuilder}
import io.nats.client.{Connection, Dispatcher, Message, Nats}

import java.nio.charset.StandardCharsets

object CreateOrderRequestSubscription:
  private val receiveFrom = "reception.create-order"
  private val replyTo = "reception.order-created"

  def onOrderCreated(useCase: CreateOrderUseCase): Unit =
    println("Starting subscriber on " + receiveFrom)
    try {
      val connection: Connection = Nats.connect()

      val dispatcher: Dispatcher = connection.createDispatcher((message: Message) => {
        println("Handling " + message + " from " + receiveFrom)
        val gson: Gson = new Gson()
        val requestString: String = new String(message.getData, StandardCharsets.UTF_8)
        val createOrderRequest: CreateOrderRequest = gson.fromJson(requestString, classOf[CreateOrderRequest])
        val orderCreatedResponse: OrderCreatedResponse = useCase.execute(createOrderRequest)

        val responseString: String = gson.toJson(orderCreatedResponse)
        println("Replying to " + replyTo)
        connection.publish(replyTo, responseString.getBytes(StandardCharsets.UTF_8))
      })

      dispatcher.subscribe(receiveFrom)
    } catch {
      case exception: Exception => exception.printStackTrace()
    }
