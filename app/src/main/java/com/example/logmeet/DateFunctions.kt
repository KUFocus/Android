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

fun reformatDate(dayOfMonth: String): String? {
    return try {
        val inputFormat = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
        val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        val date = inputFormat.parse(dayOfMonth)
        outputFormat.format(date!!)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}