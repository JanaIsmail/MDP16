package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter(private val userList: ArrayList<User>, val context: Context) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        val user: User = userList[position]
        holder.viewImage.setImageResource(R.drawable.camera)
        holder.textViewName.text = user.name

        when(user.status){
            1 -> { holder.textViewState.text = "Online" ; holder.textViewState.setTextColor(Color.GREEN) }
            0 -> { holder.textViewState.text = "Offline" ; holder.textViewState.setTextColor(Color.RED) }
        }

        holder.itemView.setOnClickListener{ context.startActivity(Intent(context,CameraActivity::class.java)) }
    }

    override fun getItemCount() : Int{ return userList.size }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder{
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_devices, parent, false)
        return ViewHolder(v)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val viewImage = itemView.findViewById(R.id.ViewImage) as ImageView
        val textViewName = itemView.findViewById(R.id.textViewName) as TextView
        val textViewState = itemView.findViewById(R.id.textViewState) as TextView
    }
}