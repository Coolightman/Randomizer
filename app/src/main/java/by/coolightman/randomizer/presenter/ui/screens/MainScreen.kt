package by.coolightman.randomizer.presenter.ui.screens

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import by.coolightman.randomizer.R
import by.coolightman.randomizer.domain.model.CoinFace
import by.coolightman.randomizer.domain.model.DiceFace
import by.coolightman.randomizer.domain.model.RandomMode
import by.coolightman.randomizer.domain.model.ResultItem
import by.coolightman.randomizer.presenter.ui.components.NumberRangeSelect
import by.coolightman.randomizer.presenter.ui.components.RandomModeChip
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

    val scrollState = rememberScrollState()
    val listState = rememberLazyListState()

    val isSpecialMenuVisible by remember(uiState.selectedMode) {
        mutableStateOf(uiState.selectedMode == RandomMode.SPECIAL)
    }

    var minSpecialValue by remember(uiState.specialRangeMin) {
        mutableStateOf(uiState.specialRangeMin.toString())
    }
    var maxSpecialValue by remember(uiState.specialRangeMax) {
        mutableStateOf(uiState.specialRangeMax.toString())
    }
    var promptText by remember {
        mutableStateOf("")
    }
    LaunchedEffect(uiState.selectedMode, minSpecialValue, maxSpecialValue) {
        promptText = when (uiState.selectedMode) {
            RandomMode.SPECIAL -> "$minSpecialValue ≤ X ≤ $maxSpecialValue"
            RandomMode.DICE -> ""
            RandomMode.COIN -> ""
            else -> uiState.selectedMode.description
        }
    }
    LaunchedEffect(uiState.history.size) {
        listState.animateScrollToItem(0)
    }
    var scrollColumnHeight by remember {
        mutableStateOf(0)
    }

    Scaffold { paddingValues ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(scrollState)
                .onGloballyPositioned { pos ->
                    scrollColumnHeight = derivedStateOf { pos.size.height }.value
                }
        ) {
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            )
            {
                IconButton(onClick = { onSwitchTheme() }) {
                    Icon(
                        painter = painterResource(
                            if (uiState.isDarkMode) R.drawable.ic_light_mode_24
                            else R.drawable.ic_dark_mode_24
                        ),
                        contentDescription = "theme icon"
                    )
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(320.dp)
            ) {
                when (uiState.selectedMode) {
                    RandomMode.COIN -> {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .fillMaxWidth(0.6f)
                                .align(Alignment.Center)
                        ) {
                            Image(
                                painter = painterResource(uiState.tossedCoinFace.imgRes),
                                contentDescription = "coin face",
                                modifier = Modifier
                            )
                            Text(
                                text = stringResource(uiState.tossedCoinFace.descriptionRes),
                                style = MaterialTheme.typography.labelMedium.copy(
                                    color = Color.Gray.copy(0.5f)
                                )
                            )
                            Spacer(modifier = Modifier.height(40.dp))
                        }
                    }
                    RandomMode.DICE -> {
                        Image(
                            painter = painterResource(uiState.rolledDiceFace.imgRes),
                            contentDescription = "dice face",
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(bottom = 56.dp)
                                .size(120.dp)
                        )
                    }
                    else -> {
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
                }
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
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color(0xFFE0EBF1)
                ),
                contentPadding = PaddingValues(),
                modifier = Modifier
                    .fillMaxWidth(0.4f)
                    .height(56.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.linearGradient(
                                colors = listOf(
                                    Color(0xFF1F77C2),
                                    Color(0xFFA629DD),
                                    Color(0xFFE72B57)
                                )
                            )
                        )
                ) {
                    Text(
                        text = when (uiState.selectedMode) {
                            RandomMode.COIN -> "Toss"
                            RandomMode.DICE -> "Roll"
                            else -> "Generate"
                        },
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
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

            Spacer(modifier = Modifier.height(4.dp))

            if (uiState.history.isNotEmpty()) {
                LazyRow(
                    state = listState,
                    contentPadding = PaddingValues(horizontal = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                ) {
                    items(items = uiState.history, key = { it.createdAt }) { resultItem ->
                        when (resultItem.type) {
                            RandomMode.COIN -> {
                                Card(
                                    shape = CircleShape,
                                    modifier = Modifier.fillMaxHeight()
                                ) {
                                    Image(
                                        painter = painterResource(CoinFace.values()[resultItem.value].imgRes),
                                        contentDescription = "coin img"
                                    )
                                }
                            }
                            RandomMode.DICE -> {
                                Card(
                                    shape = RoundedCornerShape(5.dp),
                                    modifier = Modifier.height(35.dp)
                                ) {
                                    Image(
                                        painter = painterResource(DiceFace.values()[resultItem.value].imgRes),
                                        contentDescription = "dice img"
                                    )
                                }
                            }
                            else -> {
                                Card(
                                    modifier = Modifier.height(30.dp)
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(horizontal = 4.dp)
                                    ) {
                                        Text(text = resultItem.value.toString())
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                Spacer(modifier = Modifier.height(40.dp))
            }

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
                    onClick = {
                        onClickMode(it)
                        scope.launch {
                            scrollState.animateScrollTo(scrollColumnHeight)
                        }
                    }
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
        val history = listOf(
            ResultItem(1, RandomMode.DICE, 3),
            ResultItem(2, RandomMode.COIN, 0),
            ResultItem(3, RandomMode.SPECIAL, 23),
        )
        MainScreen(
            uiState = MainUiState("13", RandomMode.COIN, history = history),
            onClickGenerate = {},
            onClickMode = {},
            onClickPlusOne = {},
            onSwitchTheme = {},
            onUpdateSpecialRange = { _, _ -> }
        )
    }
}
