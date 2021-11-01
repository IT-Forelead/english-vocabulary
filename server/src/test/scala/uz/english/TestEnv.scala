package uz.english

import cats.effect.IO
import cats.implicits.*
import org.scalatest.Assertion
import org.scalatest.Checkpoints.*
import uz.english.PureTestSuite

import java.util.concurrent.atomic.AtomicInteger

trait TestEnv extends PureTestSuite:

  class StepsChecker {
    var wasWrongStep = false
    var currentSt = new AtomicInteger(0)

    def st[T](i: Int, t: T): T = {
      val stBefore = currentSt.get()
      if (stBefore == i) {
        if (!currentSt.compareAndSet(stBefore, stBefore + 1)) {
          wasWrongStep = true
        }
      } else {
        wasWrongStep = true
      }
      t
    }

    def checkTotal(shouldBe: Int): Boolean =
      !wasWrongStep && currentSt.get() == shouldBe
  }

  def runAssertions(ioAssertions: IO[Assertion]*): Unit = {
    val cp = new Checkpoint()

    IOAssertion {
      ioAssertions
        .toList
        .traverse(identity)
        .map { ts =>
          ts.foreach(cp(_))
          cp.reportAll()
        }
    }
  }