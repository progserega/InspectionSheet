package ru.drsk.progserega.inspectionsheet.storages.http



import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody
import okio.Buffer
import java.nio.charset.Charset


class ChunkedInterceptor: Interceptor {

    val Utf8Charset = Charset.forName ("UTF-8")

    override fun intercept (chain: Interceptor.Chain): Response {
        val originalResponse = chain.proceed (chain.request ())
        val responseBody = originalResponse.body ()
        val source = responseBody!!.source ()

        val buffer = Buffer () // We create our own Buffer
        val resBuffer = Buffer();
        // Returns true if there are no more bytes in this source

        while (!source.exhausted ()) {
            val readBytes = source.read (buffer, Long.MAX_VALUE) // We read the whole buffer
           // val data = buffer.readString (Utf8Charset)

            println ("Read: $readBytes bytes")
           // println ("Content: \n $data \n")

           resBuffer.write(buffer.readByteArray());
        }

       // val data = buffer.readString (Utf8Charset)
//        println ("Read: $readBytes bytes")
//        println ("Content: \n $data \n")

        println ("Read: ${resBuffer.size()} bytes")
      //  println ("Content: \n $data \n")
       // Log.d("RESPONSE BODY", String.format("raw JSON response is: %s", buffer.readString (Utf8Charset)))
        // Re-create the response before returning it because body can be read only once
        return originalResponse.newBuilder()
                .body(ResponseBody.create(responseBody.contentType(),resBuffer.size(), resBuffer)).build()

       // return originalResponse
    }
}