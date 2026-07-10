package com.tencent.kuikly.essayocr

import android.os.Handler
import android.os.Looper
import android.content.Context
import android.util.Log
import com.tencent.kuikly.essayocr.adapter.execOnSubThread
import java.io.IOException
import java.util.regex.Pattern

typealias OnLoadFinishListener = (Boolean) -> Unit

class SkinIniFile(private val context: Context) {

    private val sections: MutableMap<String, Section> = mutableMapOf()
    data class Section(val name: String, val values: MutableMap<String, String> = mutableMapOf())

    @Volatile
    private var hasLoaded = false

    fun load(assetsPath: String, onLoadFinishListener: OnLoadFinishListener? = null) {
        if (hasLoaded) { onLoadFinishListener?.invoke(true); return }
        execOnSubThread { loadInternal(assetsPath, onLoadFinishListener) }
    }

    @Synchronized
    private fun loadInternal(assetsPath: String, onLoadFinishListener: OnLoadFinishListener? = null) {
        if (hasLoaded) { onLoadFinishListener?.invoke(true); return }
        try {
            context.assets.open(assetsPath).use { inputStream ->
                inputStream.bufferedReader().use { reader ->
                    var curSection = ""
                    reader.forEachLine {
                        val line = it.trim()
                        curSection = readEachLine(line, curSection)
                    }
                }
            }
            hasLoaded = true
            Handler(Looper.getMainLooper()).post { onLoadFinishListener?.invoke(true) }
        } catch (e: IOException) {
            Log.e(TAG, "load assets failed: ${e.message}")
            Handler(Looper.getMainLooper()).post { onLoadFinishListener?.invoke(false) }
        }
    }

    @Synchronized
    fun get(sectionName: String, sectionKey: String, defaultValue: String? = null): String? {
        return sections[sectionName]?.values?.get(sectionKey) ?: defaultValue
    }

    @Synchronized
    fun getAllSectionsKey(sectionName: String): List<String> {
        return sections[sectionName]?.values?.keys?.toList() ?: emptyList()
    }

    private fun readEachLine(line: String, sectionName: String): String {
        var cur = sectionName
        if (SECTION_PATTERN.matcher(line).matches()) {
            cur = line.substring(1, line.length - 1)
            sections.getOrPut(cur) { Section(cur) }
        } else {
            val kv = line.split("=", limit = 2)
            if (kv.size == 2) {
                sections.getOrPut(cur) { Section(cur) }.values[kv[0]] = kv[1]
            }
        }
        return cur
    }

    companion object {
        private val SECTION_PATTERN = Pattern.compile("^\\[.*\\]$")
        private const val TAG = "SkinIniFile"
    }
}