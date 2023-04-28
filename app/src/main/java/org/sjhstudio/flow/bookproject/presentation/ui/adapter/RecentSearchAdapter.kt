package org.sjhstudio.flow.bookproject.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.sjhstudio.flow.bookproject.databinding.ItemRecentSearchBinding
import org.sjhstudio.flow.bookproject.domain.model.RecentSearch

class RecentSearchAdapter(
    private val onClickItem: (RecentSearch) -> Unit,
    private val onDelete: (RecentSearch) -> Unit
) : ListAdapter<RecentSearch, RecentSearchAdapter.RecentSearchViewHolder>(diffCallback) {

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<RecentSearch>() {
            override fun areItemsTheSame(oldItem: RecentSearch, newItem: RecentSearch): Boolean {
                return oldItem.word == newItem.word
            }

            override fun areContentsTheSame(oldItem: RecentSearch, newItem: RecentSearch): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class RecentSearchViewHolder(private val binding: ItemRecentSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                click(onClickItem)  // 검색화면으로 돌아가 해당 쿼리 검색
            }

            binding.ivDelete.setOnClickListener {
                click(onDelete) // 검색어 삭제 유도
            }
        }

        fun bind(data: RecentSearch) {
            with(binding) {
                recentSearch = data
            }
        }

        private fun click(task: (RecentSearch) -> Unit) {
            adapterPosition.takeIf { pos ->
                pos != RecyclerView.NO_POSITION
            }?.let { position ->
                task.invoke(getItem(position))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentSearchViewHolder {
        return RecentSearchViewHolder(
            ItemRecentSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecentSearchViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}