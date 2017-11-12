package com.upadhyay.nilay.tictactoedemo.util

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.Point
import android.graphics.Rect
import android.view.Display
import android.view.View

/**
 * Created by nilay on 11/9/2017.
 */

object AppUtils {

    fun takeScreenShot(activity: Activity): Bitmap {
        val view = activity.window.decorView
        view.isDrawingCacheEnabled = true
        view.buildDrawingCache()


        val b1 = view.drawingCache
        val frame = Rect()
        activity.window.decorView.getWindowVisibleDisplayFrame(frame)
        val statusBarHeight = frame.top

        val display = activity.windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        val width = size.x
        val height = size.y


        val b = Bitmap.createBitmap(b1, 0, statusBarHeight, width, height - statusBarHeight)
        view.destroyDrawingCache()
        return b
    }
}