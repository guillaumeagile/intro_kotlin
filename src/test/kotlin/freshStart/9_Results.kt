package freshStart

import arrow.core.flatMap
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.result.shouldBeSuccess
import io.kotest.matchers.shouldBe

class `9_Results` : StringSpec({
    "Result is better than try catch"{
        val res = echoString("ok")
        res shouldBeSuccess "ok"
    }

    "Result fold"{
        val res = echoString("ok")
        val output = res.fold(
            onSuccess = {
                ("$it is not null")
            }
        ) {
            ("${it.message}")
        }
        output shouldBe "ok is not null"
    }

    "Result then"{
        val res = echoString("ok")
        val finish = res.flatMap { it ->
            if (it == "ok")
                Result.success("validated $it")
            else
                Result.failure(Exception("not ok"))
        }
        finish shouldBeSuccess "validated ok"
    }

})

fun echoString(string: String): Result<String> {
    return if (string.isEmpty()) {
        Result.failure(Exception("Error"))
    } else {
        Result.success(string)
    }
}