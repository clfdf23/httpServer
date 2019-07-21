package extensions

import java.text.SimpleDateFormat
import java.util.*

const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR

fun Date.format(pattern: String = "dd MM yyyy HH:mm:ss"): String {
    val dateFormat = SimpleDateFormat(pattern, Locale("en"))
    return "${selectDayOfWeek()}, ${selectMonth(dateFormat.format(this))}"
}

fun Date.add(value: Int, units: TimeUnits = TimeUnits.SECOND): Date {
    var time = this.time

    time += when (units) {
        TimeUnits.SECOND -> value * SECOND
        TimeUnits.MINUTE -> value * MINUTE
        TimeUnits.HOUR -> value * HOUR
        TimeUnits.DAY -> value * DAY
    }
    this.time = time
    return this
}
fun selectDayOfWeek(): String {
    return when(Calendar.getInstance().get(Calendar.DAY_OF_WEEK)) {
        1 -> "Sun"
        2 -> "Mon"
        3 -> "Tue"
        4 -> "Wed"
        5 -> "Thu"
        6 -> "Fri"
        7 -> "Sat"
        else -> Calendar.getInstance().get(Calendar.DAY_OF_WEEK).toString()
    }
}
fun selectMonth(s: String): String {
    var a = s.split(' ')
    return when (a[1]) {
        "01" -> "${a[0]} Jan ${a[2]} ${a[3]}"
        "02" -> "${a[0]} Feb ${a[2]} ${a[3]}"
        "03" -> "${a[0]} Mar ${a[2]} ${a[3]}"
        "04" -> "${a[0]} Apr ${a[2]} ${a[3]}"
        "05" -> "${a[0]} May ${a[2]} ${a[3]}"
        "06" -> "${a[0]} Jun ${a[2]} ${a[3]}"
        "07" -> "${a[0]} Jul ${a[2]} ${a[3]}"
        "08" -> "${a[0]} Aug ${a[2]} ${a[3]}"
        "09" -> "${a[0]} Sep ${a[2]} ${a[3]}"
        "10" -> "${a[0]} Oct ${a[2]} ${a[3]}"
        "11" -> "${a[0]} Now ${a[2]} ${a[3]}"
        "12" -> "${a[0]} Dec ${a[2]} ${a[3]}"
        else -> "${a[0]} ${a[1]} ${a[2]} ${a[3]}"
    }
}

enum class TimeUnits {
    SECOND,
    MINUTE,
    HOUR,
    DAY
}
