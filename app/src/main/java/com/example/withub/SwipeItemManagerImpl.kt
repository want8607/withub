package com.example.withub

import android.view.View
import com.daimajia.swipe.SwipeLayout
import com.daimajia.swipe.util.Attributes
import java.lang.IllegalArgumentException
import java.util.*
import kotlin.collections.ArrayList
import kotlin.properties.Delegates

class SwipeItemManagerImpl(swipeAdapterInterface: SwipeAdapterInterface) : SwipeItemManagerInterface {
    var mMode = Attributes.Mode.Single
    var INVALID_POSITION : Int = -1
    var mOpenPosition = INVALID_POSITION
    var mOpenPositions = hashSetOf<Int>()
    var mShownLayouts = hashSetOf<SwipeLayout>()
    var swipeAdapterInterface = swipeAdapterInterface


    fun bind(view : View, position: Int){
        var resId = swipeAdapterInterface.getSwipeLayoutResourceId(position)
        var swipeLayout: SwipeLayout? = view.findViewById<SwipeLayout>(resId)
            ?: throw IllegalStateException("can not find SwipeLayout in target view")
        if (swipeLayout!!.getTag(resId) == null){
            var onLayoutListner = OnLayoutListener(position)
            var swipeMemory = SwipeMemory(position)
            swipeLayout.addSwipeListener(swipeMemory)
            swipeLayout.addOnLayoutListener(onLayoutListner)
            swipeLayout.setTag(resId,ValueBox(position,swipeMemory,onLayoutListner))
            mShownLayouts.add(swipeLayout)

        }else{
            var valueBox : ValueBox = swipeLayout.getTag(resId) as ValueBox
            valueBox.swipeMemory.setPosition(position)
            valueBox.onLayoutListener.setPosition(position)
            valueBox.position = position
        }
    }

    override fun openItem(position: Int) {
        if (mMode == Attributes.Mode.Multiple){
            if (!mOpenPositions.contains(position)){
                mOpenPositions.add(position)
            }else{
                mOpenPosition = position
            }
            swipeAdapterInterface.notifyDatasetChanged()
        }
    }

    override fun closeItem(position: Int) {
        if (mMode == Attributes.Mode.Multiple){
            mOpenPositions.remove(position)
        }else{
            if(mOpenPosition == position){
                mOpenPosition = INVALID_POSITION
            }
        }
        swipeAdapterInterface.notifyDatasetChanged()
    }

    override fun closeAllExcept(layout: SwipeLayout) {
        for (s: SwipeLayout in mShownLayouts){
            if(s != layout){
                s.close()
            }
        }
    }

    override fun closeAllItems() {
        if (mMode == Attributes.Mode.Multiple){
            mOpenPositions.clear()
        }else{
            mOpenPosition = INVALID_POSITION
        }
        for (s :SwipeLayout in mShownLayouts){
            s.close()
        }
    }

    override fun getOpenItems(): List<Int> {
        if(mMode == Attributes.Mode.Multiple){
            return ArrayList<Int>(mOpenPosition)
        }else{
            return Collections.singletonList(mOpenPosition)
        }
    }

    override fun getOpenLayouts(): List<SwipeLayout> {
        return ArrayList<SwipeLayout>(mShownLayouts)
    }

    override fun removeShownLayouts(layout: SwipeLayout) {
        mShownLayouts.remove(layout)
    }

    override fun isOpen(position: Int): Boolean {
        if (mMode == Attributes.Mode.Multiple){
            return mOpenPositions.contains(position)
        }else{
            return mOpenPosition == position
        }
    }

    override fun getMode(): Attributes.Mode {
        return mMode
    }

    override fun setMode(mode: Attributes.Mode) {
        this.mMode = mode
        mOpenPositions.clear()
        mShownLayouts.clear()
        mOpenPosition = INVALID_POSITION
    }

    data class ValueBox(var position: Int, val swipeMemory: SwipeMemory, val onLayoutListener: OnLayoutListener)

    inner class OnLayoutListener(position: Int) : SwipeLayout.OnLayout{

        var mPosition = position

        fun setPosition(position: Int){
            mPosition = position
        }

        override fun onLayout(v: SwipeLayout?) {
            if (isOpen(mPosition)){
                v?.open(false,false)
            }else{
                v?.close(false,false)
            }
        }

    }

    inner class SwipeMemory(position: Int) : SimpleSwipeListener() {
        var mPosition = position

        override fun onClose(layout: SwipeLayout?) {
            if (mMode == Attributes.Mode.Multiple){
                mOpenPositions.remove(mPosition)
            }else{
                mOpenPosition = INVALID_POSITION
            }
        }

        override fun onStartOpen(layout: SwipeLayout?) {
            if (mMode == Attributes.Mode.Single){
                closeAllExcept(layout!!)
            }
        }

        override fun onOpen(layout: SwipeLayout?) {
            if (mMode == Attributes.Mode.Multiple){
                mOpenPositions.add(mPosition)
            }else{
                closeAllExcept(layout!!)
                mOpenPosition = mPosition
            }
        }

        fun setPosition(position: Int){
            this.mPosition = position
        }

    }
}