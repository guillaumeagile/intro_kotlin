package freshStart

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.beInstanceOf


fun calculate(x: Int, y: Int, operation: (Int, Int) -> Int): Int {
    return operation(x, y)
}

fun sum(
    x: Int,
    y: Int = 0,
) = x + y

fun ReturnNothing(c: Char): Unit{}


class `5_Functions` : StringSpec({

 "généralités"{

     sum(x = 1) shouldBe  1

     sum(x = 1, -1) shouldBe  0

     val fn = ::sum
     fn(1 , 2) shouldBe 3

     ReturnNothing('a')

     // infix + extension
      1 sumI 3 shouldBe 4
 }

  "high order functions 1" {
      val sumResult = calculate(4, 5, ::sum)

      sumResult shouldBe 4+5

      val mulResult = calculate(4, 5,  { a, b -> a * b })   //move lambda out of parenthesis

      mulResult shouldBe  4 * 5
  }

    "HOF return a fun"{

        makeIncrementer()(7) shouldBe 8

    }

    "lambdas"{
        val upperCase1: (String) -> String = { str: String -> str.uppercase() }

        val upperCase2: (String) -> String = { str -> str.uppercase() }

        val upperCase3 = { str: String -> str.uppercase() }

        // val upperCase4 = { str -> str.uppercase() }

        val upperCase5: (String) -> String = { it.uppercase() }

        val upperCase6: (String) -> String = String::uppercase

        val upperCase7: (String, Int) -> String = {  s, _ -> s.uppercase() }

        upperCase1("s") shouldBe "S"
    }


    "inline"{
        add(1,2,3) shouldBe 6

        compG("c", "a") shouldBe 2

        compG(null, "a") shouldBe 0

         foo<String>() shouldBe String::class.java

    }

})

fun makeIncrementer() = fun(number: Int) = 1 + number






inline fun add(x: Int, y: Int, z: Int): Int {
    val temp = x + y
    val res = temp + z
    return res
}

inline fun <reified T:Any> foo() = T::class.java


fun < T : Comparable<T>> compG(x: T?, y: T): Int {
    if (x !is T)
        return 0
    val temp = x!!.compareTo(y)
    return temp
}

 fun <U> TreeNode<U>.findParentOfType(clazz: Class<U>): U? {    // inline reified
    var p = parent
    //while (p != null && p !is U) {
    while (p != null && !clazz.isInstance(p)) {
        p = p.parent
    }
   // @Suppress("UNCHECKED_CAST")
    return p as U?
}


private infix fun Int.sumI(i: Int): Int = this + i



class TreeNode<T>(value:T){
    var value:T = value
    var parent:TreeNode<T>? = null

    var children:MutableList<TreeNode<T>> = mutableListOf()

    fun addChild(node:TreeNode<T>){
        children.add(node)
        node.parent = this
    }
    override fun toString(): String {
        var s = "${value}"
        if (!children.isEmpty()) {
            s += " {" + children.map { it.toString() } + " }"
        }
        return s
    }
}