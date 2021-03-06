# Pattern matching #



> Generally speaking, pattern matching is a technique for assigning names to things
> and decomposing data structures and objects with a known structure into its underlying parts.
>
> -- <cite>[Pattern Matching in Scala][1]</cite>

[1]: http://wiki.ifs.hsr.ch/SemProgAnTr/files/PatternMatchingInScala.pdf



Greet-metode:
```scala
case class Person(gender: Gender, name: String)

def greet(person: Person) = {
  if (person.gender == MALE)
    "Hello, Mr. " + person.name
  else if (person.gender == FEMALE)
    "Hello, Mrs. " + person.name
  else
    "Hello, " + person.name
}
```



Med pattern matching:
```scala
def greet(person: Person) {
  person match {
    case Person(MALE, name)   => "Hello, Mr. " + name
    case Person(FEMALE, name) => "Hello, Mrs. " + name
    case Person(_, name)      => "Hello, " + name
  }
}
```



### Syntaks: ###
> selector match { alternatives }



### Syntaks: ###
> selector match { alternatives }

```scala
def fib(n: Int) : Int =
  n match {
    case 0 => 0
    case 1 => 1
    case _ => fib(n-1) + fib(n-2)
  }
```



### Typer patterns: ###

- Constant pattern
- Variable pattern
- Typed pattern
- Wildcard pattern
- Constructor pattern
- Sequence pattern



### Constant pattern: ###
Matcher bare seg selv:
```scala
def describe(x: Any) = {
  x match {
    case 5  => "five"
    case true => "truth"
    case "hello" => "hi!"
    case Nil => "the empty list"
    case _ => "something else"
  }
}
```



### Variable pattern: ###
Matcher hva som helst, men binder
variabelen til objektet man matcher på:
```scala
def printIf42(x: Any) = {
  x match {
    case 42 => println(42)
    case something => println("Not 42: " + something)
  }
}
```



### Typed pattern: ###
```scala
def generalSize(x: Any) {
  x match {
    case s: String => s.length
    case m: Map[_, _] => m.size
    case l: List[_] => l.length
    case _ => -1
  }
}
```



### Wildcard pattern ###
_ matcher hva som helst:
```scala
def printIf42(x: Any) = {
  x match {
    case 42 => println(42)
    case _ => println("Not 42, bleh")
  }
}
```



### Case classes (repetisjon): ###
```scala
case class Person(name: String, age: Int)
```



### Constructor pattern: ###
Sjekker klassetilhørighet og constructor-argumenter:
```scala
def printNameIfPerson(x: Any) = {
  x match {
    case Person(name, age) => println("Name: " + name)
    case something => println("Not a person: " + something)
  }
}

```



### Constructor pattern: ###
Nøstede patterns:
```scala
abstract class Tree
case class Node(value: Int, left: Tree, right: Tree) extends Tree
case class Leaf(value: Int) extends Tree

def bothDescendantsAreLeaves(tree: Tree) = {
  tree match {
    case Node(value, Leaf(v1), Leaf(v2)) => true
    case _ => false
  }
}
```



### Sequence pattern: ###
Kan matche på Sequences:
```scala
seq match {
  case Seq(0, _, _) => println("Three-element with head=0!")
  case _ =>
}
```
Kan bruke _* for å matche et vilkårlig antall elementer:

```scala
seq match {
  case Seq(0, _*) => println("List starting with 0!")
  case _ =>
}
```



### Annen syntaks for sequence matching (scala 2.10): ###
```scala
seq match {
    case 0 +: rest => println("List starting with 0!")
    case _ =>
}
```



### Tuple pattern: ###
```scala
def printTuple(x: Any) {
  x match {
    case (a, b) => println(a + " " + b)
    case _ =>
  }
}
```



### Option matching ###
```scala
foo match {
    case Some(value) => println(value)
    case None => println("None")
  }
```



### Variabel-binding: ###

```scala
def leftLeaf(tree: Tree): Option[Leaf] = {
  tree match {
    case Node(_, leaf @ Leaf(_), child) => Some(leaf)
    case _ => None
  }
}
```



### Pattern guard ###
Man kan kun bruke en pattern-variabel én gang så dette er ikke lov:
```scala
def leftAndRightAreEqual(tree: Tree) {
  tree match {
    case Node(_, child, child) => true
    case _ => false
  }
}
```

