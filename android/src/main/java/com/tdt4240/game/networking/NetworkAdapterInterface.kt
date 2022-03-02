package com.tdt4240.game.networking

import com.badlogic.ashley.core.Entity

interface NetworkAdapterInterface {

    fun beginUpdate()
    fun endUpdate()


    fun updateLocalEntity(entity: Entity)
    fun updateRemoteEntity(entity: Entity)

    fun allNetworkIDs(): MutableIterator<String>?
}