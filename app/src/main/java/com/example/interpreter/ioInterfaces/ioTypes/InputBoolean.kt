package com.example.interpreter.ioInterfaces.ioTypes

import android.content.Context
import android.util.AttributeSet
import android.widget.EditText
import com.example.interpreter.ioInterfaces.IO
import com.example.interpreter.customView.blockView.IOContainer
import com.example.interpreter.ioInterfaces.Input
import com.example.interpreter.ioInterfaces.Output
import com.example.interpreter.customView.blockView.BlockView
import com.example.interpreter.vm.Compiler
import com.example.interpreter.vm.instruction.Bool
import com.example.interpreter.vm.instruction.Instruction

class InputBoolean(
    override val name: IO.Name,
    override var parent: IOContainer,
    override val description: String = "",
    override val autocomplete: Boolean = false,
    override val isDefault: Boolean = true,
    override val isLink: Boolean = true
) : Input {
    override val type = IO.Type.Boolean
    override val color = "#6CD4FF"
    var default: Boolean? = false
    
    override fun parseValue(value: String) {
        default = !value.matches("""\s*false\s*""".toRegex())
    }
    
    override fun clone(): Input {
        return InputBoolean(name, parent, description, autocomplete, isDefault, isLink)
    }
    
    override fun getValue() = default
    
    override fun generateCoupleOutput(): Output {
        return OutputBoolean(IO.Name.Fake, FakeBlock(parent.view.context, this))
    }
    
    private class FakeBlock @JvmOverloads constructor(
        context: Context, val input: InputBoolean, attrs: AttributeSet? = null
    ) : BlockView(context, attrs) {
    
        override fun init() {
        
        }
        
        override fun compile(compiler: Compiler): List<Instruction> {
            android.util.Log.i("Input Bool", outputs.toString())
            return listOf(Bool(
                compiler,
                input.getValue() ?: throw Error("argument is null")
            ))
        }
    }
}