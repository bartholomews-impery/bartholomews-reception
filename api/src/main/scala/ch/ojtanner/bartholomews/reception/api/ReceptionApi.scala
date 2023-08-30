package ch.ojtanner.bartholomews.reception.api

import ch.ojtanner.bartholomews.reception.common.{CreateOrderRequest, OrderCreatedResponse}
import com.google.gson.{Gson, GsonBuilder}
import io.nats.client.{Connection, Dispatcher, Message, Nats}

import java.nio.charset.StandardCharsets
import java.time.Duration

object ReceptionApi:
  private val sendTo = "reception.create-order"
  private val receiveFrom = "reception.order-created"

  def createOrder(createOrderRequest: CreateOrderRequest): Unit =
    try {
      val connection: Connection = Nats.connect()

      val gsonBuilder: GsonBuilder = new GsonBuilder()
      val gson: Gson = gsonBuilder.create()
      val json: String = gson.toJson(createOrderRequest)

      println("Publishing message to " + sendTo)
      connection.publish(sendTo, json.getBytes(StandardCharsets.UTF_8))
      connection.flush(Duration.ZERO)
      connection.close()
    } catch {
      case exception: Exception => exception.printStackTrace()
    }

  def onOrderCreated(callback: OrderCreatedResponse => Unit): Unit =
    println("Starting subscriber on " + receiveFrom)
    try {
      val connection: Connection = Nats.connect()

      val dispatcher: Dispatcher = connection.createDispatcher((message: Message) => {
        println("Handling " + message + " from " + receiveFrom)
        val gson: Gson = new Gson()
        val string: String = new String(message.getData, StandardCharsets.UTF_8)
        val orderCreatedResponse: OrderCreatedResponse = gson.fromJson(string, classOf[OrderCreatedResponse])

        callback(orderCreatedResponse)
      })

      dispatcher.subscribe(receiveFrom)
    } catch {
      case exception: Exception => exception.printStackTrace()
    }
