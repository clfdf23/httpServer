package extensions

fun List<String>.findFirst(s: String) : String {
    var out = ""
    this.forEach {
        if (it.contains(s))
            out = it
    }
    return out;
}