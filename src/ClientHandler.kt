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

    var inputString: String = ""
    override fun run() {
        try {
            readInputHeaders()
        } catch (t: Throwable) {
        }
        println(inputString)
        val request = Request.makeRequest(inputString)
        sendResponse(Response.from(request))
    }

    private fun sendResponse(res: Response) {
        outputStream.write(res.toOutBytes())
        outputStream.flush()
        socket.close()
        println("connection close")

    }

    @Throws(Throwable::class)
    fun readInputHeaders() {
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))
        while (true) {
            val s = bufferedReader.readLine()
            inputString += "$s\n"
            if (s == null || s.trim().isEmpty())
                break
        }
    }
}