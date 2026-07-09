package com.tencent.kuikly.essayocr.model

data class OCRResult(
    val blocks: List<RecognitionBlock>,
    val fullText: String,
    val confidence: Float
)