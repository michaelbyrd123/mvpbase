package com.raywenderlich.android.creaturemon.presenter

import androidx.lifecycle.LiveData
import com.raywenderlich.android.creaturemon.model.Creature

interface AllCreaturesContract {
    interface Presenter{
        fun getAllCreatures() : LiveData<List<Creature>>
        fun clearAllCreatures()
        fun deleteCreature(creature : Creature)
        fun saveCreature(creature : Creature)
    }
    interface View{
        fun showCreaturesCleared()
        fun showCreaturesSaved()
        fun showCreaturesDeleted()
    }
}