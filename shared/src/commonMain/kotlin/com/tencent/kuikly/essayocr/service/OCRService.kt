package com.tencent.kuikly.essayocr.service

import com.tencent.kuikly.essayocr.model.OCRResult

interface OCRService {
    suspend fun recognize(imageBytes: ByteArray): OCRResult
}