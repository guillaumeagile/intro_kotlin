package freshStart

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class `2_NoNullsAllowed` : StringSpec({


    "cheh" {
        var obj = null
        obj shouldBe null
        //  val valeur : Any
        //  valeur shouldNotBe null

        lateinit var tableau: Array<Int>
        tableau shouldBe null
    }

    "safe call"{
        var nullable: Int? = 1
        // nullable = null
        nullable shouldBe null
        (nullable ?: 1) shouldBe 1
    }

    "safe call on object"{ // https://kotlinlang.org/docs/whatsnew12.html#check-whether-a-lateinit-var-is-initialized
        lateinit var third: Node<Int>

        val second = Node(2, next = { third })
        val first = Node(1, next = { second })
        third = Node(3, next = { first })

        third.next().value shouldBe 1
        third.next().value shouldBe 1

/*
        third = Node(3, next = { null })
        third.next()?.value shouldBe null
        third.next()!!.value shouldBe null
 */
    }

})

class Node<T>(val value: T, val next: () -> Node<T>)
// class Node<T>(val value: T, val next: () -> Node<T>?)