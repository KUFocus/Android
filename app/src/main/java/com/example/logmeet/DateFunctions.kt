import android.util.Log
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Locale

fun formatDate(dayOfMonth: String): String? {
    return try {
        val today: LocalDate = LocalDate.now()
        val day = dayOfMonth.toIntOrNull()

        if (day == null || day < 1 || day > today.lengthOfMonth()) {
            null
        } else {
            val formattedDate = LocalDate.of(today.year, today.month, day)
            formattedDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"))
        }
    } catch (e: DateTimeParseException) {
        e.printStackTrace()
        null
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun formatDateForServer(date: String): String? {
    return try {
        val inputFormat = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
        val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        val newDate = inputFormat.parse(date)
        outputFormat.format(newDate!!)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}


fun formatDateForFront(date: String): String {
    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val outputFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")

    val parsedDate = LocalDate.parse(date, inputFormatter)
    return parsedDate.format(outputFormatter)
}

fun splitDateTime(dateTime: String): Pair<String, String> {
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val date = inputFormat.parse(dateTime)

        val dateFormat = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        if (date != null) {
            Pair(dateFormat.format(date), timeFormat.format(date))
        } else {
            Pair("", "")
        }
    } catch (e: Exception) {
        Log.e("splitDateTime", "Error parsing dateTime: ${e.message}")
        Pair("", "")
    }
}