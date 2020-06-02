package com.example.practice.ui.scenes.popularFragment

import com.example.practice.data.models.Image
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndStrategy::class)
interface PopularView : MvpView {
    fun showOnSuccess()
    fun showOnError()
    fun setData(data: List<Image>)
    fun addData(data: List<Image>)
}