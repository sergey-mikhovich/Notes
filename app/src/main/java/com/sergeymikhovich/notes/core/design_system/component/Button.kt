package com.sergeymikhovich.notes.core.design_system.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp

@Composable
fun AuthButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .height(54.dp),
        shape = RoundedCornerShape(12.dp),
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
    Button(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentSize(),
        shape = RoundedCornerShape(12.dp),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Transparent
        ),
        elevation = ButtonDefaults.elevation(
            defaultElevation = 0.dp
        )
    ) {
        Text(
            text = text,
            lineHeight = TextUnit(1.4F, TextUnitType.Em),
            fontSize = TextUnit(16F, TextUnitType.Sp),
            fontWeight = FontWeight.ExtraBold,
            color = Color(0xFFD9614C)
        )
    }
}

@Composable
fun AccountCenterButton(
    modifier: Modifier = Modifier,
    text: String,
    textColor: Color,
    startIcon: ImageVector,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .height(56.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .padding(
                    horizontal = 8.dp,
                    vertical = 16.dp
                ),
            imageVector = startIcon,
            contentDescription = "",
            tint = Color(0xFFD9614C)
        )
        Text(
            modifier = Modifier
                .padding(horizontal = 8.dp),
            text = text,
            fontWeight = FontWeight.Medium,
            fontSize = TextUnit(16f, TextUnitType.Sp),
            color = textColor
        )
    }
}

@Composable
fun AccountCenterButton(
    modifier: Modifier = Modifier,
    text: String,
    textColor: Color,
    startIcon: ImageVector,
    endIcon: ImageVector,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AccountCenterButton(
            text = text,
            textColor = textColor,
            startIcon = startIcon,
            onClick = {}
        )
        Icon(
            modifier = Modifier
                .padding(
                    horizontal = 8.dp,
                    vertical = 16.dp
                ),
            imageVector = endIcon,
            contentDescription = "",
            tint = Color(0xFF595550)
        )
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
        startIcon = Icons.AutoMirrored.Filled.ExitToApp,
        onClick = {}
    )
}

@Preview
@Composable
private fun AccountCenterButtonPreview1() {
    AccountCenterButton(
        text = "Edit profile",
        textColor = Color(0xFF595550),
        startIcon = Icons.Filled.Edit,
        endIcon = Icons.AutoMirrored.Filled.KeyboardArrowRight,
        onClick = {}
    )
}