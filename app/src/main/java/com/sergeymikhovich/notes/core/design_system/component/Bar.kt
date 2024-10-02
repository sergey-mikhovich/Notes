package com.sergeymikhovich.notes.core.design_system.component

import android.app.Activity
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesSearchBar(
    modifier: Modifier = Modifier,
    active: Boolean,
    onActiveChange: (Boolean) -> Unit,
    onSearch: (String) -> Unit,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    var query by remember { mutableStateOf("") }

    val padding: Dp by animateDpAsState(
        targetValue =  if (active) 0.dp else 16.dp,
        label = "paddingSpec",
        animationSpec = tween(
            durationMillis = 160,
            easing = FastOutSlowInEasing
        )
    )
    val paddings by remember {
        derivedStateOf {
            if (!active)
                PaddingValues(start = padding, end = padding, bottom = padding)
            else
                PaddingValues(padding)
        }
    }

    StatusBarColorChanger(
        statusBarColor = if (active) Color(0xFFF5E9D3) else Color(0xFFFFFDF0),
        navigationBarColor = if (active) Color(0xFFF5E9D3) else Color(0xFFFFFDF0),
        isLightIcons = true
    )

    SearchBar(
        query = query,
        onQueryChange = { query = it },
        onSearch = onSearch,
        active = active,
        onActiveChange = onActiveChange,
        placeholder = placeholder,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        modifier = modifier
            .fillMaxWidth()
            .background(color = Color(0xFFFFFDF0))
            .then(
                Modifier.padding(paddings)
            ),
        colors = SearchBarDefaults.colors(
            containerColor = Color(0xFFF5E9D3)
        ),
        content = content
    )
}

@Composable
fun StatusBarColorChanger(
    statusBarColor: Color? = null,
    navigationBarColor: Color? = null,
    isLightIcons: Boolean = false
) {
    val window = (LocalContext.current as Activity).window
    val view = LocalView.current

    SideEffect {
        statusBarColor?.let {
            window.statusBarColor = it.toArgb()
        }

        navigationBarColor?.let {
            window.navigationBarColor = it.toArgb()
        }

        WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = isLightIcons
    }
}