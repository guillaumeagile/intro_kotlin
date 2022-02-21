package freshStart

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.maps.shouldContain
import io.kotest.matchers.maps.shouldHaveSize
import io.kotest.matchers.shouldBe

class `8_Collections` : StringSpec({
    "Lists"{
        val shoppingList = listOf(
            "catfish", "water",
            "tulips", "blue paint"
        )

        val shoppingListMutable = mutableListOf(
            "catfish", "water",
            "tulips", "blue paint"
        )
        shoppingListMutable[1] = "bottle of water"
        shoppingListMutable.add("bucket")

    }

    "Maps/Dictionnaties"{
        val occupations = mapOf(
            "Malcolm" to "Captain",
            "Kaylee" to "Mechanic"
        )

        occupations.shouldContain("Malcolm" to "Captain")

        //  occupations.entries.first() shouldBe

        val occupationsMutable = mutableMapOf(
            "Malcolm" to "Captain",
            "Kaylee" to "Mechanic"
        )
        occupationsMutable["Jayne"] = "Public Relations"
        occupationsMutable.put("Rick", "Navigation")


    }

    "collection operations"{
        // https://github.com/mythz/kotlin-linq-examples/blob/master/README.md

        val shipments = listOf(
            IceCreamShipment("Chocolate", 3),
            IceCreamShipment("Strawberry", 7),
            IceCreamShipment("Vanilla", 5),
            IceCreamShipment("Chocolate", 5),
            IceCreamShipment("Vanilla", 1),
            IceCreamShipment("Rocky Road", 1),
        )

        val iceCreamInventory = shipments.groupBy({ it.first }, { it.second })
        iceCreamInventory shouldHaveSize 4

        val iceCreamInventorySum = iceCreamInventory.mapValues { it.value.sum() }

        iceCreamInventorySum.entries.first().key shouldBe "Chocolate"

        //filters
        //val iceCreamInventorySumFilt (value > 4 )
/*
        val asStrings = iceCreamInventorySumFilt.map { (flavor, qty) -> "$qty tubs of $flavor" }
        asStrings shouldHaveSize 3
        asStrings shouldContainAll (setOf(
            "8 tubs of Chocolate", "7 tubs of Strawberry", "6 tubs of Vanilla"
        ))
*/

        //  https://kotlinlang.org/docs/collection-transformations.html#string-representation

        // https://www.runtastic.com/blog/en/kotlin-collections-2/
        //.forEachIndexed({ idx, i -> println("numbers[$idx] = $i") })

        val newCollection = shipments.mapIndexed() { index, pair -> index to pair.first }
        // newCollection.last().first shouldBe

        /*
    nums.take(3)                    // [0, 1, 2]
    nums.takeWhile { it < 5 }       // [0, 1, 2, 3, 4]
    nums.takeLast(3)                // [6, 7, 8]
    nums.takeLastWhile { it > 6 }   // [7, 8]
         */

        //https://www.bezkoder.com/kotlin-list-mutable-list/

    }
})

typealias IceCreamShipment = Pair<String, Int>


