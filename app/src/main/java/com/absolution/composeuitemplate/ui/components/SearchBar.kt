package com.absolution.composeuitemplate.ui.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape

@Composable
fun SearchBar(
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit,
    onClear: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(50),
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        shape = shape,
        placeholder = {
            Text(placeholder)
        },
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.Blue.copy(alpha = 0.08f),
            focusedContainerColor = Color.Blue.copy(alpha = 0.08f),
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
        ),
        leadingIcon = {
            Icon(
                Icons.Rounded.Search,
                contentDescription = null,
                tint = Color.Blue
            )
        },
        trailingIcon = {
            if (value.isNotEmpty()) {
                IconButton(onClick = onClear) {
                    Icon(
                        Icons.Rounded.Clear,
                        contentDescription = null,
                    )
                }
            }
        }
    )
}