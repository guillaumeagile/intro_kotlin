package freshStart

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldBeSmallerThan
import io.kotest.matchers.shouldBe

class `3_ImmutabilityShould` : StringSpec({

    "be immutable by default" {
        val unitArray: Array<String> = Array(10) { i -> "Number of index: $i"  }

        unitArray.size shouldBe 10
        // unitArray.add("1")
    }



    "be mutable only if needed" {
        val intList1 = arrayOf(1, 2, 3)
        val intList2 = intList1.toList()

        val intMutableList2 = intList1.toMutableList()
        intMutableList2.add(4)
        intMutableList2.size shouldBe 4
    }



    "allow list manipulations"{  // https://www.bezkoder.com/kotlin-list-mutable-list/#Add_items_to_List_in_Kotlin
        var nums = listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)
        nums.drop(3)   shouldBeSmallerThan  nums                // [3, 4, 5, 6, 7, 8]
    }





})