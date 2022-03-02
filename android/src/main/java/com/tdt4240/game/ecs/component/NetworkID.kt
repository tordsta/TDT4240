package com.tdt4240.game.ecs.component


private val charPool : List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')


// TODO: document
open class NetworkIdProvider {
    companion object {
        var instance = NetworkIdProvider()
    }

    var nextId : String? = null

    open fun getNewId() : String{
        if(nextId != null){
            val tmp = nextId
            nextId = null
            return tmp!!
        }

        return (1..16)
                .map { kotlin.random.Random.nextInt(0, charPool.size) }
                .map(charPool::get)
                .joinToString("")
    }
}


// TODO: document
class NetworkID : BaseComponent() {
    val id: String = NetworkIdProvider.instance.getNewId()

    override fun accept(visitor: ComponentVisitor) {
        visitor.visit(this)
    }

}