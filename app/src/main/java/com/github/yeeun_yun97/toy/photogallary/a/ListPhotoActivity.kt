package com.github.yeeun_yun97.toy.photogallary.a

import android.content.ClipData
import android.content.ClipDescription
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.DragStartHelper
import com.bumptech.glide.Glide

class ListPhotoActivity : ComponentActivity() {

    private val list = listOf(
        "https://animalfriends.co.kr/web/product/big/202110/c22334f2a3163a26a25a1763e40ea227.jpg",
        "https://contents.sixshop.com/thumbnails/uploadedFiles/25350/product/image_1590041409507_750.jpg",
        "https://cphoto.asiae.co.kr/listimglink/7/2022011822055214307_1642511152.jpg",
        "https://img.insight.co.kr/static/2022/01/31/700/img_20220131173622_qu0gm4mh.webp",
        "http://www.sputnik.kr/article_img/202201/article_1641706730.jpg",
        "https://w.namu.la/s/4b15aabcb4ec82ad78de8ae1a9fcee59777cbf6b68c19d03b80bcde86817e511ce61c06b4e3d645d91335798672d3a1d82f2783abce8c6c698d8590220b619cf70be1b3fdff2257b5a5a2841112c661d21cd89e1f9e6e9e27a17638ed6a9e94c",
        "http://img.hani.co.kr/imgdb/resize/2018/0305/152012836902_20180305.JPG",
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSlOMbd4RpvOULdsA5C9Gc-l86GYeSQQyR2i5Q14j2wbFW8ltoIrQDYoLXeH7FP6ehTLDc&usqp=CAU",
        "https://m.animalfriends.co.kr/web/product/big/202102/a7af6abee9c55d71d86aeea5e6c88a8d.jpg"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Screen()
                Text("ListPhoto")
                Spacer(modifier = Modifier.height(15.dp))
            }
        }
    }

    @Composable
    @Preview
    fun Preview() {
        Screen()
    }

    @Composable
    fun Screen() {
        ImageList(list)
    }

    @Composable
    fun ImageList(items: List<String>) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LazyColumn {
                items(items) { imageUrl ->
                    ImageItem(
                        imageUrl = imageUrl
                    )
                }
            }
        }
    }

    @Composable
    fun ImageItem(
        imageUrl: String,
        modifier: Modifier = Modifier
    ) {
        Column {
            AndroidView(
                modifier = modifier.size(200.dp),
                factory = {
                    val imageView = ImageView(it)
                    Glide.with(it).load(imageUrl).centerCrop().into(imageView)


                    val listener = DragStartHelper.OnDragStartListener { _, _ ->
                        val clipData = ClipData(
                            ClipDescription(
                                imageUrl,
                                arrayOf("text/plain")
                            ),
                            ClipData.Item(imageUrl)
                        )
                        val flags = View.DRAG_FLAG_GLOBAL or View.DRAG_FLAG_GLOBAL_URI_READ
                        imageView.startDragAndDrop(
                            clipData,
                            View.DragShadowBuilder(imageView),
                            null,
                            flags
                        )
                    }
                    DragStartHelper(imageView, listener).apply { attach() }
                    imageView
                }
            )
            Spacer(
                modifier = modifier.size(10.dp)
            )
        }
    }




}