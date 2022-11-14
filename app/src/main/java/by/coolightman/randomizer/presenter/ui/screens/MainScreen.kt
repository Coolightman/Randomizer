package by.coolightman.randomizer.presenter.ui.screens

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import by.coolightman.randomizer.R
import by.coolightman.randomizer.domain.model.RandomMode
import by.coolightman.randomizer.presenter.ui.components.NumberRangeSelect
import by.coolightman.randomizer.presenter.ui.components.RandomModeChip
import by.coolightman.randomizer.presenter.ui.components.SelectThemeRow
import by.coolightman.randomizer.presenter.ui.theme.RandomizerTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    uiState: MainUiState,
    onClickGenerate: () -> Unit,
    onClickMode: (RandomMode) -> Unit,
    onClickPlusOne: () -> Unit,
    onSwitchTheme: () -> Unit,
    onUpdateSpecialRange: (max: Int, min: Int) -> Unit
) {

    val focusManager = LocalFocusManager.current
    val scope = rememberCoroutineScope()

    var isDropMenuExpanded by remember {
        mutableStateOf(false)
    }

    val isSpecialMenuVisible by remember(uiState.selectedMode) {
        mutableStateOf(uiState.selectedMode == RandomMode.SPECIAL)
    }

    var minSpecialValue by remember {
        mutableStateOf("0")
    }
    var maxSpecialValue by remember {
        mutableStateOf("47")
    }
    var promptText by remember {
        mutableStateOf("")
    }
    LaunchedEffect(uiState.selectedMode, minSpecialValue, maxSpecialValue) {
        promptText = if (uiState.selectedMode == RandomMode.SPECIAL) {
            "$minSpecialValue ≤ X ≤ $maxSpecialValue"
        } else {
            uiState.selectedMode.title
        }
    }

    val scrollState = rememberScrollState()

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
                .verticalScroll(scrollState)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(320.dp)
            ) {
                Text(
                    text = uiState.result,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.displayLarge.copy(
                        fontSize = 72.sp
                    ),
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(bottom = 56.dp)
                )
            }

            Button(
                onClick = {
                    if (uiState.selectedMode == RandomMode.SPECIAL) {
                        if (minSpecialValue.isNotEmpty() && maxSpecialValue.isNotEmpty()) {
                            onUpdateSpecialRange(minSpecialValue.toInt(), maxSpecialValue.toInt())
                            onClickGenerate()
                            focusManager.clearFocus()
                        }
                    } else {
                        onClickGenerate()
                    }
                    scope.launch {
                        scrollState.animateScrollTo(0)
                    }
                },
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
                text = promptText,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.labelMedium.copy(
                    color = Color.Gray.copy(0.5f)
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            NumberRangeSelect(
                selectedMode = uiState.selectedMode,
                selectedPlusOne = uiState.selectedPlusOne,
                onClickMode = { onClickMode(it) },
                onClickPlusOne = { onClickPlusOne() }
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                RandomModeChip(
                    selectedMode = uiState.selectedMode,
                    chipMode = RandomMode.COIN,
                    leadingIcon = {
                        Icon(
                            painter = painterResource(R.drawable.ic_coin_24),
                            contentDescription = "coin"
                        )
                    },
                    onClick = { onClickMode(it) }
                )

                RandomModeChip(
                    selectedMode = uiState.selectedMode,
                    chipMode = RandomMode.DICE,
                    leadingIcon = {
                        Icon(
                            painter = painterResource(R.drawable.ic_dice_24),
                            contentDescription = "dice"
                        )
                    },
                    onClick = { onClickMode(it) }
                )

                RandomModeChip(
                    selectedMode = uiState.selectedMode,
                    chipMode = RandomMode.SPECIAL,
                    leadingIcon = {
                        Icon(
                            painter = painterResource(R.drawable.ic_special_24),
                            contentDescription = "special"
                        )
                    },
                    onClick = { onClickMode(it) }
                )
            }
            AnimatedVisibility(
                visible = isSpecialMenuVisible
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        OutlinedTextField(
                            value = minSpecialValue,
                            shape = RoundedCornerShape(16.dp),
                            placeholder = { Text(text = "min") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            maxLines = 1,
                            onValueChange = { value ->
                                if (value.length <= 6) {
                                    minSpecialValue = value.filter { it.isDigit() }
                                }
                            },
                            modifier = Modifier.width(90.dp)
                        )

                        OutlinedTextField(
                            value = maxSpecialValue,
                            shape = RoundedCornerShape(16.dp),
                            placeholder = { Text(text = "max") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            maxLines = 1,
                            onValueChange = { value ->
                                if (value.length <= 6) {
                                    maxSpecialValue = value.filter { it.isDigit() }
                                }
                            },
                            modifier = Modifier.width(90.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun MainScreenPreview() {
    RandomizerTheme {
        MainScreen(
            uiState = MainUiState("13", RandomMode.TO_9),
            onClickGenerate = {},
            onClickMode = {},
            onClickPlusOne = {},
            onSwitchTheme = {},
            onUpdateSpecialRange = { _, _ -> }
        )
    }
}
