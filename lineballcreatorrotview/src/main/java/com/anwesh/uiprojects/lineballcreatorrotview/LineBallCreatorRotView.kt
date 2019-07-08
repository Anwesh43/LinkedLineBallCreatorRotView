package com.anwesh.uiprojects.lineballcreatorrotview

/**
 * Created by anweshmishra on 08/07/19.
 */

import android.view.View
import android.view.MotionEvent
import android.app.Activity
import android.content.Context
import android.graphics.Paint
import android.graphics.Canvas
import android.graphics.Color

val nodes : Int = 5
val balls : Int = 5
val scGap : Float = 0.05f
val scDiv : Double = 0.51
val strokeFactor : Int = 90
val sizeFactor : Float = 2.9f
val foreColor : Int = Color.parseColor("#1A237E")
val backColor : Int = Color.parseColor("#BDBDBD")
val rotDeg : Float = 90f

fun Int.inverse() : Float = 1f / this
fun Float.scaleFactor() : Float = Math.floor(this / scDiv).toFloat()
fun Float.maxScale(i : Int, n : Int) : Float = Math.max(0f, this - i * n.inverse())
fun Float.divideScale(i : Int, n : Int) : Float = Math.min(n.inverse(), maxScale(i, n)) * n
fun Float.mirrorValue(a : Int, b : Int) : Float {
    val k : Float = scaleFactor()
    return (1 - k) * a.inverse() + k * b.inverse()
}
fun Float.updateValue(dir : Float, a : Int, b : Int) : Float = mirrorValue(a, b) * dir * scGap

fun Canvas.drawBall(i : Int, sc : Float, size : Float, paint : Paint) {
    val sci : Float = sc.divideScale(i, balls)
    val sci1 : Float = sci.divideScale(0, 2)
    val sci2 : Float = sci.divideScale(1, 2)
    val r : Float = size / balls
    val origY : Float = -size
    val destY : Float = size - r - 2 * r * i
    save()
    translate(0f, origY + (destY - origY) * sci2)
    drawCircle(0f, 0f, r * sci1, paint)
    restore()
}

fun Canvas.drawBalls(sc : Float, size : Float, paint : Paint) {
    for (j in 0..(balls - 1)) {
        drawBall(j, sc, size, paint)
    }
}

fun Canvas.drawLBCRNode(i : Int, scale : Float, paint : Paint) {
    val w : Float = width.toFloat()
    val h : Float = height.toFloat()
    val gap : Float = w / (nodes + 1)
    val size : Float = gap / sizeFactor
    val sc1 : Float = scale.divideScale(0, 2)
    val sc2 : Float = scale.divideScale(1, 2)
    paint.strokeCap = Paint.Cap.ROUND
    paint.strokeWidth = Math.min(w, h) / strokeFactor
    paint.color = foreColor
    save()
    translate(gap * (i + 1), h / 2)
    rotate(rotDeg * sc2)
    drawBalls(sc1, size, paint)
    restore()
}
