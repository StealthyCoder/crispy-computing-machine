import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

object ConcurrencyUtil {
  def awaitAll[A](futures: List[Future[A]]): Unit = {
    Await.ready(Future{ while( ! futures.forall(f => f.isCompleted)) { } }, Duration.Inf)
  }
}
