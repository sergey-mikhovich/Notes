package com.sergeymikhovich.notes.core.design_system.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp

@Composable
fun AuthOutLinedTextField(
    modifier: Modifier = Modifier,
    value: String,
    singleLine: Boolean = true,
    onValueChange: (String) -> Unit,
    placeholderText: String

) {
    OutlinedTextField(
        singleLine = singleLine,
        modifier = modifier
            .fillMaxWidth()
            .border(
                BorderStroke(width = 1.dp, color = Color(0xFFF2E5D5)),
                shape = RoundedCornerShape(12.dp)
            ),
        shape = RoundedCornerShape(12.dp),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color(0xFFFFFDFA),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        value = value,
        onValueChange = onValueChange ,
        placeholder = {
            Text(
                text = placeholderText,
                style = TextStyle(
                    lineHeight = TextUnit(1.4F, TextUnitType.Em),
                    fontWeight = FontWeight.Normal,
                    fontSize = TextUnit(16F, TextUnitType.Sp),
                    color = Color(0xFFC8C5CB),
                )
            )
        }
    )
}

@Composable
fun NoteBasicTextField(
    modifier: Modifier = Modifier,
    textStyle: TextStyle,
    placeholderText: String,
    placeHolderStyle: TextStyle,
    singleLine: Boolean = false,
    value: String,
    onValueChange: (String) -> Unit
) {
    BasicTextField(
        modifier = modifier.fillMaxWidth(),
        textStyle = textStyle,
        singleLine = singleLine,
        value = value,
        onValueChange = onValueChange,
        decorationBox = { innerTextField ->
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                if (value.isEmpty()) {
                    Text(
                        modifier = Modifier.fillMaxSize(),
                        text = placeholderText,
                        style = placeHolderStyle,
                    )
                }
            }
            innerTextField()
        }
    )
}

@Composable
@Preview
private fun AuthOutLinedTextFieldPreview() {
    AuthOutLinedTextField(
        value = "",
        onValueChange = {},
        placeholderText = "sergey.mikhovich@gmail.com"
    )
}

@Composable
@Preview
private fun NoteBasicTextFieldPreview() {
    NoteBasicTextField(
        textStyle = TextStyle(
            fontSize = TextUnit(16f, TextUnitType.Sp),
            fontWeight = FontWeight.Medium,
            lineHeight = TextUnit(1.3F, TextUnitType.Em),
            color = Color(0xFF595550)
        ),
        placeholderText = "Title",
        placeHolderStyle = TextStyle(
            fontSize = TextUnit(16f, TextUnitType.Sp),
            fontWeight = FontWeight.Bold,
            lineHeight = TextUnit(1.4F, TextUnitType.Em),
            color = Color(0x51595550)
        ),
        value = "",
        onValueChange = {}
    )
}