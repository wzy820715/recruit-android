package nz.co.test.transactions

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("nz.co.test.transactions", appContext.packageName)
    }

    @Test
    fun offsetDateTimeConvert(){
        val string = "2021-01-01T15:38:46"
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
        val localDateTime = LocalDateTime.parse(string, formatter)
        val offsetDateTime = OffsetDateTime.of(localDateTime, OffsetDateTime.now().offset)
        assertEquals(true, offsetDateTime.toString().contains(string))
    }

    @Test
    fun bigDecimalConvert(){
        val string = "9379.55"
        assertEquals("9379.55", BigDecimal(string).toString())
    }
}