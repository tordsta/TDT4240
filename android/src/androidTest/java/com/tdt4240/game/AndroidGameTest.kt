package com.tdt4240.game


import com.badlogic.gdx.Application
import com.badlogic.gdx.ApplicationListener
import com.badlogic.gdx.backends.headless.HeadlessApplication
import org.junit.AfterClass
import org.junit.BeforeClass

open class AndroidGameTest {
    companion object {

        private var application: Application? = null

        @BeforeClass
        @JvmStatic
        fun init() {

            application = HeadlessApplication(object : ApplicationListener {
                override fun create() {

                }

                override fun resize(width: Int, height: Int) {

                }

                override fun render() {

                }

                override fun pause() {

                }

                override fun resume() {

                }

                override fun dispose() {

                }
            })

            // System.out.println("The path is " + Gdx.files.localStoragePath)

           //Gdx.gl20 = Mockito.mock(GL20::class.java)
           // Gdx.gl = Gdx.gl20
        }

        @AfterClass
        @JvmStatic
        fun cleanUp() {
            application!!.exit()
            application = null
        }

    }
}