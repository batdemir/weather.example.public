package com.batdemir.template.di.component

import com.batdemir.template.ui.view.cities.CitiesFragment
import com.batdemir.template.ui.view.cities.add.CitiesAddFragment
import dagger.Subcomponent

@Subcomponent
interface CitiesComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): CitiesComponent
    }

    fun inject(citiesFragment: CitiesFragment)
    fun inject(citiesAddFragment: CitiesAddFragment)
}