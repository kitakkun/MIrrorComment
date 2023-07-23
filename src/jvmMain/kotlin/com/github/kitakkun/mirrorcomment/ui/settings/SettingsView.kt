package com.github.kitakkun.mirrorcomment.ui.settings

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator

@Composable
fun SettingsView(
    uiState: SettingsState,
    onChangeSpeakingEnabled: (Boolean) -> Unit,
    onChangeVoiceVoxServerUrl: (String) -> Unit,
    onSpeakerUpdated: (String) -> Unit,
    onClickCancel: () -> Unit,
    onClickApply: () -> Unit,
) {
    var presetDropdownExpanded by remember { mutableStateOf(false) }

    val navigator = LocalNavigator.current

    Column {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "設定",
                        modifier = Modifier.size(32.dp),
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = "設定",
                        style = MaterialTheme.typography.h5,
                    )
                }
                Spacer(Modifier.height(16.dp))
            }
            item {
                Text(
                    text = "読み上げ",
                    style = MaterialTheme.typography.h6,
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(text = "コメントを読み上げる")
                    Spacer(Modifier.weight(1f))
                    Switch(checked = uiState.speakingEnabled, onCheckedChange = onChangeSpeakingEnabled)
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.alpha(if (uiState.speakingEnabled) 1f else 0.5f)
                ) {
                    Text(text = "VOICEVOXサーバーのURL")
                    Spacer(Modifier.weight(1f))
                    TextField(
                        value = uiState.voiceVoxServerUrl,
                        onValueChange = onChangeVoiceVoxServerUrl,
                        enabled = uiState.speakingEnabled,
                        modifier = Modifier.width(400.dp),
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
                Spacer(Modifier.height(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(text = "キャラクター")
                    Spacer(Modifier.weight(1f))
                    Box(
                        contentAlignment = Alignment.TopCenter
                    ) {
                        Text(
                            text = uiState.speakers.find { it.speakerUuid == uiState.speakerUUID }?.name ?: "Not Found",
                            modifier = Modifier
                                .border(
                                    width = 1.dp,
                                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.12f),
                                    shape = MaterialTheme.shapes.small,
                                )
                                .width(200.dp)
                                .padding(8.dp)
                                .clip(MaterialTheme.shapes.small)
                                .clickable { presetDropdownExpanded = true },
                        )
                        DropdownMenu(
                            expanded = presetDropdownExpanded,
                            onDismissRequest = { presetDropdownExpanded = false },
                        ) {
                            uiState.speakers.forEach { speaker ->
                                DropdownMenuItem(onClick = {
                                    onSpeakerUpdated(speaker.speakerUuid)
                                    presetDropdownExpanded = false
                                }) {
                                    Text(text = speaker.name)
                                }
                            }
                        }
                    }
                }
            }
            item {
                Text(
                    text = "その他",
                    style = MaterialTheme.typography.h6,
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clickable {
                            navigator?.push(LicenseScreen())
                        }
                        .padding(vertical = 16.dp)
                        .fillMaxWidth()
                ) {
                    Text(text = "OSSライセンス")
                    Spacer(Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                    )
                    Spacer(Modifier.weight(1f))
                    Icon(
                        imageVector = Icons.Default.ArrowForwardIos,
                        contentDescription = null,
                    )
                }
            }
        }
        Row(
            modifier = Modifier
                .align(Alignment.End)
                .padding(16.dp)
        ) {
            OutlinedButton(onClick = onClickCancel) {
                Text("キャンセル")
            }
            Spacer(Modifier.width(8.dp))
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
        onSpeakerUpdated = {},
    )
}
