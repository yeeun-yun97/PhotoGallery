package com.github.yeeun_yun97.toy.photogallary.ui

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.yeeun_yun97.toy.yyncompose.YNDragImageView
import com.github.yeeun_yun97.toy.yyncompose.YnDropImageView

private const val EMPTY_IMAGE =
    "https://thumbs.dreamstime.com/b/vector-empty-transparent-background-transparency-grid-seamless-pattern-171149540.jpg"
private const val HAMSTER_IMAGE =
    "https://img.insight.co.kr/static/2022/01/31/700/img_20220131173622_qu0gm4mh.webp"

class TestActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                YNDragImageView(HAMSTER_IMAGE)
                MySpacer(width = 20)
                YnDropImageView(EMPTY_IMAGE)
            }
        }
        //preprocessor //TODO preprocessor 알아보기
    }

    @Composable
    fun MySpacer(width: Int = 0, height: Int = 0) {
        Spacer(
            modifier = Modifier
                .height(height.dp)
                .width(width.dp)
        )
    }


}