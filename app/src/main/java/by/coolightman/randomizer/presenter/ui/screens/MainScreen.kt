package by.coolightman.randomizer.presenter.ui.screens

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import by.coolightman.randomizer.R
import by.coolightman.randomizer.presenter.ui.theme.RandomizerTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    result: String,
    onClickGenerate: () -> Unit
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
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        ) {
                            Text(text = stringResource(R.string.dark_theme))
                            Spacer(modifier = Modifier.width(8.dp))
                            Switch(
                                checked = true,
                                onCheckedChange = {}
                            )
                        }
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
                    text = result,
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
                    text = "GENERATE",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}

@Preview(
    uiMode = UI_MODE_NIGHT_YES,
    showSystemUi = true
)
@Preview(showSystemUi = true)
@Composable
fun MainScreenPreview() {
    RandomizerTheme {
        MainScreen(
            result = "00",
            onClickGenerate = {})
    }
}
