package by.coolightman.randomizer.presenter.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import by.coolightman.randomizer.domain.model.RandomMode

@Composable
fun NumberRangeSelect(
    selectedMode: RandomMode,
    selectedPlusOne: Boolean,
    onClickMode: (RandomMode) -> Unit,
    onClickPlusOne: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {

        RandomModeChip(
            selectedMode = selectedMode,
            chipMode = if (selectedPlusOne) RandomMode.TO_10
            else RandomMode.TO_9,
            onClick = { onClickMode(it) }
        )

        Spacer(modifier = Modifier.width(16.dp))

        RandomModeChip(
            selectedMode = selectedMode,
            chipMode = if (selectedPlusOne) RandomMode.TO_100
            else RandomMode.TO_99,
            onClick = { onClickMode(it) }
        )

        Spacer(modifier = Modifier.width(16.dp))

        RandomModeChip(
            selectedMode = selectedMode,
            chipMode = if (selectedPlusOne) RandomMode.TO_1000
            else RandomMode.TO_999,
            onClick = { onClickMode(it) }
        )

        Spacer(modifier = Modifier.width(32.dp))

        PlusOneChip(
            selected = selectedPlusOne,
            onClick = { onClickPlusOne() }
        )
    }
}