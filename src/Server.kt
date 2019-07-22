import java.io.InputStream
import java.io.OutputStream
import java.net.InetAddress
import java.net.ServerSocket

class Server(
    val address: InetAddress,
    val port: Int
){
    constructor(address: String?, port: Int) : this(
    address = InetAddress.getByName(address),
    port = port
    )

    constructor(port: Int) : this(
    address = null,
    port = port
    )
    val serverSocket: ServerSocket = ServerSocket(port, 50, address)

    init {
        println("server was created on $address:$port")
    }
    fun start() {
        while (true) {
            val clientSocket = serverSocket.accept()
            println("clent connected")
            Thread(ClientHandler(clientSocket)).start()
        }
    }

    fun stop() {
        if (!serverSocket.isClosed)
            serverSocket.close()
    }
}