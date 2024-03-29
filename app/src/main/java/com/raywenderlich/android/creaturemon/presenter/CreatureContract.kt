package com.raywenderlich.android.creaturemon.presenter

import androidx.annotation.DrawableRes
import com.raywenderlich.android.creaturemon.model.AttributeType

interface CreatureContract {
    interface Presenter{
        fun updateName(name: String)
        fun attributeSelected(attributeType: AttributeType, position: Int)
        fun drawableSelected(drawable: Int)
        fun isDrawableSelected() : Boolean
        fun saveCreature()
    }
    interface View{
        fun showHitPoints(hitPoints: String)
        fun showAvatarDrawable(@DrawableRes resourceId: Int)
        fun showCreatureSaved()
        fun showCreatureSavedError()
    }
}