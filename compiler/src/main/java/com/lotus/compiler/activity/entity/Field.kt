package com.lotus.compiler.activity.entity

import com.sun.tools.javac.code.Symbol

open class Field(private val symbol: Symbol.VarSymbol):Comparable<Field> {

    val name = symbol.qualifiedName.toString()

    open val prefix = "REQUIRED_"

    //是否私有修饰符
    val isPrivate = symbol.isPrivate

    //是否是基本类型
    val isPrimitive = symbol.type.isPrimitive

    override fun compareTo(other: Field): Int {
        return name.compareTo(other.name)
    }

    override fun toString(): String {
        return "$name:${symbol.type}"
    }

}