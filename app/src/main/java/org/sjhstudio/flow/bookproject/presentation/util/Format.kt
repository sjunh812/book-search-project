package org.sjhstudio.flow.bookproject.presentation.util

import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

val originDateFormat = SimpleDateFormat("yyyyMMdd", Locale.KOREA)
val newDateFormat = SimpleDateFormat("yyyy. MM. dd", Locale.KOREA)
val recentSearchDateFormat = SimpleDateFormat("MM.dd", Locale.KOREA)

val discountFormat = DecimalFormat("###,###")