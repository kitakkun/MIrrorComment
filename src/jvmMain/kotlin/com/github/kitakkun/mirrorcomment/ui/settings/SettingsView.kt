package com.github.kitakkun.mirrorcomment.ui.settings

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SettingsView(
    uiState: SettingsState,
    onChangeChromeDriverPath: (String) -> Unit,
    onChangeSpeakingEnabled: (Boolean) -> Unit,
    onChangeVoiceVoxServerUrl: (String) -> Unit,
    onClickCancel: () -> Unit,
    onClickApply: () -> Unit,
) {
    Column {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(text = "ChromeDriverのパス")
                    Spacer(Modifier.weight(1f))
                    TextField(
                        value = uiState.chromeDriverPath,
                        onValueChange = onChangeChromeDriverPath,
                        modifier = Modifier.width(500.dp),
                    )
                }
            }
            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(text = "VOICEVOXサーバーのURL")
                    Spacer(Modifier.weight(1f))
                    TextField(
                        value = uiState.voiceVoxServerUrl,
                        onValueChange = onChangeVoiceVoxServerUrl,
                        modifier = Modifier.width(500.dp),
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
        Row(
            modifier = Modifier.align(Alignment.End)
        ) {
            OutlinedButton(onClick = onClickCancel) {
                Text("キャンセル")
            }
            Button(onClick = onClickApply) {
                Text("適用して閉じる")
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
        onClickCancel = {},
        onClickApply = {},
    )
}
