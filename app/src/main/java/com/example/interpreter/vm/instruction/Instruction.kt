package com.example.interpreter.vm.instruction

import com.example.interpreter.vm.Compiler
import com.example.interpreter.vm.Env
import com.example.interpreter.vm.Executor
import java.lang.Error
import kotlin.String
import kotlinx.serialization.Serializable

@Suppress("LeakingThis")
@Serializable
sealed class Instruction {
//    @SerialName("class_type")
    companion object{
        @Suppress("ObjectPropertyName")
        var _id = 0
    }
    
    open val types: String = javaClass.simpleName
    open val isBasic: Boolean = false
    open val id = _id++
    open var blocks: List<Executor>? = null
    
    @Suppress("PropertyName")
    val TAG: String = "VM_INSTRUCTION[$types]"
    
    abstract fun exec(env: Env): Iterator<Instruction>
    
    open fun toNumber(): Double = throw Error("Is not a number")
    override fun toString(): String = throw Error("Is not a string")
    
    @Suppress("UNUSED_PARAMETER")
    constructor(compiler: Compiler)
    @Suppress("UNUSED_PARAMETER")
    constructor(compiler: Compiler, blocks: List<Executor>){
        this.blocks = blocks
    }
}