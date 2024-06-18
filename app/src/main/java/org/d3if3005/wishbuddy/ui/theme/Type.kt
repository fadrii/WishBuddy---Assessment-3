package org.d3if3005.wishbuddy.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import org.d3if3005.wishbuddy.R

// Set of Material typography styles to start with
val Kanit = FontFamily(
    Font(R.font.kanit_regular),
    Font(R.font.kanit_bold, FontWeight.Bold),
    Font(R.font.kanit_light, FontWeight.Light),
    Font(R.font.kanit_semibold, FontWeight.SemiBold)
)
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = Kanit,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    displayMedium = TextStyle(
        fontFamily = Kanit,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp
    ),
    displayLarge = TextStyle(
        fontFamily = Kanit,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp
    ),
    displaySmall = TextStyle(
        fontFamily = Kanit,
        fontWeight = FontWeight.Light,
        fontSize = 14.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = Kanit,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = Kanit,
        fontWeight = FontWeight.SemiBold,
        fontSize = 28.sp
    )
)