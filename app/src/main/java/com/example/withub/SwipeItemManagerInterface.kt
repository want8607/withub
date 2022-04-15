package com.example.withub

import com.daimajia.swipe.SwipeLayout

interface SwipeItemManagerInterface {
    fun openItem(position : Int)
    fun closeItem(position: Int)
    fun closeAllExcept(layout: SwipeLayout)
    fun closeAllItems()
    fun getOpenItems() : List<Int>
    fun getOpenLayouts() : List<SwipeLayout>
    fun removeShownLayouts(layout: SwipeLayout)
    fun isOpen(position: Int) : Boolean
    fun getMode() : com.daimajia.swipe.util.Attributes.Mode
    fun setMode(mode: com.daimajia.swipe.util.Attributes.Mode)
}