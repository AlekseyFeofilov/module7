package com.example.interpreter.customView.blocks

import android.R
import android.content.Context
import android.util.AttributeSet
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Spinner
import com.example.interpreter.customView.blockView.BlockView
import com.example.interpreter.ioInterfaces.IO
import com.example.interpreter.ioInterfaces.ioTypes.InputBoolean
import com.example.interpreter.ioInterfaces.ioTypes.InputDouble


class CompareBlock @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : BlockView(context, attrs, defStyleAttr) {
    /*override fun compile() {
        super.compile()
    }*/
    private fun setSpinner() {
        val comparing = arrayListOf("<", ">", "=", "!=", "<=", ">=")
        
        (((binding.listOfInputLinearLayout.getChildAt(2) as LinearLayout)
            .getChildAt(0) as LinearLayout)
            .getChildAt(2) as Spinner)
            .adapter = ArrayAdapter(
            context,
            R.layout.simple_spinner_item,
            comparing.toList()
        )
    }
    
    init {
        addInput(InputDouble(IO.Name.First, this, "Compare this:"))
        val input = InputBoolean(IO.Name.By, this, "By sign:", isLink = false)
        addInput(input)
        addInput(InputDouble(IO.Name.Second, this, "With this:"))
        
        setHeader("Compare", "#EC8532")
        
        setSpinner()
    }
}