import extensions.TimeUnits
import extensions.add
import extensions.format
import java.io.File
import java.io.FileReader
import java.net.Inet4Address
import java.net.InetAddress
import java.net.ServerSocket
import java.net.Socket
import java.util.*
import java.util.Calendar



@Throws(Throwable::class)
fun main(args: Array<String>) {
    val server = Server("0.0.0.0", 8080)
    println("0.0.0.0")
    try {
        server.start()
//    ServerSocket(8080)
    }
    finally {
        println("asd")
        server.stop()
    }
}