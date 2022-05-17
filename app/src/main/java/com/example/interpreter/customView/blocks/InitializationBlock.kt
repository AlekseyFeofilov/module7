package com.example.interpreter.customView.blocks

import android.content.Context
import android.util.AttributeSet
import com.example.interpreter.customView.blockView.BlockView
import com.example.interpreter.ioInterfaces.IO
import com.example.interpreter.ioInterfaces.ioTypes.InputString
import com.example.interpreter.vm.Compiler
import com.example.interpreter.vm.Env
import com.example.interpreter.vm.instruction.*
import com.example.interpreter.vm.instruction.Number
import kotlin.text.String

class InitializationBlock @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : BlockView(context, attrs, defStyleAttr) {
    override fun compile(compiler: Compiler): List<Instruction> {
        super.compile(compiler)
        return initVariables(compiler)
    }
    
    private fun initVariables(compiler: Compiler): MutableList<Instruction> {
        val initializationList = mutableListOf<Instruction>()
        
        inputs.forEach { it ->
            val value = (it.first as InputString).default
            
            if (value != null) {
                val initializations = value.split(",")
                
                initializations.forEach { initialization ->
                    var instruction = when (it.first.name) {
                        IO.Name.Double -> Number(compiler)
                        IO.Name.String -> String(compiler, "")
                        else -> Bool(compiler, true)
                    }
                    
                    val assignment = """^\s*([_A-Za-z][_A-Za-z\d]*)\s*(=\s*)?(.+)?$""".toRegex()
                            .find(initialization)
                    
                    if (initialization != assignment?.groups?.get(0)?.value) throw Error("incorrect expression $initialization")
                    
                    if (assignment.groups[2]?.value.let { it != null && it != "" }) {
                        if (assignment.groups[3]?.value.let { it != null && it != "" }) {
                            instruction = when (it.first.name) {
                                IO.Name.Double -> Math(compiler, assignment.groups[3]!!.value)
                                IO.Name.String -> String(compiler, assignment.groups[3]!!.value)
                                else -> Bool(compiler, assignment.groups[3]!!.value.matches("""\s*true\s*""".toRegex()))
                            }
                        } else throw Error("incorrect expression $initialization")
                    }
                    
                    initializationList.add(SetVar(compiler, assignment.groups[1]!!.value, instruction, true))
                }
            }
        }
        
        return initializationList
    }
    
    init {
        addInput(InputString(IO.Name.Double, this, "Double:",true, isLink = false))
        addInput(InputString(IO.Name.String, this, "String:",true, isLink = false))
        addInput(InputString(IO.Name.Boolean, this, "Boolean:", true, isLink = false))
        
        setHeader("Init", "#8281B1")
    }
}