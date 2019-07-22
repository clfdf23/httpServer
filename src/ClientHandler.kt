import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStream
import java.net.Socket

class ClientHandler(
    private val socket: Socket,
    private val inputStream: InputStream = socket.getInputStream(),
    private val outputStream: OutputStream = socket.getOutputStream()
) : Runnable {
    override fun run() {
        try {
            val request = Request.makeRequest(readInputHeaders()) ?: return
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
            if (s == null || s.trim().isEmpty())
                break
            requestString += "$s\n"
        }
        println("REQUEST:\n$requestString\$")
        return requestString
    }
}