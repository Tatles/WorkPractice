package com.example.practice.ui.scenes.newFragment


import com.example.exampleproj.RetrofitImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class NewPresenter(private var apiService: RetrofitImpl) : MvpPresenter<NewView>() {
    private var isRequest = false
    private var pageNumber = 1
    fun onRefresh() {
        pageNumber = 1
        apiService.getImages(new = true, page = pageNumber)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                viewState.setData(it.data)
                viewState.showOnSuccess()
            }, {
                viewState.showOnError()
            })
    }

    fun getMoreItems() {
        if (!isRequest) {
            isRequest = true
            pageNumber++
            apiService.getImages(new = true, page = pageNumber)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    viewState.addData(it.data)
                }, {
                    viewState.showOnError()
                })
            isRequest = false
        }
    }
}