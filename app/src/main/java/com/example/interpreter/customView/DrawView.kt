package com.example.interpreter.customView

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.Log
import android.view.MotionEvent
import android.view.View

data class Line(var point1: Point, var point2: Point, var point3: Point, var point4: Point)
data class Position (var curX: Float, var curY: Float, var startX: Float, var startY: Float)
@SuppressLint("ClickableViewAccessibility")
class DrawView(context: Context?) : View(context) {
    private var p: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var path = Path()
    
    private var connections = mutableListOf<Line>()
    
    private var point1 = Point(0, 0)
    private var point2 = Point(0, 0)
    private var point3 = Point(0, 0)
    private var point4 = Point(0, 0)
    
    var position = Position(0f, 0f, 0f, 0f)
    
    private fun saveLine(point1: Point, point2: Point, point3: Point, point4: Point){
        connections.add(Line(point1, point2, point3, point4))
    }
    
    fun draw(point1: Point, point2: Point, point3: Point, point4: Point) {
        this.point1 = point1
        this.point2 = point2
        this.point3 = point3
        this.point4 = point4
        
        invalidate()
    }
    
    override fun onDraw(canvas: Canvas) {
        p.color = Color.CYAN
        
        path.reset()
        path.moveTo(point1.x.toFloat(), point1.y.toFloat())
        cubicTo()
        
        p.style = Paint.Style.STROKE
        canvas.drawPath(path, p)
    }
    
    private fun cubicTo() {
        path.cubicTo(
            point2.x.toFloat(), point2.y.toFloat(),
            point3.x.toFloat(), point3.y.toFloat(),
            point4.x.toFloat(), point4.y.toFloat()
        )
    }
    
    fun down(event: MotionEvent, touchPoint: com.example.interpreter.Point) {
        Log.i("hello2", "down")
        position.startX = touchPoint.x
        position.startY = touchPoint.y
    }
    fun move(event: MotionEvent, touchPoint: com.example.interpreter.Point) {
        Log.i("hello2", "move")
        position.curX = event.x + this.x + touchPoint.x
        position.curY = event.y + this.y + touchPoint.y
    
        draw(
            Point(position.startX.toInt(), position.startY.toInt()),
            Point(position.startX.toInt() + 400, position.startY.toInt()),
            Point(position.curX.toInt() - 400, position.curY.toInt()),
            Point(position.curX.toInt(), position.curY.toInt())
        )
    }
    fun up(event: MotionEvent, touchPoint: com.example.interpreter.Point) {
        Log.i("hello", "up")
        //TODO("redraw old lines")
    }
    
//    private val touchListener = OnTouchListener { it, event ->
//        when (event.action) {
//            MotionEvent.ACTION_DOWN -> {
//
//            }
//            MotionEvent.ACTION_MOVE -> {
//
//            }
//            MotionEvent.ACTION_UP -> {
//
//            }
//        }
//        true
//    }
    
    init {
        p.strokeWidth = 10f
        //this.setOnTouchListener(touchListener)
    }
}