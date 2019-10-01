package com.raywenderlich.android.creaturemon.presenter

import com.raywenderlich.android.creaturemon.model.*
import com.raywenderlich.android.creaturemon.model.room.RoomRepository

class CreatureDetailPresenter(private val generator: CreatureGenerator = CreatureGenerator(),
                            private val repository: CreatureRepository = RoomRepository())
    : BasePresenter<CreatureDetailContract.View>(), CreatureDetailContract.Presenter {

    private lateinit var creature: Creature

    var name = ""
    private var intelligence = 0
    private var strength = 0
    private var endurance = 0
    private var drawable = 0


    fun setCreature(temp: Creature) {
        creature = temp
        name = creature.name
        intelligence = creature.attributes.intelligence
        strength = creature.attributes.intelligence
        endurance = creature.attributes.intelligence
        drawable = creature.drawable
        getView()?.showAvatarDrawable(drawable)
        getView()?.showHitPoints(creature.hitPoints.toString())
        getView()?.setCreature(creature)
    }

}