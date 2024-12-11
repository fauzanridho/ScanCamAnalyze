package com.capstone.scancamanalyze.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.scancamanalyze.R
import com.capstone.scancamanalyze.data.api.AvatarItem

class AvatarAdapter(
    private val avatars: List<AvatarItem>,
    private val onClick: (String) -> Unit
) : RecyclerView.Adapter<AvatarAdapter.AvatarViewHolder>() {

    inner class AvatarViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivAvatar: ImageView = view.findViewById(R.id.img_avatar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AvatarViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_avatar, parent, false)
        return AvatarViewHolder(view)
    }

    override fun onBindViewHolder(holder: AvatarViewHolder, position: Int) {
        val avatar = avatars[position]
        Glide.with(holder.itemView.context)
            .load(avatar.image)
            .into(holder.ivAvatar)

        holder.itemView.setOnClickListener {
            onClick(avatar.image.toString())
        }
    }

    override fun getItemCount(): Int = avatars.size
}
