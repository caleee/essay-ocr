package com.tencent.kuikly.essayocr.adapter

import android.content.Context
import android.graphics.Color
import com.tencent.kuikly.core.render.android.adapter.IKRColorParserAdapter
import com.tencent.kuikly.essayocr.SkinIniFile

class KRColorParserAdapter(context: Context) : IKRColorParserAdapter {

    private val colorIniFile = SkinIniFile(context).apply { load("configColor.ini") }

    override fun toColor(colorStr: String): Int? {
        if (!colorStr.contains(COLOR_UNIQUE_ID) && !colorStr.contains(COLOR_KUIKLY_TOKEN_PREFIX)) {
            return colorStr.toLongOrNull()?.toInt()
        }
        var token = colorStr
        if (colorStr.contains(COLOR_UNIQUE_ID)) {
            token = colorStr.substring(0, colorStr.indexOf(COLOR_UNIQUE_ID))
        }
        val colorHex = colorIniFile.get(COLOR_SECTION, token) ?: return null
        return try {
            Color.parseColor(colorHex)
        } catch (_: IllegalArgumentException) { null }
    }

    companion object {
        private const val COLOR_UNIQUE_ID = "_color_unique_id_"
        private const val COLOR_KUIKLY_TOKEN_PREFIX = "kuikly"
        private const val COLOR_SECTION = "Color"
    }
}