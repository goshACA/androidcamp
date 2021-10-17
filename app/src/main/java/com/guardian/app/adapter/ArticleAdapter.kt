package com.guardian.app.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.guardian.app.Constants
import com.guardian.app.R
import com.guardian.app.adapter.viewholder.BaseViewHolder
import com.guardian.app.adapter.viewholder.NewsViewHolder
import com.guardian.app.entity.RecyclerArticleItem


class ArticleAdapter(
    var context: Context,
    private var data: ArrayList<RecyclerArticleItem>,
    private var onArticleClickListener: OnArticleClickListener,
    private val onRemoveItem: OnRemoveItemListener,
    private val onLikeItemListener: OnLikeItemListener
) :
    PagedListAdapter<RecyclerArticleItem, BaseViewHolder> (RecyclerArticleItem.DIFF_CALLBACK){
    private val onItemClickListener = object : BaseViewHolder.OnItemClickListener {
        override fun onItemClick(pos: Int, imgView: ImageView) {
            onArticleClickListener.onClick(data[pos].id, imgView)
        }
    }

    private val onPosLikeItemListener = object : BaseViewHolder.OnPosLikedListener {
        override fun onPosLiked(pos: Int) = onLikeItemListener.onLikedItem(data[pos])
        override fun onPosLikeRemoved(pos: Int) = onLikeItemListener.onRemoveLikedItem(data[pos])

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val inflater = LayoutInflater.from(context)
        if (viewType == Constants.NEWS_TYPE)
            return NewsViewHolder(
                inflater.inflate(R.layout.item_news_article_layout, parent, false),
                onItemClickListener, onPosLikeItemListener
            )
        else return BaseViewHolder(
            inflater.inflate(R.layout.item_article_layout, parent, false),
            onItemClickListener,
            onPosLikeItemListener
        )
    }

    fun getPositionByItemId(id: String) = data.indexOfFirst {
        it.id == id
    }

    fun removeItem(position: Int) {
        onRemoveItem.onDeleteItem(data[position])
        data.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, data.size)
    }

    fun restoreItem(article: RecyclerArticleItem, position: Int) {
        data.add(position, article)
        onRemoveItem.onRestoreItem(data[position])
        notifyItemInserted(position)
    }

    override fun getItem(position: Int) = data[position]
    fun getAdapterItem(position: Int) = getItem(position)

    fun updateData(newData: ArrayList<RecyclerArticleItem>) {
        data.addAll(newData.filter { !data.contains(it) })
        notifyDataSetChanged()
    }

    override fun getItemCount() = data.size
    override fun getItemId(position: Int) = position.toLong()


    override fun getItemViewType(position: Int): Int {
        return if (data[position].sectionName == Constants.NEWS_SECTION) Constants.NEWS_TYPE
        else Constants.BASE_TYPE
    }


    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(data[position], null)
    }


    interface OnArticleClickListener {
        fun onClick(id: String, imgView: ImageView)
    }

    interface OnRemoveItemListener {
        fun onDeleteItem(item: RecyclerArticleItem)
        fun onRestoreItem(item: RecyclerArticleItem)
    }

    interface OnLikeItemListener {
        fun onLikedItem(item: RecyclerArticleItem)
        fun onRemoveLikedItem(item: RecyclerArticleItem)
    }


}
