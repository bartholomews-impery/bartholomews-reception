import cats.effect.{IO, IOApp}
import ch.ojtanner.bartholomews.reception.service.adapter.incomming.CreateOrderRequestSubscription
import ch.ojtanner.bartholomews.reception.service.adapter.outgoing.InMemoryOrderRepository
import ch.ojtanner.bartholomews.reception.service.usecases.CreateOrder
import me.gonzih.nats.Nats

object Main extends IOApp.Simple:
  def run: IO[Unit] =
    val payload = "hello world"
    val stream = "my-persistent-stream"
    val subj = "my-subject"
    val durable = "my-durable-id"

    // connect to nats via Cats Effect Resource
    Nats
      .connect("nats://localhost:4222")
      .use({ nc =>
        for {
          // stream creation can be done externally or via cats-nats API
          _ <- nc.addStream(stream, subj)
          // create JetStream instance
          js <- nc.js
          // subscribe to subject on stream, this is backed by unbound Cats Effect Queue
          sub <- js.subscribe(stream, subj, durable, true)
          // publish your message
          _ <- js.publish(subj, payload.getBytes)
          // wait for message to be received
          msg <- sub.take
          // unsubscribe
          _ <- sub.unsubscribe
          // print result
          _ <- IO.println(String(msg.getData))
        } yield ()
      })
