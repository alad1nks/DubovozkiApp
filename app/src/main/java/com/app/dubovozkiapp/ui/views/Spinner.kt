package com.app.dubovozkiapp.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ExpandMore
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Spinner(
    modifier: Modifier,
    expanded: Boolean,
    onClick: () -> Unit,
    onDismiss: () -> Unit,
    selected: MenuItem,
    items: List<MenuItem>,
    onSelect: (MenuItem) -> Unit
) {
    Box(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.surface)
                .clickable(onClick = onClick)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = selected.title,
                modifier = Modifier.padding(start = 8.dp, top = 8.dp, bottom = 8.dp),
                fontSize = 16.sp
            )
            if (!expanded) {
                Icon(
                    Icons.Outlined.ExpandMore,
                    contentDescription = null
                )
            }
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = onDismiss
        ) {
            items.forEach { item ->
                DropdownMenuItem(text = { Text(item.title) }, onClick = { onSelect(item) })
            }
        }
    }
}