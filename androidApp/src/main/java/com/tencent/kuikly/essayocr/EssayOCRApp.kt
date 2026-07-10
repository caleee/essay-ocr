package com.tencent.kuikly.essayocr

import android.app.Application

class EssayOCRApp : Application() {

    override fun onCreate() {
        super.onCreate()
        application = this
    }

    companion object {
        lateinit var application: Application
    }
}