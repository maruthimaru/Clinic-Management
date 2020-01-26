package com.example.medicalmanagement.helper

import android.content.Context
import android.graphics.*
import androidx.core.content.res.ResourcesCompat
import com.example.medicalmanagement.R

/**
 * Created by admin on 03-Apr-18.
 */

class CircleImage(var context: Context) {
    init {
    }
    fun circleBackground(color: Int, customText: String): Bitmap {
        // Create a Paint object for underlined text.
        // The Paint clas offers a full complement of typographical styling methods.
        val mPaintText = Paint()
        // Bounding box for text.
        val mBounds = Rect()
        // Initialize a new Bitmap object
        val bitmap = Bitmap.createBitmap(
                500, // Width
                470, // Height
                Bitmap.Config.ARGB_8888 // Config
        )

        // Initialize a new Canvas instance
        val canvas = Canvas(bitmap)

        // Draw a solid color to the canvas background
        canvas.drawColor(Color.TRANSPARENT)
        mPaintText.color = ResourcesCompat.getColor(context.resources,
                R.color.white, null)
        mPaintText.style = Paint.Style.FILL
        mPaintText.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        mPaintText.textSize = 220f
        mPaintText.isAntiAlias = false
        mPaintText.isDither = false
        // Initialize a new Paint instance to draw the CircleImage
        val paint = Paint()
        paint.style = Paint.Style.FILL
        paint.color = color
        paint.isAntiAlias = true
        paint.isDither = true

        // Calculate the available radius of canvas
        val radius = Math.min(canvas.width, canvas.height / 2)

        // Set a pixels value to padding around the circle
        val padding = 1

        val vWidth = canvas.width
        val vHeight = canvas.height
        val halfWidth = vWidth / 2
        val halfHeight = vHeight / 2
        // Finally, draw the circle on the canvas
        canvas.drawCircle(
                halfWidth.toFloat(), // cx
                halfHeight.toFloat(), // cy
                (radius - padding).toFloat(), // Radius
                paint // Paint
        )
        // Get bounding box for text to calculate where to draw it.
        mPaintText.getTextBounds(customText, 0, customText.length, mBounds)
        // Calculate x and y for text so it's centered.
        val x = halfWidth - mBounds.centerX()
        val y = halfHeight - mBounds.centerY()
        canvas.drawText(customText, x.toFloat(), y.toFloat(), mPaintText)
        return bitmap
        // Display the newly created bitmap on app interface
    }
}
