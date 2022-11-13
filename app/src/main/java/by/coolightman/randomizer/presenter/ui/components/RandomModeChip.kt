package by.coolightman.randomizer.presenter.ui.components

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ElevatedFilterChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import by.coolightman.randomizer.domain.model.RandomMode

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RandomModeChip(
    selectedMode: RandomMode,
    chipMode: RandomMode,
    onClick: (RandomMode) -> Unit
) {
    ElevatedFilterChip(
        selected = chipMode == selectedMode,
        shape = CircleShape,
        label = { Text(text = chipMode.title) },
        onClick = { onClick(chipMode) }
    )
}