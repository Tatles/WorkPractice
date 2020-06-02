package com.example.practice.ui.scenes.popularFragment

import com.example.practice.ui.PaginationScrollListener
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.practice.ui.adapters.Adapter
import com.example.exampleproj.RetrofitImpl
import com.example.practice.ui.scenes.DetailImageActivity
import com.example.practice.R
import com.example.practice.data.models.Image
import com.example.practice.ui.scenes.newFragment.NewPresenter
import kotlinx.android.synthetic.main.fragment_popular.*
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class PopularFragment : MvpAppCompatFragment(), PopularView {

    private lateinit var adapter: Adapter
    private val apiService = RetrofitImpl.create()

    private val popularPresenter by moxyPresenter { PopularPresenter(apiService) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_popular, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvSetUp()
        popularPresenter.onRefresh()
    }

    private fun rvSetUp() {
        adapter = Adapter { clickOnImage(it) }
        rv_popular.layoutManager = GridLayoutManager(context, 2)
        rv_popular.adapter = adapter
        rv_popular.addOnScrollListener(
            PaginationScrollListener(
                { popularPresenter.getMoreItems() },
                10
            )
        )
        swipe_lay_popular.setOnRefreshListener { popularPresenter.onRefresh() }
    }

    private fun clickOnImage(model: Image) {
        val intent = Intent(context, DetailImageActivity::class.java)
        intent.putExtra("model", model)
        startActivity(intent)
    }

    override fun showOnSuccess() {
        swipe_lay_popular.isRefreshing = false
        no_internet_popular.visibility = View.INVISIBLE
    }

    override fun showOnError() {
        no_internet_popular.visibility = View.VISIBLE
        rv_popular.visibility = View.INVISIBLE
        swipe_lay_popular.isRefreshing = false
    }

    override fun addData(data: List<Image>) {
        adapter.addData(data)
    }

    override fun setData(data: List<Image>) {
        adapter.setData(data)
    }

}