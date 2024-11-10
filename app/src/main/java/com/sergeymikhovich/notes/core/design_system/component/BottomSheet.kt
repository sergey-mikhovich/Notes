package com.sergeymikhovich.notes.core.design_system.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

data class BottomSheetItem(
    val leadingIcon: ImageVector? = null,
    val startText: String = "",
    val endText: String = "",
    val onClick: () -> Unit
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegularBottomSheet(
    modifier: Modifier = Modifier,
    items: List<BottomSheetItem>,
    onDismissRequest: () -> Unit,
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        shape = RectangleShape,
        dragHandle = null
    ) {
        Spacer(
            modifier = Modifier
                .background(Color(0xFFFFFDF0))
                .padding(top = 8.dp)
                .fillMaxWidth()
        )
        items.forEach { item ->
            TextButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFFFFDF0)),
                onClick = {
                    item.onClick()
                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            onDismissRequest()
                        }
                    }
                }
            ) {
                Row(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row {
                        item.leadingIcon?.let {
                            Image(
                                modifier = Modifier.padding(end = 16.dp),
                                imageVector = it,
                                contentDescription = ""
                            )
                            Text(
                                text = item.startText,
                                fontSize = TextUnit(16f, TextUnitType.Sp),
                                color = Color(0xFF595550),
                                letterSpacing = TextUnit(0.3f, TextUnitType.Sp),
                                fontWeight = FontWeight(490),
                                lineHeight = TextUnit(1.3F, TextUnitType.Em)
                            )
                        }
                    }
                    Text(
                        text = item.endText,
                        fontSize = TextUnit(16f, TextUnitType.Sp),
                        letterSpacing = TextUnit(0.3f, TextUnitType.Sp),
                        color = Color(0xFF595550),
                        fontWeight = FontWeight(490),
                        lineHeight = TextUnit(1.3F, TextUnitType.Em)
                    )
                }
            }
        }
        Spacer(
            modifier = Modifier
                .background(Color(0xFFFFFDF0))
                .navigationBarsPadding()
                .fillMaxWidth()
        )
    }
}