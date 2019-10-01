package com.raywenderlich.android.creaturemon.presenter

import com.raywenderlich.android.creaturemon.model.*
import com.raywenderlich.android.creaturemon.model.room.RoomRepository

class EditCreaturePresenter(private val generator: CreatureGenerator = CreatureGenerator(),
                        private val repository: CreatureRepository = RoomRepository())
    : BasePresenter<EditCreatureContract.View>(), EditCreatureContract.Presenter {

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
        getView()?.setCreature(creature)
        updateCreature()
    }

    private fun updateCreature() {
        val attributes = CreatureAttributes(intelligence, strength, endurance)
        creature = generator.generateCreature(attributes, name, drawable)
        getView()?.showHitPoints(creature.hitPoints.toString())
    }

    override fun updateName(name: String) {
        this.name = name
        updateCreature()
    }

    override fun attributeSelected(attributeType: AttributeType, position: Int) {
        when(attributeType){
            AttributeType.INTELLIGENCE ->
                intelligence = AttributeStore.INTELLIGENCE[position].value
            AttributeType.STRENGTH ->
                strength = AttributeStore.STRENGTH[position].value
            AttributeType.ENDURANCE ->
                endurance = AttributeStore.ENDURANCE[position].value
        }
        updateCreature()
    }

    override fun drawableSelected(drawable: Int) {
        this.drawable = drawable
        getView()?.showAvatarDrawable(drawable)
        updateCreature()
    }

    override fun isDrawableSelected(): Boolean {
        return drawable != 0
    }

    fun getUpdatedCreature(): Creature{
        return creature
    }

    private fun canSaveCreature(): Boolean{
        return intelligence != 0 && strength != 0 && endurance != 0 && name.isNotEmpty() && drawable != 0
    }

    fun deleteOriginal(deletable : Creature){
        repository.deleteCreature(deletable)
    }

    override fun saveCreature() {
        if(canSaveCreature()){
            repository.saveCreature(creature)
            getView()?.showCreatureSaved()
        }else{
            getView()?.showCreatureSavedError()
        }
    }
}