package com.tencent.kuikly.essayocr.pages

import com.tencent.kuikly.compose.ComposeContainer
import com.tencent.kuikly.compose.material3.Text
import com.tencent.kuikly.compose.setContent
import com.tencent.kuikly.compose.ui.Modifier
import com.tencent.kuikly.compose.foundation.layout.Column
import com.tencent.kuikly.compose.foundation.layout.fillMaxSize
import com.tencent.kuikly.compose.ui.unit.sp
import com.tencent.kuikly.core.annotations.Page
import androidx.compose.runtime.Composable

@Page("home")
class HomePage : ComposeContainer() {

    override fun willInit() {
        super.willInit()
        setContent { HomeScreen() }
    }
}

@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Essay OCR",
            fontSize = 24.sp
        )
    }
}