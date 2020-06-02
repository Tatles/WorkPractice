package com.example.practice.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

import androidx.recyclerview.widget.RecyclerView
import com.example.practice.R
import com.example.practice.data.models.Image
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.rv_item.view.*


class Adapter(private var callback: (model: Image) -> Unit) :
    RecyclerView.Adapter<Adapter.MyViewHolder>() {
    private var data = listOf<Image>()

    fun addData(listItems: List<Image>) {
        val sizePast = this.data.size
        this.data = this.data + listItems
        val sizeNew = this.data.size
        notifyItemRangeChanged(sizePast, sizeNew)
    }

    fun setData(data: List<Image>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = data.count()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder =
        MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.rv_item, parent, false), callback
        )


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(data[position])
    }

    class MyViewHolder(itemView: View, private val callback: (model: Image) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.imageView
        fun bind(model: Image) {
            Picasso.get().load("http://gallery.dev.webant.ru/media/" + model.image.name).centerCrop().fit().into(imageView)
            itemView.setOnClickListener {
                callback.invoke(model)
            }
        }
    }
}