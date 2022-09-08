package com.github.yeeun_yun97.toy.photogallary

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.DragEvent
import android.widget.ImageView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.bumptech.glide.Glide

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDrag = {
                                    change, dragAmount ->
                                Log.d("디버그 드래그 표시?", "드래그 중 - $dragAmount")
                            },
                            onDragEnd = {
                                Log.d("디버그 드래그 끝", "드래그 끝남남")
                            }
                        )
                    },
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                var imageUrl by remember {
                    mutableStateOf(
                        "https://thumbs.dreamstime.com/b/vector-empty-transparent-background-transparency-grid-seamless-pattern-171149540.jpg",
                    )
                }
                AndroidView(
                    modifier = Modifier.size(300.dp),
                    factory = { it ->
                        val imageView = ImageView(it)
                        Glide.with(it).load(imageUrl).centerCrop().into(imageView)
                        imageView.setOnDragListener { view, dragEvent ->
                            if (dragEvent.action == DragEvent.ACTION_DROP) {
                                val bounds = this@MainActivity.windowManager.currentWindowMetrics.bounds
                                val top = bounds.top
                                val start = bounds.left
                                val bottom = bounds.bottom
                                val end = bounds.right

                                Log.d("activity","좌표 = x(${start} ~ ${end}), y(${top} ~ ${bottom})")
                                val location = IntArray(2){0}
                                imageView.getLocationOnScreen(location)
                                Log.d("imageView","좌표 = x(${location[0]} ~ ${location[0]+imageView.right}), y(${location[1]} ~ ${location[1]+imageView.bottom})")
                                Log.d("드롭된 위치","좌표 = x(${dragEvent.x}), y(${dragEvent.y})")
                                val imgView = view as ImageView
                                val item = dragEvent.clipData.getItemAt(0)
                                val dragData: String = item.text.toString()
                                imageUrl = dragData
                                Glide.with(it).load(dragData).centerCrop().into(imgView)
                                view.invalidate()
                            }
                            return@setOnDragListener true
                        }
                        imageView
                    })
                Spacer(modifier = Modifier.height(40.dp))
                Text("hello, world!")
                Spacer(modifier = Modifier.height(15.dp))
                Button(onClick = { openInMulti() }) {
                    Text("click me to open multi window")
                }
                Spacer(modifier = Modifier.height(15.dp))
            }
        }
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