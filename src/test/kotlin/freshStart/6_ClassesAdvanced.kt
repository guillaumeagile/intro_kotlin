package freshStart

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import kotlin.reflect.KProperty


class `6_ClassesAdvanced` : StringSpec({
    "interface like in C#"{
        val sut: JExiste = JeSuis("moi")

        sut.DoncJePense() shouldBe "moi penser à toi"
    }


    "interface power!"{
        val original = JeSuis("moi")
        val sut: JExiste = JeSuisToujours(original)

        sut.DoncJePense() shouldBe "moi penser à toi"
    }


    "delegation par reflection"    {
        val sut = Example()
        sut.maProp shouldBe "thank you for delegating 'maProp' to me!"
    }

    "lazzy prop"    {
        val sut = Example()
        sut.lazyInitialized shouldBe false
        sut.lazyProp shouldBe "hello"
        sut.lazyInitialized shouldBe true
        sut.lazyProp shouldBe "hello"
        sut.lazyInitialized shouldBe true
    }
})


interface JExiste {
    fun DoncJePense(): String
}

class JeSuis(val ID: String) : JExiste {
    override fun DoncJePense() = "$ID penser à toi"
}


class Example {
    var maProp: String by Delegate()

    val lazyProp: String by lazy {
        this.lazyInitialized = !this.lazyInitialized
        "hello"
    }

    var lazyInitialized = false
}

class Delegate {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
        return "thank you for delegating '${property.name}' to me!"
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        println("$value has been assigned to '${property.name}' in $thisRef.")
    }

}


class JeSuisToujours(autre: JeSuis) : JExiste by autre {
    //override
}