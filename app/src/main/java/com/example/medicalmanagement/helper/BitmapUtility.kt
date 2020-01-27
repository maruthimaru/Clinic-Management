package com.example.medicalmanagement.helper

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.StrictMode
import android.util.Base64
import android.util.Log
import android.widget.ImageView
import com.jackandphantom.circularimageview.CircleImage


import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.net.URL
import java.nio.ByteBuffer


class BitmapUtility(private val context: Context) {
    private val staticData: StaticData
    private val circleImage: CircleImage


    init {
        staticData = StaticData(context)
        circleImage = CircleImage(context)
    }

    fun getBytes(bitmap: Bitmap?): ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap!!.compress(Bitmap.CompressFormat.PNG, 75, stream)
        return stream.toByteArray()
    }

    /* fun getBitmapFromURL(src: String): ByteArray? {
         try {
             val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
             StrictMode.setThreadPolicy(policy)
             val url = URL(src)
             Log.e(TAG, "getEmployee1: "+url)
             val connection = url.openConnection() as HttpURLConnection
             connection.setDoInput(true)
             connection.connect()
             val input = connection.getInputStream()

             Log.e(TAG, "getEmployee1: "+input)
             val image = BitmapFactory.decodeStream(input)
             val stream = ByteArrayOutputStream()
             Log.e(TAG, "getEmployee1: "+image)

           if(stream.size()>0){
             image.compress(Bitmap.CompressFormat.PNG, 70, stream)
                 return stream.toByteArray()
          }else{
               Log.e(TAG, "getEmployee: nostream")
                return null
             }
         } catch (e: IOException) {
             e.printStackTrace()
             Log.e(TAG, "getEmployee: exception")
             return null
         }

     }*/
    fun getBitmapFromURL(src: String): ByteArray? {
        try {
            Log.e("TAG", "getBitmapFromURL: $src")
            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
            val url = URL(src)
            val image = BitmapFactory.decodeStream(url.openConnection().getInputStream())
            val stream = ByteArrayOutputStream()
            image.compress(Bitmap.CompressFormat.PNG, 100, stream)
            return stream.toByteArray()
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }

    }

    //get the First letter from the string
    private fun getFirstLetter(input: String): String {
        return input.substring(0, 1)
    }

    @Throws(Exception::class)
    fun getImage(custName: String): ByteArray {
        //get the First Letter
        val firstLetter = getFirstLetter(custName.trim { it <= ' ' })
        //get the color for the user input from the resource file
        val userColorList = staticData.getSelectedName(firstLetter)

        val userName = userColorList[0].name
        val userColor = userColorList[0].color
        //Get the bitmap image
        var bmp: Bitmap? = null
        try {
//            bmp = circleImage.circleBackground(context.resources.getColor(userColor), userName!!)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        val result = getBytes(bmp)
        bmp!!.recycle()
        //Convert the bitmap to byte to Store the image in sqlite database
        return result
    }

    fun getStringImage(bm: Bitmap): String {
        val ba = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.PNG, 90, ba)
        return Base64.encodeToString(ba.toByteArray(), Base64.DEFAULT)
    }

    fun profileImage(url1: String): Bitmap? {
        var image: Bitmap? = null
        try {
            val url = URL(url1)
            image = BitmapFactory.decodeStream(url.openConnection().getInputStream())
        } catch (e: IOException) {
            println(e)
            Log.e("ERR", "ERRR" + e.cause)
        }

        return image
    }

    fun getImageBytes(bitmap: Bitmap): ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        return stream.toByteArray()
    }

    fun getImageBytesarray(bitmap: Bitmap): ByteArray {
        val lnth = bitmap.byteCount
        val dst = ByteBuffer.allocate(lnth)
        bitmap.copyPixelsToBuffer(dst)
        return dst.array()
    }

    fun getImage(image: ByteArray): Bitmap {

        Log.e("TAG", "image size : " + image.size + " = " + image)

        return BitmapFactory.decodeByteArray(image, 0, image.size)
    }

    @Throws(IOException::class)
    fun getBytes(inputStream: InputStream, context: Context): ByteArray {
        val byteBuffer = ByteArrayOutputStream()
        inputStream.buffered(1024).reader().forEachLine {
            byteBuffer.bufferedWriter().write(it)
        }
        return byteBuffer.toByteArray()
    }

    // convert from byte array to bitmap
    fun getStringImage(image: ByteArray): String {
        val bitmap = BitmapFactory.decodeByteArray(image, 0, image.size)
        val ba = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 90, ba)
        val by = ba.toByteArray()
        return Base64.encodeToString(by, Base64.DEFAULT)
    }

    fun getByteArrayFromImageURL(url: String): String? {

        try {
            val imageUrl = URL(url)
            val ucon = imageUrl.openConnection()
            val `is` = ucon.getInputStream()
            val baos = ByteArrayOutputStream()
            val buffer = ByteArray(1024)
            var read = 0
            do {
                val len1 = `is`.read(buffer)
                if (len1 == -1) {
                    break
                }
                baos.write(buffer, 0, len1)//Write new file
            } while (true)
            baos.flush()
            return Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT)
        } catch (e: Exception) {
            Log.d("Error", e.toString())
        }

        return null
    }

    fun base64toBitmap(image: String): Bitmap {
         var decodedString = Base64.decode(image, Base64.DEFAULT)
       return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
    }

    fun imageView2Bitmap(view: ImageView): Bitmap {
        return (view.drawable as BitmapDrawable).bitmap
    }
}
