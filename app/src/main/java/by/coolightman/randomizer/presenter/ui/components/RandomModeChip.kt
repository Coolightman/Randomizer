package by.coolightman.randomizer.presenter.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ElevatedFilterChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import by.coolightman.randomizer.domain.model.RandomMode

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RandomModeChip(
    selectedMode: RandomMode,
    chipMode: RandomMode,
    leadingIcon: @Composable (() -> Unit)? = null,
    onClick: (RandomMode) -> Unit
) {
    ElevatedFilterChip(
        selected = chipMode == selectedMode,
        shape = CircleShape,
        leadingIcon = { leadingIcon?.invoke() },
        label = { Text(text = chipMode.title) },
        onClick = { onClick(chipMode) },
        modifier = Modifier
            .height(40.dp)
            .animateContentSize()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlusOneChip(
    selected: Boolean,
    onClick: () -> Unit
) {
    ElevatedFilterChip(
        selected = selected,
        shape = CircleShape,
        label = { Text(text = "+1") },
        onClick = { onClick() },
        modifier = Modifier.height(40.dp)
    )
}