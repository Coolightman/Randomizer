package by.coolightman.randomizer.presenter.ui.screens

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import by.coolightman.randomizer.domain.model.RandomMode
import by.coolightman.randomizer.presenter.ui.components.NumberRangeSelect
import by.coolightman.randomizer.presenter.ui.components.RandomModeChip
import by.coolightman.randomizer.presenter.ui.components.SelectThemeRow
import by.coolightman.randomizer.presenter.ui.theme.RandomizerTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    uiState: MainUiState,
    onClickGenerate: () -> Unit,
    onClickMode: (RandomMode) -> Unit,
    onClickPlusOne: () -> Unit,
    onSwitchTheme: () -> Unit
) {
    var isDropMenuExpanded by remember {
        mutableStateOf(false)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                actions = {

                    IconButton(onClick = { isDropMenuExpanded = true }) {
                        Icon(
                            Icons.Default.MoreVert,
                            contentDescription = "more"
                        )
                    }

                    DropdownMenu(
                        expanded = isDropMenuExpanded,
                        onDismissRequest = { isDropMenuExpanded = false }
                    ) {
                        DropdownMenuItem(
                            text = {
                                SelectThemeRow(isDarkMode = uiState.isDarkMode)
                            },
                            onClick = {
                                isDropMenuExpanded = false
                                onSwitchTheme()
                            }
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxSize(0.5f)
            ) {
                Text(
                    text = uiState.result,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.displayLarge,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            Button(
                onClick = { onClickGenerate() },
                modifier = Modifier
                    .fillMaxWidth(0.4f)
                    .height(56.dp)
            ) {
                Text(
                    text = "Generate",
                    style = MaterialTheme.typography.titleMedium
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = uiState.selectedMode.title,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.labelMedium.copy(
                    color = Color.Gray.copy(0.5f)
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.fillMaxHeight(0.08f))

            NumberRangeSelect(
                selectedMode = uiState.selectedMode,
                selectedPlusOne = uiState.selectedPlusOne,
                onClickMode = { onClickMode(it) },
                onClickPlusOne = { onClickPlusOne() }
            )

            Spacer(modifier = Modifier.fillMaxHeight(0.08f))

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                RandomModeChip(
                    selectedMode = uiState.selectedMode,
                    chipMode = RandomMode.COIN,
                    onClick = { onClickMode(it) }
                )

                Spacer(modifier = Modifier.width(32.dp))

                RandomModeChip(
                    selectedMode = uiState.selectedMode,
                    chipMode = RandomMode.DICE,
                    onClick = { onClickMode(it) }
                )

                Spacer(modifier = Modifier.width(32.dp))

                RandomModeChip(
                    selectedMode = uiState.selectedMode,
                    chipMode = RandomMode.SPECIAL,
                    onClick = { onClickMode(it) }
                )
            }
        }
    }
}

@Preview(
    uiMode = UI_MODE_NIGHT_YES
)
@Composable
fun MainScreenPreview() {
    RandomizerTheme {
        MainScreen(
            uiState = MainUiState("13", RandomMode.TO_9),
            onClickGenerate = {},
            onClickMode = {},
            onClickPlusOne = {},
            onSwitchTheme = {}
        )
    }
}
