package com.example.medicalmanagement.helper

import android.content.Context
import android.graphics.*
import android.graphics.Shader.TileMode
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.ImageView

class Zoomimage : ImageView {
    private var bitmap: Bitmap? = null
    @get:JvmName("getActivity") private var matrix: Matrix? = null
    private var paint: Paint? = null
    private var shader: BitmapShader? = null
    private val sizeOfMagnifier = 350
    private var zoomPos: PointF? = null
    private var zooming = false

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    private fun init() {
        this.zoomPos = PointF(0.0f, 0.0f)
        this.matrix = Matrix()
        this.paint = Paint()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val action = event.action
        this.zoomPos!!.x = event.x
        this.zoomPos!!.y = event.y
        when (action) {
            0, 2 -> {
                this.zooming = true
                invalidate()
            }
            1, 3 -> {
                this.zooming = false
                invalidate()
            }
        }
        return true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (this.zooming) {
            this.bitmap = drawingCache
            this.shader = BitmapShader(this.bitmap!!, TileMode.CLAMP, TileMode.CLAMP)
            this.paint = Paint()
            this.paint!!.shader = this.shader
            this.matrix!!.reset()
            this.matrix!!.postScale(2.0f, 2.0f, this.zoomPos!!.x, this.zoomPos!!.y)
            this.paint!!.shader.setLocalMatrix(this.matrix)
            canvas.drawCircle(this.zoomPos!!.x, this.zoomPos!!.y, this.sizeOfMagnifier.toFloat(), this.paint!!)
            return
        }
        buildDrawingCache()
    }
}
