import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStream
import java.net.Socket

class ClientHandler(
    val socket: Socket,
    val inputStream: InputStream = socket.getInputStream(),
    val outputStream: OutputStream = socket.getOutputStream()
) : Runnable {
    override fun run() {
        try {
            val request = Request.makeRequest(readInputHeaders())
            sendResponse(Response.from(request))
        } catch (t: Throwable) {
        }

    }

    private fun sendResponse(res: Response) {
        outputStream.write(res.toOutBytes())
        outputStream.flush()
        socket.close()
        println("connection close")
        Thread.currentThread().interrupt()
    }

    @Throws(Throwable::class)
    fun readInputHeaders(): String {
        var requestString = ""
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))
        while (true) {
            val s = bufferedReader.readLine()
            requestString += "$s\n"
            if (s == null || s.trim().isEmpty())
                break
        }
        println("REQUEST:\n$requestString\$")
        return requestString
    }
}