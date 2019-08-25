import extensions.*
import java.io.File
import java.util.*

data class Response(
    val httpVersion: String = "HTTP/1.1",
    var status: String = "",
    val server: String = "ruz_Server_228",
    val date: String = "${Date().add(-3, TimeUnits.HOUR).format()} GMT",
    val contentLang: String = "ru",
    var contentType: String = "",
    var contentLen: String = "",
    val connectionStatus: String = "close",
    var data: String = ""
) {
    constructor(data: String, status: String, contentType: String) : this(
        data = data,
        status = status,
        contentLen = data.length.toString(),
        contentType = contentType
    )

    fun toOutBytes(): ByteArray {
        val out = "$httpVersion $status\r\n" +
                "Server: $server\r\n" +
                "Date: $date\r\n" +
                "Content-Language: $contentLang\r\n" +
                "Content-Type: $contentType\r\n" +
                "Content-Length: $contentLen\r\n" +
                "Connection: $connectionStatus\r\n\r\n" +
                data
        return out.toByteArray()
    }

    companion object {
        fun from(req: Request): Response {
            if (req.type == "")
                return errorResponse("/400.html","400 Bad Request")
            if (req.type != "GET") {
                return errorResponse("/405.html", "405 Method Not Allowed")
            }
            if (req.path.contains("..")) {
                return okResponse("/index.html")
            }
            if (req.path == "/")
                return okResponse("/index.html")
            val file = File("$WEB_DIR${req.path}")
            if (file.exists())
            {
                return if (file.isDirectory)
                    okResponse(req.path + "/index.html")
                else
                    okResponse(req.path)
            }
            return errorResponse("/404.html", "404 Page Not Found")
        }

        private fun errorResponse(filename: String, status: String): Response {
            return Response(File("$ERR_DIR$filename").readText(), status, contentType(filename))
        }

        private fun okResponse(filename: String): Response {
            return Response(File("$WEB_DIR$filename").readText(), "200 OK", contentType(filename))
        }

        private fun contentType(filename: String): String {
            return when (filename.substring(filename.lastIndexOf('.') + 1)) {
                "html" -> "text/html; charset=UTF-8"
                "htm" -> "text/html; charset=UTF-8"
                "xml" -> "text/xml; charset=UTF-8"
                "txt" -> "text/plain; charset=UTF-8"
                "css" -> "text/html; charset=UTF-8"
                "png" -> "image/png; charset=UTF-8"
                "jpg" -> "image/jpg; charset=UTF-8"
                "jpeg" -> "image/jpeg; charset=UTF-8"
                "zip" -> "application/zip; charset=UTF-8"
                else -> "application/${filename.substring(filename.lastIndexOf('.') + 1)}"
            }
        }

        const val WEB_DIR = "server/web"
        const val ERR_DIR = "server/errors"
    }
}