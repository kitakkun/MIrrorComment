package com.github.kitakkun.mirrorcomment.ui.settings

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class HexColorVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        return TransformedText(
            text = AnnotatedString(text = "#" + text.text),
            offsetMapping = object : OffsetMapping {
                override fun originalToTransformed(offset: Int): Int {
                    return (offset + 1).coerceAtMost(text.text.length + 1)
                }

                override fun transformedToOriginal(offset: Int): Int {
                    return (offset - 1).coerceAtLeast(0)
                }
            }
        )
    }
}
