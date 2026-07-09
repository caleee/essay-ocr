package com.tencent.kuikly.essayocr.model

data class RecognitionBlock(
    val text: String,
    val confidence: Float,
    val x: Float,
    val y: Float,
    val width: Float,
    val height: Float
)