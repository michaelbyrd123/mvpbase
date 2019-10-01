package com.raywenderlich.android.creaturemon.presenter

import androidx.lifecycle.LiveData
import com.raywenderlich.android.creaturemon.model.*
import com.raywenderlich.android.creaturemon.model.room.RoomRepository

class AllCreaturesPresenter(private val repository: CreatureRepository = RoomRepository())
    : BasePresenter<AllCreaturesContract.View>(), AllCreaturesContract.Presenter {

    override fun getAllCreatures(): LiveData<List<Creature>> {
        return repository.getAllCreatures()
    }

    override fun deleteCreature(creature: Creature) {
        repository.deleteCreature(creature)
        getView()?.showCreaturesDeleted()
    }

    override fun saveCreature(creature: Creature){
        repository.saveCreature(creature)
        getView()?.showCreaturesSaved()

    }

    override fun clearAllCreatures() {
        repository.clearAllCreatures()
        getView()?.showCreaturesCleared()
    }
}