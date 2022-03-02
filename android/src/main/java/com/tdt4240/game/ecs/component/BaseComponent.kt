package com.tdt4240.game.ecs.component

import com.badlogic.ashley.core.Component

abstract class BaseComponent : Component {
    abstract fun accept(visitor: ComponentVisitor)
}