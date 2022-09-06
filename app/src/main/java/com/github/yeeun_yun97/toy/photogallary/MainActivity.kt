package com.github.yeeun_yun97.toy.photogallary

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import com.skydoves.landscapist.glide.GlideImage
import org.intellij.lang.annotations.JdkConstants

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                GlideImage(
                    modifier = Modifier
                        .size(300.dp)
                        .pointerInput(Unit) {
                                            detectDragGesturesAfterLongPress { change, dragAmount ->


                                            }
                        },
                    imageModel = "https://thumbs.dreamstime.com/b/vector-empty-transparent-background-transparency-grid-seamless-pattern-171149540.jpg",

                    )
                Spacer(modifier = Modifier.height(40.dp))
                Text("hello, world!")
                Spacer(modifier = Modifier.height(15.dp))
            }
        }
    }





    override fun onResume() {
        super.onResume()
        if (!this.isInMultiWindowMode) {
            openInMulti()
        }
    }

    private fun openInMulti() {
        val intent = Intent(this@MainActivity, ListPhotoActivity::class.java)
        intent.addFlags(
            Intent.FLAG_ACTIVITY_LAUNCH_ADJACENT
                    or
                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK
                    or
                    Intent.FLAG_ACTIVITY_NEW_TASK
        )
        startActivity(intent)
    }
}