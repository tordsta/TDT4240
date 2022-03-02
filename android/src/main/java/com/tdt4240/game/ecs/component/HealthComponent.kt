package com.tdt4240.game.ecs.component

import android.util.Log

/**
 * Component representing the health status of an entity. 
 */
class HealthComponent : BaseComponent() {

    var healthPoints: Int = 0

    fun increaseHealth(points : Int) {
        Log.i("HealthComponent", "Increasing health points by $points")
        healthPoints += points
    }

    fun decrease(points: Int) : Int {
        Log.i("HealthComponent", "Decreasing health points by $points")
        healthPoints -= points
        return healthPoints
    }

    fun isDead() : Boolean{
        return healthPoints <= 0
    }

    override fun accept(visitor: ComponentVisitor) {
        visitor.visit(this)
    }

    fun getHealthLevel() : Int {
        when {
            healthPoints >= 90 -> return 3
            healthPoints in 30..89 -> return 2
            healthPoints in 1..29 -> return 1
            else -> return 0
        }
    }

}
