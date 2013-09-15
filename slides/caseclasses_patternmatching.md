### Case classes ###
```scala
scala> case class Person(name: String, age: Int)
```
```scala
scala> Person("Stig", 2)
// res2: Person = Person(Stig,2)
```
```scala
scala> Person("Stig", 2) == Person("Stig", 2)
// res1: Boolean = true
```



### Pattern matching ###
```scala
abstract class Tree

case class Node(value: Int, left: Tree, right: Tree) extends Tree

case class Leaf extends Tree
```
```scala
def sumTree(tree: Tree): Int = {
  tree match {
    case Branch(value, left: Tree, right: Tree) => value +
      sumTree(left) + sumTree(right)
    case Leaf(value) => value
  }
}
```



```scala
def sumTree(tree: Tree): Int = {
  // selector match {alternatives}
  tree match {
    // Case pattern => expression: Alternativ 1, vi er på en gren
    case Branch(value, left: Tree, right: Tree) => value +
      sumTree(left) + sumTree(right)
    // Alternativ 2, vi er på en løvnode
    case Leaf(value) => value
  }
}
```