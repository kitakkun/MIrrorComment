package com.github.kitakkun.mirrorcomment.ui.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.rememberDialogState
import com.github.kitakkun.mirrorcomment.ext.toColor
import com.github.kitakkun.mirrorcomment.ext.toHexString
import com.godaddy.android.colorpicker.HsvColor
import com.godaddy.android.colorpicker.harmony.ColorHarmonyMode
import com.godaddy.android.colorpicker.harmony.HarmonyColorPicker

@Composable
fun ColorPickerDialog(
    initialColor: Color,
    onCloseRequest: () -> Unit,
    onConfirmColor: (Color) -> Unit,
    modifier: Modifier = Modifier,
) {
    var hsvColor by remember { mutableStateOf(HsvColor.from(initialColor)) }
    var hexStringColor by remember { mutableStateOf(initialColor.toHexString().drop(1)) }

    Dialog(
        state = rememberDialogState(
            position = WindowPosition.Aligned(Alignment.Center),
            height = 600.dp
        ),
        onCloseRequest = onCloseRequest,
        resizable = false,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            TextField(
                value = hexStringColor,
                onValueChange = {
                    hexStringColor = it
                    val color = "#$it".toColor() ?: return@TextField
                    hsvColor = HsvColor.from(color)
                },
                visualTransformation = HexColorVisualTransformation(),
                modifier = Modifier.width(200.dp),
                textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
                trailingIcon = {
                    ColorTile(
                        color = hsvColor.toColor(),
                        modifier = Modifier.size(32.dp)
                    )
                }
            )
            HarmonyColorPicker(
                harmonyMode = ColorHarmonyMode.SHADES,
                color = hsvColor,
                onColorChanged = {
                    hsvColor = it
                    hexStringColor = it.toColor().toHexString().drop(1)
                },
                modifier = Modifier.size(400.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Button(onClick = onCloseRequest) {
                    Text(text = "Cancel")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = { onConfirmColor(hsvColor.toColor()) }) {
                    Text(text = "OK")
                }
            }
        }
    }
}
