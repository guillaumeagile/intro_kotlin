package freshStart

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.comparables.shouldBeLessThan
import io.kotest.matchers.ints.exactly
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeTypeOf

class `1stTests` : StringSpec({

    "Should not pass" {
        false shouldBe true
    }

    "val vs const val" {
        val valeur = "7"
       //  valeur++

    }



    "Typage implicite" {
        val valeur = 7.0
        valeur shouldBe 7
        // valeur shouldBeExactly  (7.0)
        //(valeur !is Double) shouldBe false    -> try with Any
    }





    "Smart cast" {
        val valeur : Any = 7
        if (valeur is String)
         valeur shouldBe "7"
       // else
       //     throw Exception("not a String")

        if (valeur is Int)
            valeur shouldBe exactly(7)
        else
            throw Exception("not an Int")
    }

    "Type Coercion" {
        val valeur =  7
        val label = "resultat : "

        (label + valeur).shouldBeTypeOf<Any>()
        //(valeur + label) shouldBe typeOf<String>()

    }

    "When"    {   // https://kotlinlang.org/docs/control-flow.html#when-expression
        val age = 19
        when (age) {
            1 -> age shouldBe  1
            in 1..18 ->  age shouldBeLessThan   19
            else ->  age shouldBeGreaterThan 18
        }

    }
})