package com.tao.bus.home.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.base.entity.home.ArticleEntity
import com.example.base.ui.BindingViewHolder
import com.example.bus.home.databinding.LayoutArticleItemBinding

class ArticleAdapter :
    ListAdapter<ArticleEntity, BindingViewHolder<LayoutArticleItemBinding>>(Diff()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BindingViewHolder<LayoutArticleItemBinding> {
        val binding = LayoutArticleItemBinding.inflate(LayoutInflater.from(parent.context))
        return BindingViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: BindingViewHolder<LayoutArticleItemBinding>,
        position: Int
    ) {
        val item = getItem(position)
        holder.binding.run {
            tvContent.text = item.title
            tvAuthor.text = item.shareUser
            tvTime.text = item.niceDate
        }
    }

    private class Diff : DiffUtil.ItemCallback<ArticleEntity>() {
        override fun areItemsTheSame(oldItem: ArticleEntity, newItem: ArticleEntity): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: ArticleEntity, newItem: ArticleEntity): Boolean =
            oldItem == newItem

    }
}