package freshStart_Coroutines

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Semaphore

class `7_Coroutines` : StringSpec({

    "normal functions are blocking"{
        val maClasse = MaClasse()
        var result: Sequence<Int>? = null
        val sut = { result = maClasse.blockingLongRunningCall() }  //launch

        sut.invoke()

        result shouldNotBe null


    }

    "suspend function are non blocking"{
        val maClasse = MaClasse()
        var result: Sequence<Int>? = null
        val sut = async { result = maClasse.nonblockingRunningCall() }  //launch
        result shouldBe null
        sut.join()
        // on peut faire mieux
        result shouldNotBe null
    }

    "flows are suspendable"{
        val maClasse = MaClasse()
        var result: Flow<Int>? = null
        val sut = async { result = maClasse.nonblockingLongRunningCall() }  //launch
        result shouldBe null
        sut.await()
        result shouldNotBe null
        result!!.toList()
    }

    "go with the flows"{
        val maClasse = MaClasse()
        launch( ) {
            maClasse.nonblockingLongRunningCall().collect { println("I'm not blocked either $it") }
        }
        maClasse.nonblockingLongRunningCall().collect { println("I'm not blocked  $it") }
    }

    "synch the flow"{
        val maClasse = MaClasse()
        val semaphore = Semaphore(1, 1)
        semaphore.release()
        maClasse.nonblockingSyncRunningCall(semaphore).collect {
            println("I'm released  $it");
            semaphore.release()  // release the next one
        }
    }


    "be non blocking" {
        val maClasse = MaClasse()
        //given
        var res = emptyList<String>()

        //when
        //runBlocking {
        async { res = maClasse.longRunningCall(res) }
        res = res.plus("Hello,")
        // }

        //then
        res shouldContainExactly listOf("Hello,", "world!")
    }

    "be a promise" {
        val maClasse = MaClasse()
        //given
        var res = emptyList<String>()
        //when
        val defferedRes1 = async { maClasse.longRunningCall(res) }
        val defferedRes2 = async { maClasse.longRunningCallHello(res) }

        val finalRes1 = defferedRes1.await().plus("Hello,")
        val finalRes2 = defferedRes2.await() + defferedRes1.await()

        //then
        finalRes1 shouldContainExactly listOf("world!", "Hello,")
        finalRes2 shouldContainExactly listOf("Hello,", "world!")
    }


    "play with promises"{
        val mesClassesAsync = listOf(MaClasse(), MaClasse(), MaClasse())
        var sharedRes = emptyList<String>().toMutableList()
        // mesClassesAsync.map {   it.longRunningCallHello2(sharedRes) }

        val res = mesClassesAsync.map {
            async {
                (it.longRunningCallHello2(sharedRes))
            }
        }
        sharedRes shouldHaveSize 0
        res.awaitAll()
        sharedRes shouldHaveSize 3
    }

    "play with promises and their output"{
        val mesClassesAsync = listOf(MaClasse(), MaClasse(), MaClasse())
        val res = mesClassesAsync.map {
            async {
                (it.longRunningCallHello3())
            }
        }
        val awaitAllResult = res.awaitAll()
        val finalRes = awaitAllResult.flatten()
        finalRes shouldHaveSize 6
    }
})


class MaClasse() {

    fun blockingLongRunningCall(): Sequence<Int> = sequence { // sequence builder
        for (i in 1..10) {
            Thread.sleep(1_000L) // pretend we are computing it
            yield(i) // yield next value
        }
    }


    suspend fun nonblockingRunningCall(): Sequence<Int> = sequence { // sequence builder
        for (i in 1..10) {
            yield(i) // yield next value
        }
    }


    suspend fun nonblockingLongRunningCall(): Flow<Int> = flow { // flow builder
        for (i in 1..10) {
            delay(10)
            emit(i) // yield  next value
        }
    }


    suspend fun nonblockingSyncRunningCall(sharedCounterLock: Semaphore): Flow<Int> = flow { // flow builder
        for (i in 1..10) {
            sharedCounterLock.acquire()
            emit(i) // yield next value
        }
    }


    suspend fun longRunningCall(input: List<String>): List<String> {
        delay(10)
        return input.plus("world!")
    }


    suspend fun longRunningCallHello(input: List<String>): List<String> {
        delay(10)
        return input.plus("Hello,")
    }


    suspend fun longRunningCallHello2(input: MutableList<String>) {
        delay(10)
        input.add("Hello,")
    }

    suspend fun longRunningCallHello3(): List<String> {
        delay(10)
        return listOf("Hello,", "world")
    }

}

