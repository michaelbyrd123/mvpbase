package com.raywenderlich.android.creaturemon.presenter

import androidx.annotation.DrawableRes
import com.raywenderlich.android.creaturemon.model.AttributeType
import com.raywenderlich.android.creaturemon.model.Creature

interface EditCreatureContract {
    interface Presenter{
        fun updateName(name: String)
        fun attributeSelected(attributeType: AttributeType, position: Int)
        fun drawableSelected(drawable: Int)
        fun isDrawableSelected() : Boolean
        fun saveCreature()
    }
    interface View{
        fun setCreature(creature : Creature)
        fun showHitPoints(hitPoints: String)
        fun showAvatarDrawable(@DrawableRes resourceId: Int)
        fun showCreatureSaved()
        fun showCreatureSavedError()
    }
}