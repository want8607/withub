package com.example.withub

interface SwipeAdapterInterface {
    fun getSwipeLayoutResourceId(position : Int) : Int
    fun notifyDatasetChanged()
}