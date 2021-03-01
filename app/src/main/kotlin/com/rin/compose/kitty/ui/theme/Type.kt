package com.rin.compose.kitty.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.rin.compose.kitty.R


val typography = Typography(
  h1 = TextStyle(
    fontFamily = KittyFont.Impact,
    fontSize = 90.sp,
    letterSpacing = 3.6.sp,
  ),
  h2 = TextStyle(
    fontFamily = KittyFont.Impact,
    fontSize = 68.sp,
  ),
  h3 = TextStyle(
    fontFamily = KittyFont.Impact,
    fontSize = 40.sp,
  ),
  h4 = TextStyle(
    fontFamily = KittyFont.Impact,
    fontSize = 30.sp,
    letterSpacing = 1.sp,
  ),
  body1 = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Normal,
    fontSize = 16.sp
  ),
)

object KittyFont {
  val Impact = FontFamily(Font(R.font.impact))
  val HYZhuZiMeiXinTiW = FontFamily(Font(R.font.hy_zhuzimeixinti_w))
}