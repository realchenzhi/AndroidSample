package realchenzhi.androidsample

import kotlinx.coroutines.*
import org.junit.Test
import realchenzhi.androidsample.ui.kotlin.coroutine.ContinuationInterceptorImpl

/**
 * @author          chenyuxuan
 * @date            12/18/20
 * @description
 */
class CoroutineUnitTest {

    @Volatile
    var result: String? = null

    /*
    输出结果:
    1
    2
    3
    start_1
    start_2
    5
    start_1_
    start_2_
    Result Hey_Hey
    4
     */
    @Test
    fun async_test() {
        println("1")
        GlobalScope.launch(Dispatchers.Unconfined) {
            println("2")
            //1：挂起协程
            val deferred_1 = async() {
                //4：由于3处等待，此处执行
                println("start_1")
                //5：挂起
                val i = requestData()
                println("start_1_")
                i
            }
            val deferred_2 = async() {
                //6：5处挂起，执行println
                println("start_2")
                //7：挂起
                var i = requestData()
                println("start_2_")
                i
            }
            //2：1处挂起协程，执行println
            println("3")
            //3：等待协程执行完毕
            result = deferred_1.await() + "_" + deferred_2.await()
            //8：两处挂起执行完毕，执行输出结果
            println("Result $result")
            println("4")
        }
        println("5")

        while (result == null) {

        }
    }

    /*
    输出结果：
    1
    2
    5
    start_1
    start_1_
    start_2
    start_2_
    3
    Result Hey_Hey
    4
     */
    @Test
    fun withContext_test() {
        println("1")
        GlobalScope.launch(Dispatchers.Unconfined) {
            println("2")
            //1：挂起
            val deferred_1 = withContext(Dispatchers.IO) {
                //3：执行
                println("start_1")
                val i = requestData()
                println("start_1_")
                i
            }
            val deferred_2 = withContext(Dispatchers.IO) {
                println("start_2")
                var i = requestData()
                println("start_2_")
                i
            }
            println("3")
            result = deferred_1 + "_" + deferred_2
            println("Result $result")
            println("4")
        }
        //2：执行println
        println("5")

        while (result == null) {

        }
    }


    private suspend fun requestData(): String {
        delay(1000)
        return "Hey"
    }
}