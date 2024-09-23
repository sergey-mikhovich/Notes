package com.sergeymikhovich.notes.core.design_system.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Text
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun WithTextCenteredDivider(
    modifier: Modifier = Modifier,
    centeredText: String
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Divider(modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            modifier = Modifier.wrapContentWidth(),
            text = centeredText,
            fontSize = 12.sp, color = Color(0xFF827D89)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Divider(modifier = Modifier.weight(1f))
    }
}

@Composable
@Preview
private fun WithTextCenteredDividerPreview() {
    WithTextCenteredDivider(centeredText = "or")
}