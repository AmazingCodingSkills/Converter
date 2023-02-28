package com.currency.converter.base

interface Observer<Type> {
    fun update(value: Type)
}

interface Observable<Type> {
    val observers: List<Observer<Type>>
    fun addObserver(observer: Observer<Type>)
    fun removeObserver(observer: Observer<Type>)
}