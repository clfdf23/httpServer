import extensions.findFirst

data class Request(
    val type: String,
    val path: String,
    val host: String,
    val httpVersion: String,
    val userAgent: String,
    val accept: String,
    val acceptLang: String,
    val acceptEnc: String?,
    val connectionStatus: String
) {
    companion object {
        fun makeRequest(inputString: String): Request {
            val topics = inputString.split('\n')
            val head = topics[0].split(' ')
            return Request.Builder()
                .type(head[0])
                .path(head[1])
                .httpVersion(head[2])
                .connectionStatus(topics.findFirst("Connection:").split(' ').last())
                .accept(topics.findFirst("Accept:").split("Accept: ").last())
                .acceptEnc(topics.findFirst("Accept-Encoding:").split("Accept-Encoding: ").last())
                .host(topics.findFirst("Host:").split("Host: ").last())
                .userAgent(topics.findFirst("User-Agent:").split("User-Agent: ").last())
                .acceptLang(topics.findFirst("Accept-Language:").split("Accept-Language: ").last())
                .build()
        }
    }

    data class Builder(
        private var type: String = "",
        private var path: String = "",
        private var host: String = "",
        private var httpVersion: String = "",
        private var userAgent: String = "",
        private var accept: String = "",
        private var acceptLang: String = "",
        private var acceptEnc: String = "",
        private var connectionStatus: String = ""
    ) {
        fun type(type: String) = apply { this.type = type }
        fun path(path: String) = apply { this.path = path }
        fun host(host: String) = apply { this.host = host }
        fun httpVersion(httpVersion: String) = apply { this.httpVersion = httpVersion }
        fun userAgent(userAgent: String) = apply { this.userAgent = userAgent }
        fun accept(accept: String) = apply { this.accept = accept }
        fun acceptLang(acceptLang: String) = apply { this.acceptLang = acceptLang }
        fun acceptEnc(acceptEnc: String) = apply { this.acceptEnc = acceptEnc }
        fun connectionStatus(connectionStatus: String) = apply { this.connectionStatus = connectionStatus }
        fun build() = Request(type, path, host, httpVersion, userAgent, accept, acceptLang, acceptEnc, connectionStatus)
    }
}