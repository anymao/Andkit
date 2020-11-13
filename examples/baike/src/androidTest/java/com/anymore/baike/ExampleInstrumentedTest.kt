package com.anymore.baike

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.anymore.baike.bean.BaikeResult
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun testFound() {
        val result = BaikeFinder.find("水稻")
        assert(result.resultCode == BaikeResult.SUCCESS)
    }

    @Test
    fun testNotFound(){
        val result2 = BaikeFinder.find("sdds4d4fdf4f4fg5")
        assert(result2.resultCode == BaikeResult.NOT_FOUND)
    }
}