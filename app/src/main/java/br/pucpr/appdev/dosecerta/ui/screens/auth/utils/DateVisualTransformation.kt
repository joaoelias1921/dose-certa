package br.pucpr.appdev.dosecerta.ui.screens.auth.utils

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class DateVisualTransformation(private val mask: String): VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val trimmedText = if (text.text.length >= 8) text.text.substring(0..7) else text.text
        var out = ""
        var maskIndex = 0
        trimmedText.forEach { char ->
            while (maskIndex < mask.length && mask[maskIndex] != 'd') {
                out += mask[maskIndex]
                maskIndex++
            }
            out += char
            maskIndex++
        }
        return TransformedText(AnnotatedString(out), DateOffsetMapping(mask, trimmedText))
    }
}

class DateOffsetMapping(private val mask: String, private val originalText: String): OffsetMapping {
    override fun originalToTransformed(offset: Int): Int {
        if (offset == 0) return 0
        if (offset <= 2) return offset
        if (offset <= 4) return offset + 1
        if (offset <= 8) return offset + 2
        return mask.length
    }

    override fun transformedToOriginal(offset: Int): Int {
        if (offset == 0) return 0
        if (offset <= 2) return offset
        if (offset <= 5) return offset - 1
        if (offset <= 10) return offset - 2
        return originalText.length
    }
}