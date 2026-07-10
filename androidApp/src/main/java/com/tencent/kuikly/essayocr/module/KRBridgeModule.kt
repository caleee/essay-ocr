package com.tencent.kuikly.essayocr.module

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.tencent.kuikly.core.render.android.export.KuiklyRenderBaseModule
import com.tencent.kuikly.core.render.android.export.KuiklyRenderCallback
import com.tencent.kuikly.essayocr.EssayOCRApp
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class KRBridgeModule : KuiklyRenderBaseModule() {

    override fun call(method: String, params: String?, callback: KuiklyRenderCallback?): Any? {
        return when (method) {
            "toast" -> toast(params)
            "copyToPasteboard" -> copyToPasteboard(params)
            "closePage" -> closePage(params)
            "log" -> log(params)
            "localServeTime" -> localServeTime(params, callback)
            "currentTimestamp" -> currentTimestamp(params)
            "dateFormatter" -> dateFormatter(params)
            else -> callback?.invoke(mapOf("code" to -1, "message" to "method not found"))
        }
    }

    private fun log(params: String?) {
        if (params == null) return
        Log.i("KuiklyRender", JSONObject(params).optString("content"))
    }

    private fun toast(params: String?) {
        if (params == null) return
        val msg = JSONObject(params).optString("content")
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(EssayOCRApp.application, msg, Toast.LENGTH_SHORT).show()
        }
    }

    private fun copyToPasteboard(params: String?) {
        if (params == null) return
        val paramJSON = JSONObject(params)
        (context?.getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager)?.also {
            it.setPrimaryClip(ClipData.newPlainText(MODULE_NAME, paramJSON.optString("content")))
        }
    }

    private fun closePage(params: String?) {
        Handler(Looper.getMainLooper()).post { activity?.finish() }
    }

    private fun localServeTime(params: String?, callback: KuiklyRenderCallback?) {
        callback?.invoke(mapOf("time" to (System.currentTimeMillis() / 1000)))
    }

    private fun currentTimestamp(params: String?): String = System.currentTimeMillis().toString()

    private fun dateFormatter(params: String?): String {
        val p = JSONObject(params ?: "{}")
        return SimpleDateFormat(p.optString("format"), Locale.getDefault()).format(Date(p.optLong("timeStamp")))
    }

    companion object {
        const val MODULE_NAME = "HRBridgeModule"
    }
}