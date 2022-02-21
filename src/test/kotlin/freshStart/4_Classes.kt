package freshStart

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldBeSmallerThan
import io.kotest.matchers.shouldBe

class `4_ClassesShould` : StringSpec({

    "be constructible" {
        val maClasse = MaClasse()

        (maClasse is MaClasse ) shouldBe true
        maClasse.uneProp shouldBe true

        maClasse.uneProp = false

        val maSousClasse = MaSousClasse(autreProp =  8)
        maSousClasse.uneProp shouldBe true
        maSousClasse.autreProp shouldBe 8
       // maSousClasse.tardiveProp shouldBe "oo"
        maSousClasse.getterProp shouldBe "valeur"
    }
})

open class MaClasse {
    var uneProp = true
}

class MaSousClasse( val autreProp: Int) : MaClasse() {
    lateinit var tardiveProp : String

     val getterProp : String
     get() = "valeur"

}