Løses med pattern guard:
```scala
def leftAndRightAreEqual(tree: Tree) = {
  tree match {
    case Node(_, left, right) if left == right => true
    case _ => false
  }
}
```



### Rekkefølge: ###
Kompilerer ikke: (error: unreachable code)
```scala
def sillyTreeMatch(tree: Tree) {
  tree match {
    case tree: Tree => println("Matched some tree")
    case Node(_, left, right) => println("Matched node")
    case Leaf(_) => println("Matched node")
  }
}
```
Kompilerer hvis vi bytter om:
```scala
def sillyTreeMatch(tree: Tree) {
  tree match {
    case Leaf(_) => println("Matched leaf")
    case Node(_, left, right) => println("Matched node")
    case tree: Tree => println("Matched some tree")
  }
}
```



### Kompilatorhjelp ###
Kompilerer uten advarsler:
```scala
def sillyTreeMatch(tree: Tree) {
  tree match {
    case Leaf(_) => println("Matched leaf")
  }
}
```
Hva om vil vil at kompilatoren skal advare om at vi mangler patterns?



### Sealed classes ###
```scala
sealed abstract class Tree
case class Node(value: Int, left: Tree, right: Tree) extends Tree
case class Leaf(value: Int) extends Tree

Tree sillyTreeMatch(tree: Tree) {
  tree match {
    case Leaf(_) => println("Matched leaf")
  }
}
```
warning: match is not exhaustive!



### Patterns i variabel-definisjoner: ###
```scala
val someTuple = (1,2)
val (num1, num2) = someTuple
```



> Generally speaking, pattern matching is a technique for assigning names to things
> and decomposing data structures and objects with a known structure into its underlying parts.

```scala
def silly(p: Tree): String = {
  p match {
    case Node(v, _, _) if v < 18 => s"lol! Value is ${v}"
    case a@Node(20, _, _) => a.toString
    case t: Leaf => "Leaf"
    case _ => "I give up"
  }
}
```



# Oppgaver
PatternMatching

```scala
sbt:
> ~ test-only scalakurs.patternmatching.PatternMatchingTest
```



# Extractors ##



Analyse av strenger som representerer epost-adresser

Mulig løsning:
```scala
def isEmail(s: String): Boolean
def domain(s: String): String
def user(s: String): String
```



Vi vil heller bruke pattern matching:
```scala
s match {
  case Email(user, domain) => ...
}
```

Men s er en String!



Case classes (en gang til):

```scala
case class Person(name: String, age: Int)
```

To av metodene som blir generert:
```scala
case class Person(name: String, age: Int)
object Person {
  ...
  def apply(name: String, age: Int): Person = ...
  def unapply(p: Person): Option[(String, Int)] = ...
  ...
}
```



'apply' konstruerer et objekt fra argumentene:
```scala
  def apply(name: String, age: Int): Person = ...
```



'unapply' dekomponerer et objekt:
 ```scala
  def unapply(p: Person): Option[(String, Int)] = ...
```
F.eks:
 ```scala
  unapply(Person("Ola", 55)) // == Some(("Ola", 55))
```



Ved pattern matching prøver kompilatoren å finne en passende unapply-metode!



Så når vi skriver dette:

```scala
person match {
  case Person(name, age) => println(name + " ," + age)
}
```

... forsøker kompilatoren å finne og kalle Person.unapply:
```scala
object Person {
   ...
   def unapply(p: Person): Option[(String, Int)] = ...
   ...
}
```



Hva om vi vil matche på strenger som "Stig 29", "Ola 55"  osv:

```scala
val s = "Ola 55"
s match {
    case Person(name, age) => println(name + ", " + age)
}
```



Løsning:
```scala
object Person {
  // Ny unapply-metode som tar inn en String:
  def unapply(s: String): Option[(String, Int)] = ...
}
```



Tilbake til opprinnelig problem:
```scala
s match {
  case Email(user, domain) => ...
}
```
Kompilator vil klage på at det ikke finnes noen Email.unapply(String): Option[(String, String)]



Løsning:
```scala
object Email {
  def unapply(str: String): Option[(String, String)] = {
    str.split("@") match {
      case Array(user, domain) => Some((user, domain))
      case _ => None
    }
  }
}
```



# Oppgaver
Extractors

```scala
sbt:
> ~ test-only scalakurs.patternmatching.ExtractorsTest
```
