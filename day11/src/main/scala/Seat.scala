import java.util.concurrent.CopyOnWriteArrayList

import scala.concurrent.{ExecutionContext, Future}
import scala.util.control.Breaks.{break, breakable}

class Seat(initial: Char, x: Int, y: Int)(implicit val ec: ExecutionContext.parasitic.type = ExecutionContext.parasitic) {
  private val state = State(Char.MinValue, initial, Char.MinValue)
  private val _los: CopyOnWriteArrayList[Seat] = new CopyOnWriteArrayList[Seat]()

  def pos: (Int, Int) = (x, y)
  def los: List[(Int, Int)] = _los.toArray(Array[Seat]()).map(seat => seat.pos).toList

  def getState: Char = state.current

  def queueChange(): Unit = {
    state match {
      case x if x.current == 'L' => changeBasedOnL()
      case x if x.current == '#' => changeBasedOnPound()
      case x if x.current == '.' => changeBasedOnDot()
      case _ => throw new IllegalArgumentException("Expected values to be one of 'L', '#' and '.'. Got " + state + " at " + x + "," + y)
    }
  }

  def change(): Unit = {
    state.prev = state.current
    state.current = state.next
  }

  def hasChanged: Boolean = {
    state.prev != state.current
  }

  def line_of_sight(layout: Array[Array[Seat]]): Unit = {
    var futures : List[Future[Unit]] = List.empty

    futures +:= Future { north(layout) }
    futures +:= Future { north_west(layout) }
    futures +:= Future { west(layout) }
    futures +:= Future { south_west(layout) }
    futures +:= Future { south(layout) }
    futures +:= Future { south_east(layout) }
    futures +:= Future { east(layout) }
    futures +:= Future { north_east(layout) }

    ConcurrencyUtil.awaitAll(futures)

  }

  private def changeBasedOnDot(): Unit = {
    state.prev = '.'
    state.current = '.'
    state.next = '.'
  }

  private def changeBasedOnL(): Unit = {
    var change = _los.size() != 0

    _los.forEach(seat => if (seat.getState.equals('#')) change = false)

    if (change) state.next = '#'
    else state.next = state.current
  }

  private def changeBasedOnPound(): Unit = {
    var occupied: List[Boolean] = List.empty

    _los.forEach(seat => occupied :+= seat.getState.equals('#'))

    if (occupied.count(p => p) >= 5) state.next = 'L'
    else state.next = state.current
  }

  private case class State(var prev: Char, var current: Char, var next: Char)

  private def north(layout: Array[Array[Seat]]): Unit = {
    breakable {
      for (i <- LazyList.from(-1, -1)) {
        if (y + i < 0) break
        else if (!layout(y + i)(x).getState.equals('.')) {
          _los.addIfAbsent(layout(y + i)(x))
          break
        }
      }
    }
  }

  private def north_west(layout: Array[Array[Seat]]): Unit = {
    breakable {
      for (i <- LazyList.from(-1, -1)) {
        if (y + i < 0) break
        else if (!layout(y + i)(x + Math.abs(i)).getState.equals('.')) {
          _los.addIfAbsent(layout(y + i)(x + Math.abs(i)))
          break
        }
      }
    }
  }

  private def west(layout: Array[Array[Seat]]): Unit = {
    breakable {
      for (i <- LazyList.from(1)) {
        if (i >= layout.head.length) break
        else if (!layout(y)(x + i).getState.equals('.')) {
          _los.addIfAbsent( layout(y)(x + i))
          break
        }
      }
    }
  }

  private def south_west(layout: Array[Array[Seat]]): Unit = {
    breakable {
      for (i <- LazyList.from(1)) {
        if (i >= layout.head.length) break
        else if (!layout(y + i)(x + i).getState.equals('.')){
          _los.addIfAbsent(layout(y + i)(x + i))
          break
        }
      }
    }
  }

  private def south(layout: Array[Array[Seat]]): Unit = {
    breakable {
      for (i <- LazyList.from(1)) {
        if (i >= layout.length) break
        else if (!layout(y + i)(x).getState.equals('.')) {
          _los.addIfAbsent(layout(y + i)(x))
          break
        }
      }
    }
  }

  private def south_east(layout: Array[Array[Seat]]): Unit = {
    breakable {
      for (i <- LazyList.from(-1, -1)) {
        if (x + i < 0) break
        else if (!layout(y + Math.abs(i))(x + i).getState.equals('.')) {
          _los.addIfAbsent(layout(y + Math.abs(i))(x + i))
          break
        }
      }
    }
  }

  private def east(layout: Array[Array[Seat]]): Unit = {
    breakable {
      for (i <- LazyList.from(-1, -1)) {
        if (x + i < 0) break
        else if (!layout(y)(x + i).getState.equals('.')) {
          _los.addIfAbsent(layout(y)(x + i))
          break
        }
      }
    }
  }

  private def north_east(layout: Array[Array[Seat]]): Unit = {
    breakable {
      for (i <- LazyList.from(-1, -1)) {
        if (x + i < 0) break
        else if (!layout(y + i)(x + i).getState.equals('.'))  {
          _los.addIfAbsent(layout(y + i)(x + i))
          break
        }
      }
    }
  }
}
