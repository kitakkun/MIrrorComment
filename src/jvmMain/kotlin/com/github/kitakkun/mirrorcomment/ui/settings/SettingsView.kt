package com.github.kitakkun.mirrorcomment.ui.settings

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Error
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SettingsView(
    uiState: SettingsState,
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
                    Text(text = "VOICEVOXサーバーのURL")
                    Spacer(Modifier.weight(1f))
                    TextField(
                        value = uiState.voiceVoxServerUrl,
                        onValueChange = onChangeVoiceVoxServerUrl,
                        modifier = Modifier.width(500.dp),
                        placeholder = { Text("例）http://127.0.0.1:50021") },
                        trailingIcon = {
                            if (uiState.checkingVoiceVoxServer) {
                                CircularProgressIndicator()
                            } else if (uiState.isVoiceVoxServerRunning) {
                                Row {
                                    Text(
                                        text = "起動中",
                                        color = MaterialTheme.colors.primary,
                                    )
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = "VOICEVOXサーバーが起動しています",
                                        tint = MaterialTheme.colors.primary,
                                    )
                                }
                            } else {
                                Row {
                                    Text(
                                        text = "不正なURL",
                                        color = MaterialTheme.colors.error,
                                    )
                                    Icon(
                                        imageVector = Icons.Default.Error,
                                        contentDescription = "VOICEVOXサーバーが起動していません",
                                        tint = MaterialTheme.colors.error,
                                    )
                                }
                            }
                        }
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
            voiceVoxServerUrl = "http://localhost:50021",
            speakingEnabled = true,
        ),
        onChangeVoiceVoxServerUrl = {},
        onChangeSpeakingEnabled = {},
        onClickCancel = {},
        onClickApply = {},
    )
}
