package com.example.interpreter.vm.instruction

import com.example.interpreter.vm.Compiler
import com.example.interpreter.vm.Env
import com.example.interpreter.vm.awaitLR
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
class SetObject : Instruction {
    @Transient
    var obj: Any? = null
    val name: kotlin.String
    val value: Instruction
    
    override fun exec(env: Env) = sequence<Instruction> {
        _toObject(obj!!, env)[name] = _unRegister(value, env)
        yield(Nop(Compiler.FCompiler()))
    }.iterator()
    
    private fun _unRegister(value: Instruction, env: Env): Instruction{
        if(value is Register) return _unRegister(awaitLR(value.exec(env)), env)
        
        return value
    }
    
    private fun _toObject(value: Any, env: Env): Object{
        if(value is Object) return value
        if(value is kotlin.String) return _toObject(env[value], env)
        if(value is String) return _toObject(env[value.toString()], env)
        if(value is Register) return _toObject(awaitLR(value.exec(env)), env)
        
        throw Error("Runtime Error 'to object' instruction not entry")
    }
    
    constructor(compiler: Compiler, obj: Any, name: kotlin.String, value: Instruction) : super(compiler) {
        this.obj = obj
        this.name = name
        this.value = value
    }
}