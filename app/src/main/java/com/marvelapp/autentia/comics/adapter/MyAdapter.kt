package com.marvelapp.autentia.comics.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.marvelapp.autentia.comics.Comic
import com.marvelapp.autentia.comics.MarvelDataResponse
import com.marvelapp.autentia.comics.R
import com.marvelapp.autentia.comics.commons.inflate
import com.marvelapp.autentia.comics.commons.loadImg
import kotlinx.android.synthetic.main.item_comic_list.view.*


/**
 * Adaptador para Comics
 * Created by erubio on 4/11/17.
 */

class MyAdapter(val comics: ArrayList<Comic>) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    var _comics: ArrayList<Comic> = ArrayList()
    private val loadingItem = object : ViewType {
        override fun getViewType() = AdapterConstants.LOADING
    }

    class MyAdapter(val comics: List<Comic>) {
        var _comics = comics
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = _comics[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = _comics.size

    class ViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
            parent.inflate(R.layout.item_comic_list)) {

        lateinit var comic: Comic
        private val thumbnail = itemView.ivThumbnail
        private val description = itemView.tvDescription
        private val title = itemView.tvTitle
        private val price = itemView.tvPrice

        fun bind(item: Comic) {
            this.comic = item
            title.text = item.title
            description.text = item.description
            price.text = item.prices[0].price.toString()

            var img = item.thumbnail.path + '.' + item.thumbnail.extension
            thumbnail.loadImg(img)
        }

    }

    fun swap(response: MarvelDataResponse) {
        if (response.results == null)
            return

        if (_comics.isNotEmpty())
            _comics.clear()
        _comics.addAll(response.results)
        notifyDataSetChanged()
    }


}