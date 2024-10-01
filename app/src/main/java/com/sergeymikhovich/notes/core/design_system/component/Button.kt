package com.sergeymikhovich.notes.core.design_system.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp

private val buttonShape: Shape = RoundedCornerShape(12.dp)

private fun Modifier.buttonSpecs(): Modifier =
    fillMaxWidth()
        .heightIn(54.dp)

@Composable
fun AuthButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String
) {
    Button(
        modifier = modifier
            .buttonSpecs(),
        shape = buttonShape,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color(0xFFD9614C)
        ),
        elevation = ButtonDefaults.elevation(
            defaultElevation = 0.dp
        )
    ) {
        Text(
            text = text,
            lineHeight = TextUnit(1.4F, TextUnitType.Em),
            fontSize = TextUnit(16F, TextUnitType.Sp),
            textAlign = TextAlign.Center,
            color = Color.White
        )
    }
}

@Composable
fun AuthOtherwiseButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit
) {
    TextButton(
        modifier = modifier
            .buttonSpecs(),
        shape = buttonShape,
        onClick = onClick
    ) {
        Text(
            text = text,
            lineHeight = TextUnit(1.4F, TextUnitType.Em),
            fontSize = TextUnit(16F, TextUnitType.Sp),
            fontWeight = FontWeight.ExtraBold,
            textAlign = TextAlign.Center,
            color = Color(0xFFD9614C)
        )
    }
}

@Composable
fun AccountCenterButton(
    modifier: Modifier = Modifier,
    text: String,
    textColor: Color,
    startIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    onClick: () -> Unit
) {
    TextButton(
        modifier = modifier
            .buttonSpecs(),
        shape = buttonShape,
        onClick = onClick,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (startIcon != null)
                    startIcon()
                Text(
                    modifier = Modifier
                        .padding(horizontal = 8.dp),
                    text = text,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = TextUnit(0.5f, TextUnitType.Sp),
                    lineHeight = TextUnit(1.4F, TextUnitType.Em),
                    fontSize = TextUnit(16f, TextUnitType.Sp),
                    color = textColor
                )
            }
            if (trailingIcon != null)
                trailingIcon()
        }
    }
}

@Preview
@Composable
private fun AuthButtonPreview() {
    AuthButton(onClick = {}, text = "Sign In")
}

@Preview
@Composable
private fun AuthOtherwiseButtonPreview() {
    AuthOtherwiseButton(
        text = "Don't you have any account? Register here",
        onClick = {}
    )
}

@Preview
@Composable
private fun AccountCenterButtonPreview() {
    AccountCenterButton(
        text = "Sign out",
        textColor = Color(0xFFD9614C),
        startIcon = {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                contentDescription = "",
                tint = Color(0xFFD9614C)
            )
        },
        onClick = {}
    )
}

@Preview
@Composable
private fun AccountCenterButtonPreview1() {
    AccountCenterButton(
        text = "Edit profile",
        textColor = Color(0xFF595550),
        startIcon = {
            Icon(
                imageVector = Icons.Filled.Edit,
                contentDescription = "",
                tint = Color(0xFFD9614C)
            )
        },
        trailingIcon = {
            Icon(
                imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowRight,
                contentDescription = "",
                tint = Color(0xFF595550)
            )
        },
        onClick = {}
    )
}