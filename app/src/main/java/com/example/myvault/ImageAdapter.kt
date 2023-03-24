package com.example.myvault

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ImageAdapter(private val Filenames: ArrayList<Files>, private val context : Context): RecyclerView.Adapter<ImageAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemview =LayoutInflater.from(parent.context).inflate(R.layout.rimageview,parent, false)
        return MyViewHolder(itemview)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentitem = Filenames[position]
        holder.filename.text = currentitem.filename
        Glide.with(context).load(Uri.parse(Filenames[position].url)).into(holder.image1)

    }
    override fun getItemCount(): Int {
        return Filenames.size
    }





    class MyViewHolder(itemview:View): RecyclerView.ViewHolder(itemview){
        val filename:TextView = itemview.findViewById(R.id.text_view_show_uploads)
        val image1: ImageView = itemview.findViewById(R.id.image_view_upload)
    }

}
