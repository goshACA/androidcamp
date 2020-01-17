package com.guardian.app.adapter.viewholder

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.guardian.app.R
import com.guardian.app.entity.RecyclerArticleItem

open class BaseViewHolder(
    itemView: View,
    var onItemClickListener: OnItemClickListener,
    var onPosLikedListener: OnPosLikedListener
) : RecyclerView.ViewHolder(itemView) {
    private val itemImg: ImageView = itemView.findViewById(R.id.img_item)
    private val btnLike: ImageView = itemView.findViewById(R.id.btn_like)
    private val itemTitle: TextView = itemView.findViewById(R.id.txt_title)
    private val itemSection: TextView = itemView.findViewById(R.id.txt_section_name)
    protected open val likeColorId: Int = R.color.colorAccent
    @SuppressLint("ClickableViewAccessibility")
    fun bind(article: RecyclerArticleItem, listener: View.OnClickListener?) = with(itemView) {
        Glide.with(itemView.context).load(article.getImg())
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(itemImg)
        itemView.setOnClickListener {
            itemImg.transitionName = article.getImg()
            onItemClickListener.onItemClick(adapterPosition, itemImg)
        }
        itemTitle.text = article.title
        itemSection.text = article.sectionName

        btnLike.isClickable = true
        if (!article.isLiked) btnLike.setColorFilter(
            ContextCompat.getColor(context, likeColorId),
            android.graphics.PorterDuff.Mode.MULTIPLY
        );

        btnLike.setOnClickListener {
            if (article.isLiked) {
                article.isLiked = false
                btnLike.setColorFilter(
                    ContextCompat.getColor(context, likeColorId),
                    android.graphics.PorterDuff.Mode.MULTIPLY
                );
                onPosLikedListener.onPosLikeRemoved(adapterPosition)
            } else {
                article.isLiked = true
                btnLike.colorFilter = null;
                onPosLikedListener.onPosLiked(adapterPosition)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(pos: Int, imgView: ImageView)
    }

    interface OnPosLikedListener {
        fun onPosLiked(pos: Int)
        fun onPosLikeRemoved(pos: Int)
    }


}


class NewsViewHolder(
    itemView: View, onItemClickListener: OnItemClickListener,
    onPosLikedListener: OnPosLikedListener
) : BaseViewHolder(itemView, onItemClickListener, onPosLikedListener) {
    override val likeColorId: Int = R.color.black
}



