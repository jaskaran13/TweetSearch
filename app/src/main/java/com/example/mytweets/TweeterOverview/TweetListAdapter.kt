package com.example.mytweets.TweeterOverview

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.mytweets.Network.CustomObject
import com.example.mytweets.R
import kotlinx.android.synthetic.main.my_list.view.*


class MyListAdapter(var items:ArrayList<CustomObject>, val context:Context):ListAdapter<CustomObject,ViewHolder>(DiffCallback){

    companion object DiffCallback : DiffUtil.ItemCallback<CustomObject>() {
        override fun areItemsTheSame(oldItem: CustomObject, newItem:CustomObject): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: CustomObject, newItem: CustomObject): Boolean {
            return (oldItem.customname == newItem.customname && oldItem.customscreenname == newItem.customscreenname && oldItem.custonurl == newItem.custonurl)
        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.i("main","List Adapter Called")
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.my_list, parent, false))
    }


    override fun getItemCount(): Int {
        return items.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.myTextItems.text=items.get(position).customtext
        holder.myNameItem.text=items.get(position).customname
        holder.myscreenname.text=items.get(position).customscreenname
        val imgUri = items.get(position).custonurl?.toUri().buildUpon().scheme("https").build()
        Glide.with(holder.myImg.context).load(imgUri).apply(RequestOptions().placeholder(R.drawable.loading_animation)).into((holder.myImg))
    }
}

class ViewHolder(view: View):RecyclerView.ViewHolder(view){
    val myTextItems=view.Text_items
    var myNameItem=view.name_items
    var myImg=view.image_item
    var myscreenname=view.screenname_itens
}



