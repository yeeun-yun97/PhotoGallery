package com.github.yeeun_yun97.toy.photogallary

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.DragEvent
import android.widget.ImageView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.bumptech.glide.Glide

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                DropImageContainer()
                MySpacer(height = 40)
                Button(onClick = { openInMulti() }) {
                    Text("open multi window")
                }
                MySpacer(height = 15)
            }
        }
    }

    @Composable
    fun MySpacer(width: Int = 0, height: Int = 0) {
        Spacer(
            modifier = Modifier
                .height(height.dp)
                .width(width.dp)
        )
    }

    @Composable
    fun DropImageContainer() {
        var imageUrl by remember {
            mutableStateOf("https://thumbs.dreamstime.com/b/vector-empty-transparent-background-transparency-grid-seamless-pattern-171149540.jpg")
        }
        AndroidView(
            modifier = Modifier.size(300.dp),
            factory = {
                val imageView = ImageView(it)
                Glide.with(it).load(imageUrl).centerCrop().into(imageView)
                imageView.setOnDragListener { view, dragEvent ->
                    if (dragEvent.action == DragEvent.ACTION_DROP) {
                        /*
                        val bounds = this@MainActivity.windowManager.currentWindowMetrics.bounds
                        val top = bounds.top
                        val start = bounds.left
                        val bottom = bounds.bottom
                        val end = bounds.right
                        Log.d("activity", "좌표 = x(${start} ~ ${end}), y(${top} ~ ${bottom})")
                        val location = IntArray(2) { 0 }
                        imageView.getLocationOnScreen(location)
                        Log.d(
                            "imageView",
                            "좌표 = x(${location[0]} ~ ${location[0] + imageView.right}), y(${location[1]} ~ ${location[1] + imageView.bottom})"
                        )
                        Log.d("드롭된 위치", "좌표 = x(${dragEvent.x}), y(${dragEvent.y})")
                        */
                        val item = dragEvent.clipData.getItemAt(0)
                        val dragData: String = item.text.toString()
                        imageUrl = dragData
                    }
                    return@setOnDragListener true
                }
                imageView
            },
            update = {
                Glide.with(it).load(imageUrl).centerCrop().into(it)
                it.invalidate()
            }

        )
    }

    private fun openInMulti() {
        if (!this.isInMultiWindowMode) {
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


}