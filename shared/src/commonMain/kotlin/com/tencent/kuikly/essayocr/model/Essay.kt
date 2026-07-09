package com.tencent.kuikly.essayocr.model

data class Essay(
    val id: String,
    val title: String,
    val content: String,
    val createdAt: Long,
    val updatedAt: Long
)