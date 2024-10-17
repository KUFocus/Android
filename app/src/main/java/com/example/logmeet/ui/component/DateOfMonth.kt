import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.logmeet.R
import com.example.logmeet.ui.component.DrawCircle
import com.example.logmeet.ui.component.GetDaySchedule
import com.example.logmeet.ui.component.determineTextColor
import java.time.LocalDate

@Composable
fun DateOfMonth(
    isBottomSheet: Boolean,
    date: LocalDate,
    clicked: (LocalDate) -> Unit,
) {
    val startOfMonth = date.withDayOfMonth(1)
    val endOfMonth = date.withDayOfMonth(date.lengthOfMonth())

    val firstDayOfWeek = startOfMonth.minusDays((startOfMonth.dayOfWeek.value % 7).toLong())
    val lastDayOfWeek = endOfMonth.plusDays((6 - endOfMonth.dayOfWeek.value % 7).toLong())

    val weeks = mutableListOf<List<LocalDate>>()

    var currentDay = firstDayOfWeek
    while (currentDay <= lastDayOfWeek) {
        val week = (0..6).map {
            currentDay.plusDays(it.toLong())
        }
        weeks.add(week)
        currentDay = currentDay.plusWeeks(1)
    }

    var clickedDate by remember { mutableStateOf<LocalDate?>(null) }

    Column {
        weeks.forEach { week ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                week.forEach { day ->
                    val isInCurrentMonth = day.month == date.month
                    val isSelected = clickedDate == day && isInCurrentMonth
                    val textColor = if (isInCurrentMonth) {
                        determineTextColor(LocalDate.now(), day.dayOfMonth, day.dayOfWeek.value, day.month)
                    } else {
                        androidx.compose.ui.graphics.Color.Gray
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Box(
                            modifier = Modifier
                                .width(32.dp)
                                .then(
                                    if (isInCurrentMonth) {
                                        Modifier.clickable {
                                            clickedDate = day
                                            clicked(clickedDate!!)
                                        }
                                    } else {
                                        Modifier
                                    }
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            DrawCircle(
                                today = LocalDate.now(),
                                currentDate = day.dayOfMonth,
                                isSelected = isSelected,
                                month = day.month
                            )
                            Text(
                                text = day.dayOfMonth.toString(),
                                style = TextStyle(
                                    fontSize = 12.sp,
                                    lineHeight = 16.sp,
                                    fontFamily = FontFamily(Font(R.font.pretendard_bold)),
                                    color = textColor
                                )
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        if (isInCurrentMonth && !isBottomSheet) {
                            GetDaySchedule(
                                date = day
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}
