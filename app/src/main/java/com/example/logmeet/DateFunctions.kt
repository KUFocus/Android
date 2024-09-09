import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

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