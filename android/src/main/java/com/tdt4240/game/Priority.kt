package com.tdt4240.game

/**
* Priority object to keep track of the different priority levels.
* The ECS engine loops over the systems in the order defined here
* Lower number means higher priority
*/
object Priority{
    const val DELETE = 0
    const val PHYSICS = 1
    const val BOUNDS = 3
}