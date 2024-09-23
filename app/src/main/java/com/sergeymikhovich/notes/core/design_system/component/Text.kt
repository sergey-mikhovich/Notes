package com.sergeymikhovich.notes.core.design_system.component

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType

@Composable
fun AuthTitleText(
    text: String
) {
    Text(
        text = text,
        fontSize = TextUnit(32F, TextUnitType.Sp),
        fontWeight = FontWeight.ExtraBold,
        lineHeight = TextUnit(1.2F, TextUnitType.Em),
        color = Color(0xFF403B36)
    )
}

@Composable
fun AuthDescriptionText(
    text: String
) {
    Text(
        text = text,
        fontSize = TextUnit(16F, TextUnitType.Sp),
        lineHeight = TextUnit(1.4F, TextUnitType.Em),
        color = Color(0xFF595550)
    )
}

@Composable
fun AuthOutlinedButtonText(
    modifier: Modifier = Modifier,
    text: String
) {
    Text(
        modifier = modifier,
        text = text,
        color = Color(0xFF403B36),
        fontWeight = FontWeight.Medium,
        lineHeight = TextUnit(1.4F, TextUnitType.Em),
        fontSize = TextUnit(16F, TextUnitType.Sp)
    )
}

@Composable
@Preview
private fun AuthTitlePreview() {
    AuthTitleText(text = "Let's login")
}

@Composable
@Preview
private fun AuthDescriptionTextPreview() {
    AuthDescriptionText(text = "And notes your ideas")
}

@Composable
@Preview
private fun AuthOutlinedButtonTextPreview() {
    AuthOutlinedButtonText(text = "Keep your notes together")
}