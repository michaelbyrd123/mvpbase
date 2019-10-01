package com.raywenderlich.android.creaturemon.presenter

import androidx.annotation.DrawableRes
import com.raywenderlich.android.creaturemon.model.Creature

interface CreatureDetailContract {
    interface Presenter
    interface View{
        fun setCreature(creature : Creature)
        fun showHitPoints(hitPoints: String)
        fun showAvatarDrawable(@DrawableRes resourceId: Int)
    }
}