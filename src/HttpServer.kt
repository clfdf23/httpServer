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
    Server("84.201.186.30",8080).start()
//    ServerSocket(8080)
}