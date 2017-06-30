package com.andrisasuke.app.cardnews.deps

import com.andrisasuke.app.cardnews.home.HomeActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {

    fun inject(homeActivity: HomeActivity)

}