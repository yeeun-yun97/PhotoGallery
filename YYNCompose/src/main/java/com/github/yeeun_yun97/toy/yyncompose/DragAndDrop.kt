package com.github.yeeun_yun97.toy.yyncompose

import android.content.ClipData
import android.content.ClipDescription
import android.graphics.Canvas
import android.graphics.Point
import android.view.DragEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.DragStartHelper
import com.bumptech.glide.Glide

class YNDragImageShadowBuilder(view: ImageView, width: Int, height: Int = width) :
    View.DragShadowBuilder(view) {
    private val density = view.context.resources.displayMetrics.density
    private val originWidth = view.layoutParams.width.dpToPx(density)
    private val originHeight = view.layoutParams.height.dpToPx(density)
    private val shadowWidth = width.dpToPx(density)
    private val shadowHeight = height.dpToPx(density)

    override fun onProvideShadowMetrics(outShadowSize: Point, outShadowTouchPoint: Point) {
        super.onProvideShadowMetrics(outShadowSize, outShadowTouchPoint)
        outShadowSize.set(shadowWidth, shadowHeight)
        outShadowTouchPoint.set(shadowWidth / 2, shadowHeight + (20.dpToPx(density)))
    }

    override fun onDrawShadow(canvas: Canvas) {
        canvas.scale(shadowWidth / originWidth.toFloat(), shadowHeight / originHeight.toFloat())
        view.draw(canvas)
    }
}

fun Int.dpToPx(density: Float): Int {
    return (density * this + 0.5f).toInt()
}


@Composable
fun YNDragImageView(imageUrl: String) {
    AndroidView(
        modifier = Modifier.size(150.dp),
        factory = {
            val imageView = ImageView(it)
            imageView.layoutParams = ViewGroup.LayoutParams(150, 150)
            Glide.with(it).load(imageUrl).centerCrop().into(imageView)
            val shadowBuilder = YNDragImageShadowBuilder(imageView, 80)

            val listener = DragStartHelper.OnDragStartListener { _, _ ->
                val clipData = ClipData(
                    ClipDescription(imageUrl, arrayOf("text/plain")),
                    ClipData.Item(imageUrl)
                )
                val flags = View.DRAG_FLAG_GLOBAL or View.DRAG_FLAG_GLOBAL_URI_READ
                imageView.startDragAndDrop(
                    clipData,
                    shadowBuilder,
                    null,
                    flags
                )
            }
            DragStartHelper(imageView, listener).apply { attach() }
            imageView
        },
        update = {
            Glide.with(it).load(imageUrl).centerCrop().into(it)
        }
    )
}

@Composable
fun YnDropImageView(imageUrl:String) {
    var imageUrl by remember { mutableStateOf(imageUrl) }
    AndroidView(
        modifier = Modifier.size(150.dp),
        factory = {
            val imageView = ImageView(it)
            Glide.with(it).load(imageUrl).centerCrop().into(imageView)
            imageView.setOnDragListener { _, dragEvent ->
                if (dragEvent.action == DragEvent.ACTION_DROP) {
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