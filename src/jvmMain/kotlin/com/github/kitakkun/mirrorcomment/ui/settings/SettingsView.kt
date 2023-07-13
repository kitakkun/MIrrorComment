package com.github.kitakkun.mirrorcomment.ui.settings

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SettingsView(
    uiState: SettingsState,
    onChangeChromeDriverPath: (String) -> Unit,
    onChangeSpeakingEnabled: (Boolean) -> Unit,
    onChangeVoiceVoxServerUrl: (String) -> Unit,
) {
    LazyColumn {
        item {
            Row() {
                Text(text = "ChromeDriverのパス")
                Spacer(Modifier.weight(1f))
                BasicTextField(
                    value = uiState.chromeDriverPath,
                    onValueChange = onChangeChromeDriverPath,
                )
            }
        }
        item {
            Row() {
                Text(text = "VOICEVOXサーバーのURL")
                Spacer(Modifier.weight(1f))
                BasicTextField(
                    value = uiState.voiceVoxServerUrl,
                    onValueChange = onChangeVoiceVoxServerUrl,
                )
            }
        }
        item {
            Row() {
                Text(text = "新規コメントを読み上げる")
                Spacer(Modifier.weight(1f))
                Switch(checked = uiState.speakingEnabled, onCheckedChange = onChangeSpeakingEnabled)
            }
        }
    }
}

@Preview
@Composable
private fun SettingsViewPreview() {
    SettingsView(
        uiState = SettingsState(
            chromeDriverPath = "/path/to/chromedriver",
            voiceVoxServerUrl = "http://localhost:50021",
            speakingEnabled = true,
        ),
        onChangeChromeDriverPath = {},
        onChangeVoiceVoxServerUrl = {},
        onChangeSpeakingEnabled = {},
    )
}
