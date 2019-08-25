import java.net.InetAddress
import java.net.ServerSocket
import java.net.Socket
import java.net.SocketException

class Server(
    val address: InetAddress,
    val port: Int
){
    var clientSocket: Socket? = null

    constructor(address: String?, port: Int) : this(
    address = InetAddress.getByName(address),
    port = port
    )

    val serverSocket: ServerSocket = ServerSocket(port, 50, address)

    init {
        println("server was created on $address:$port")
    }
    @Throws(Throwable::class)
    fun start() {
        while (true) {

            try {
                clientSocket = serverSocket.accept()
            }
            catch (e: SocketException) {
                println("connection broken")
                break
            }
            println("client connected")
            Thread(clientSocket?.let { ClientHandler(it) }).start()
        }
    }

    fun stop() {
        if (!serverSocket.isClosed && !Thread.currentThread().isInterrupted) {
            serverSocket.close()
            Thread.currentThread().interrupt()
        }
    }
}