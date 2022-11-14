package by.coolightman.randomizer.presenter.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import by.coolightman.randomizer.R

@Composable
fun SelectThemeRow(
    isDarkMode: Boolean
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(
                if (isDarkMode) R.drawable.ic_light_mode_24
                else R.drawable.ic_dark_mode_24
            ),
            contentDescription = "theme icon"
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = stringResource(
                if (isDarkMode) R.string.light_theme
                else R.string.dark_theme
            )
        )
    }
}