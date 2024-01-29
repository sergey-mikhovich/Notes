package com.sergeymikhovich.notes.app.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.sergeymikhovich.notes.app.App
import com.sergeymikhovich.notes.app.ui.theme.NotesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as App).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContent {
            NotesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                }
            }
        }
    }
}

@Composable
fun Notes(modifier: Modifier = Modifier) {
    Column {
        NoteCard(
            modifier = modifier,
            header = "Purchases",
            content = "1. Bread\n2. Butter",
            { }
        )
    }
}

@Composable
fun NoteCard(
    modifier: Modifier = Modifier,
    header: String,
    content: String,
    onNoteClick: () -> Unit
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(8.dp),
        border = BorderStroke(
            width = 1.dp,
            color = Color.Gray
        ),
        shape = RoundedCornerShape(16.dp),
        onClick = onNoteClick
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                modifier = Modifier
                    .padding(bottom = 16.dp),
                text = header,
                fontSize = TextUnit(20f, TextUnitType.Sp),
                fontWeight = FontWeight.Medium
            )
            Text(text = content)
        }
    }
}

@Preview(device = Devices.DEFAULT)
@Composable
fun NotesPreview() {
    NotesTheme {
        Notes()
    }
}