package scalakurs

import org.scalatest.FunSuite
import org.scalatest.matchers.ShouldMatchers

class NumeriskeListerTest
  extends NumeriskeLister
  with FunSuite
  with ShouldMatchers {

  // Range
  test("lag en range fra a til b") {
    val r = lagRange(1, 7)
    assert(r contains 1)
    assert(r contains 7)
  }

  test("summer tallene fra 1 til 20") {
    summerRange(1, 20) should be(210)
  }

  test("summer alle oddetall mellom 1 og 200 (hint: step)") {
    summerOddetall(1, 200) should be(10000)
  }

  test("lag en funksjon (predikat) som sjekker om et gitt tall er et oddetall") {
    oddetallP(11) should be (true)
    oddetallP(20) should be (false)
  }

  test("lag en funksjon som tar en liste med heltall og returnerer oddetallene") {
    filtrerOddetall(List(1, 2, 3, 4, 5)) should be(List(1, 3, 5))
  }

  test("lag en funksjon som tar en liste og returnerer en liste med oddetall og en liste med partall") {
    delOddetallOgPartall(List(1, 2, 3, 4, 5, 6)) should be ((List(1, 3, 5), List(2, 4, 6)))
  }

  test("lag et predikat som sjekker om minst et av elementene i lista er et oddetall") {
    detFinnesEtOddetall(List(2,4,6)) should be (false)
    detFinnesEtOddetall(List(2,4,7)) should be (true)
  }

  test("lag et predikat som sjekker om alle elementene i en liste er oddetall") {
    alleOddetallP(List(1,3,5)) should be (true)
    alleOddetallP(List(1,5,9,4)) should be (false)
  }

  test("lag en funksjon som returnerer en list med alle lister som inneholder oddetall") {
    filtrerListerSomInneholderOddetall(List(List(1, 3), List(2, 4), List(3, 4))) should be(List(List(1, 3), List(3, 4)))
  }

  test("lag en funksjon som returnerer en list med alle lister som kun inneholder oddetall") {
    filtrerListerSomKunInneholderOddetall(List(List(1,3),List(2,4), List(3,4))) should be (List(List(1,3)))
  }

  test("lag en funksjon som returnerer en liste med oddetall") {
    filtrerOddetallLister(List(List(1,2),List(3,4),List(5,7))) should be (List(1,3,5,7))
  }
}

class NumeriskeLister {
  def lagRange(a: Int, b: Int): Range = a to b

  def summerRange(a: Int, b: Int): Int = lagRange(a, b).sum

  def summerOddetall(a: Int, b: Int) = Range(1, 200, 2).sum

  def oddetallP(i: Int): Boolean = i % 2 == 1

  def filtrerOddetall(l: List[Int]): List[Int] = l.filter(oddetallP)

  def delOddetallOgPartall(l: List[Int]): (List[Int], List[Int]) = l.partition(oddetallP)

  def detFinnesEtOddetall(l: List[Int]): Boolean = l.exists(oddetallP)

  def alleOddetallP(l: List[Int]): Boolean = l.forall(oddetallP)

  def filtrerListerSomInneholderOddetall(l: List[List[Int]]): List[List[Int]] = l.filter(detFinnesEtOddetall)

  def filtrerListerSomKunInneholderOddetall(l: List[List[Int]]): List[List[Int]] = l.filter(alleOddetallP)

  def filtrerOddetallLister(l: List[List[Int]]): List[Int] = l.flatten.filter(oddetallP)
}