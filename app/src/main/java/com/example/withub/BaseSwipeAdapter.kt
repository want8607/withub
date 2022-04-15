package com.example.withub

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.daimajia.swipe.SwipeLayout
import com.daimajia.swipe.util.Attributes

class BaseSwipeAdapter : BaseAdapter(), SwipeAdapterInterface,SwipeItemManagerInterface{

    var mItemManager = SwipeItemManagerImpl(this)

    override fun getCount(): Int {
        TODO("Not yet implemented")
    }

    override fun getItem(p0: Int): Any {
        TODO("Not yet implemented")
    }

    override fun getItemId(p0: Int): Long {
        TODO("Not yet implemented")
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        TODO("Not yet implemented")
    }

    override fun getSwipeLayoutResourceId(position: Int): Int {
        TODO("Not yet implemented")
    }

    override fun notifyDatasetChanged() {
        TODO("Not yet implemented")
    }

    override fun openItem(position: Int) {
        TODO("Not yet implemented")
    }

    override fun closeItem(position: Int) {
        mItemManager.closeItem(position)
    }

    override fun closeAllExcept(layout: SwipeLayout) {
        mItemManager.closeAllExcept(layout)
    }

    override fun closeAllItems() {
        mItemManager.closeAllItems()
    }

    override fun getOpenItems(): List<Int> {
        TODO("Not yet implemented")
    }

    override fun getOpenLayouts(): List<SwipeLayout> {
        TODO("Not yet implemented")
    }

    override fun removeShownLayouts(layout: SwipeLayout) {
        TODO("Not yet implemented")
    }

    override fun isOpen(position: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun getMode(): Attributes.Mode {
        TODO("Not yet implemented")
    }

    override fun setMode(mode: Attributes.Mode) {
        TODO("Not yet implemented")
    }
}