package nz.co.test.transactions.services

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.ToJson
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

class DateJsonAdapter : JsonAdapter<OffsetDateTime>(){

    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
    @FromJson
    override fun fromJson(reader: JsonReader): OffsetDateTime? {
        return try {
            val localDateTime = LocalDateTime.parse(reader.readJsonValue().toString(), formatter)
            OffsetDateTime.of(localDateTime, OffsetDateTime.now().offset)
        } catch (e: Exception) {
            OffsetDateTime.now()
        }
    }
    @ToJson
    override fun toJson(writer: JsonWriter, value: OffsetDateTime?) {
        writer.jsonValue(value.toString())
    }
}

class BigDecimalJsonAdapter : JsonAdapter<BigDecimal>(){
    @FromJson
    override fun fromJson(reader: JsonReader): BigDecimal? {
        return try {
            BigDecimal(reader.readJsonValue().toString())
        } catch (e: Exception) {
            BigDecimal(0)
        }
    }

    @ToJson
    override fun toJson(reader: JsonWriter, value: BigDecimal?) {
        reader.jsonValue(value.toString())
    }

}