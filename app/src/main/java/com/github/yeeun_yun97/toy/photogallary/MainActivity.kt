package com.github.yeeun_yun97.toy.photogallary

import android.content.Intent
import android.os.Bundle
import android.view.DragEvent
import android.widget.Button
import android.widget.ImageView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.core.content.ContextCompat.startActivity
import com.bumptech.glide.Glide
import com.microsoft.device.dualscreen.draganddrop.DropContainer
import com.microsoft.device.dualscreen.draganddrop.MimeType
import com.skydoves.landscapist.glide.GlideImage

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                var imageUrl by remember {
                    mutableStateOf(
                        "https://thumbs.dreamstime.com/b/vector-empty-transparent-background-transparency-grid-seamless-pattern-171149540.jpg",
                    )
                }
//                var isDroppingImage by remember { mutableStateOf(false) }
//                var isDroppingItem by remember { mutableStateOf(false) }
//                DropContainer(
//                    modifier = Modifier,
//                    onDrag = { inBounds, isDragging ->
//                        if (!inBounds || !isDragging) {
//                            isDroppingImage = false
//                        }
//                        isDroppingItem = isDragging
//                    }
//                ) { dragData ->
//                    dragData?.also {
//                        if (dragData.type == MimeType.IMAGE_JPEG) {
//                            isDroppingImage = isDroppingItem
//                            if (!isDroppingItem) {
//                                imageUrl = dragData.data as String
//                            }
//                        }
//                    }
//                    GlideImage(
//                        modifier = Modifier
//                            .size(300.dp)
//                            .pointerInput(Unit) {
//                                detectDragGesturesAfterLongPress { change, dragAmount ->
//                                }
//                            },
//                        imageModel = imageUrl
//                    )

                    AndroidView(
                        modifier = Modifier.size(300.dp),
                        factory = { it ->
                            val imageView = ImageView(it)
                            Glide.with(it).load(imageUrl).centerCrop().into(imageView)
                            imageView.setOnDragListener { view, dragEvent ->
                                if (dragEvent.action == DragEvent.ACTION_DROP) {
                                    val imgView = view as ImageView
                                    val item = dragEvent.clipData.getItemAt(0)
                                    val dragData: String = item.uri.toString()
                                    imageUrl = dragData
                                    Glide.with(it).load(dragData).centerCrop().into(imgView)
                                    view.invalidate()
                                }
                                return@setOnDragListener true
                            }
                            imageView
                        })


                }
                Spacer(modifier = Modifier.height(40.dp))
                Text("hello, world!")

                Spacer(modifier = Modifier.height(15.dp))

                Button(onClick = { openInMulti() }){
                    Text("click me to open multi window")
                }


                Spacer(modifier = Modifier.height(15.dp))

//            }
            //DragAndDropApp()

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