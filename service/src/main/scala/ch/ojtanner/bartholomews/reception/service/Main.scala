import ch.ojtanner.bartholomews.reception.service.adapter.incomming.CreateOrderRequestSubscription
import ch.ojtanner.bartholomews.reception.service.adapter.outgoing.InMemoryOrderRepository
import ch.ojtanner.bartholomews.reception.service.usecases.CreateOrder

@main def hello: Unit =
  new Thread {
    override def run(): Unit =
      CreateOrderRequestSubscription.onOrderCreated(new CreateOrder(orderRepository = InMemoryOrderRepository))
  }.start()
