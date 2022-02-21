package freshStart

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.shouldBe

class `4_ClassesShould` : StringSpec({

    "objects are anonymous"{
        val helloWorld = object {
            val hello = "Hello"
            val world = "World"

            // object expressions extend Any, so `override` is required on `toString()`
            override fun toString() = "$hello $world"
        }

        helloWorld.toString() shouldBe helloWorld.hello + " " + helloWorld.world
    }

    "objects are kinda static"{
        Singleton.configuration shouldBe "localhost"
    }

    "class with anonymous objects inside and companions"{
        val c = C()
        val obj = c.getObject()
        //obj.

        //C.
    }

    "be constructible" {
        val maClasse = MaClasse()

        (maClasse is MaClasse) shouldBe true

        maClasse.uneProp shouldBe false

        maClasse.uneProp = false

        val maSousClasse = MaSousClasse(autreProp = 8)
        maSousClasse.uneProp shouldBe true
        maSousClasse.autreProp shouldBe 8
        // maSousClasse.tardiveProp shouldBe "oo"
        maSousClasse.getterProp shouldBe "valeur"
    }

    "be constructible with params and constructors"{
        val sut0 = MonAutreClasse(1, true)
        val sut = MonAutreClasse("1", "true")

        sut.i shouldBe sut0.i
        sut.b shouldBe sut0.b
        sut.liste shouldContain 1
        // sut0 shouldBe sut
    }

    "be extensible"{
        val sut = MonAutreClasse("1", "true")
        val actual = sut.faitAutreChose()
        actual shouldBe 2
    }


})

class MonAutreClasse(val i: Int, val b: Boolean) {
    var liste: MutableList<Int>

    constructor(valI: String, valB: String) : this(valI.toInt(), valB.toBoolean()) {
        liste.add(this.i)
    }

    init {
        liste = mutableListOf()
    }
}

private fun MonAutreClasse.faitAutreChose(): Int {
    return this.i + 1
}

object Singleton {
    val configuration: String
        get() = "localhost"
}

open class MaClasse {
    var uneProp = true
}

class MaSousClasse(val autreProp: Int) : MaClasse() {
    lateinit var tardiveProp: String

    val getterProp: String
        get() = "valeur"
}

class C {
    // The return type is Any. x is not accessible
    fun getObject() = object {
        val x: String = "x"
    }

    companion object WhateverTheName {
        fun create(): C {
            return C()
        }
    }
}