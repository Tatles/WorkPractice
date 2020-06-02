package com.example.practice.ui.scenes.newFragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.practice.ui.adapters.Adapter
import com.example.exampleproj.RetrofitImpl
import com.example.practice.ui.scenes.DetailImageActivity
import com.example.practice.ui.PaginationScrollListener
import com.example.practice.R
import com.example.practice.data.models.Image
import kotlinx.android.synthetic.main.fragment_new.*
import kotlinx.android.synthetic.main.fragment_popular.*
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class NewFragment : MvpAppCompatFragment(), NewView {

    private lateinit var adapter: Adapter
    private val apiService = RetrofitImpl.create()

    private val newPresenter by moxyPresenter { NewPresenter(apiService) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_new, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvSetUp()
        newPresenter.onRefresh()
    }

    private fun rvSetUp() {
        adapter = Adapter { clickOnImage(it) }
        rv_new.layoutManager = GridLayoutManager(context, 2)
        rv_new.adapter = adapter
        rv_new.addOnScrollListener(
            PaginationScrollListener(
                { -> newPresenter.getMoreItems() },
                10
            )
        )
        swipe_lay_new.setOnRefreshListener { newPresenter.onRefresh() }
    }

    private fun clickOnImage(model: Image) {
        val intent = Intent(context, DetailImageActivity::class.java)
        intent.putExtra("model", model)
        startActivity(intent)
    }

    override fun showOnSuccess() {
        swipe_lay_new.isRefreshing = false
        no_internet_new.visibility = View.INVISIBLE
    }

    override fun showOnError() {
        no_internet_new.visibility = View.VISIBLE
        rv_new.visibility = View.INVISIBLE
        swipe_lay_new.isRefreshing = false
    }

    override fun addData(data: List<Image>) {
        adapter.addData(data)
    }

    override fun setData(data: List<Image>) {
        adapter.setData(data)
    }


//    override fun onRefresh() {
//        pageNumber = 1
//        apiService.getImages(popular = true, page = pageNumber)
//            .observeOn(mainThread())
//            .subscribeOn(Schedulers.io())
//            .doFinally {swipe_lay.isRefreshing = false}
//            .subscribe({
//                populateData(it.data)
//                no_internet.visibility = View.INVISIBLE
//            }, {
//                no_internet.visibility = View.VISIBLE
//            })
//    }
//    override fun getMoreItems() {
//        pageNumber++
//        apiService.getImages(new = true, page = pageNumber)
//            .observeOn(mainThread())
//            .subscribeOn(Schedulers.io())
//            .subscribe({
//                adapter.addData(it.data)
//            }, {
//                Log.e(MainActivity::class.java.simpleName, "name :$it")
//                no_internet.visibility = View.VISIBLE
//                rv_popular.visibility = View.INVISIBLE
//            })
//    }


}
