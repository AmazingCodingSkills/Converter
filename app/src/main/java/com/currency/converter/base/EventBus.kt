package com.currency.converter.base

import com.currency.converter.features.rate.countryname.CountryModel
import java.util.*

object EventBus {

    val subject = Subject<CountryModel>()

    open class Subject<T>(initial: T? = null) : Observable<T?>, Observer<T?> {

        override val observers: MutableList<Observer<T?>> = LinkedList()
        private var value: T? = initial
        override fun update(value: T?) {
            this.value = value
            observers.forEach { it.update(value) }
        }

        override fun removeObserver(observer: Observer<T?>) {
            observers.remove(observer)
        }

        override fun addObserver(observer: Observer<T?>) {
            observers.add(observer)
        }
    }
}