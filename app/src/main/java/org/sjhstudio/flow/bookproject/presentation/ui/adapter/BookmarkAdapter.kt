package org.sjhstudio.flow.bookproject.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.sjhstudio.flow.bookproject.databinding.ItemBookmarkBinding
import org.sjhstudio.flow.bookproject.domain.model.Bookmark

class BookmarkAdapter(
    private val onClickBook: (bookmark: Bookmark) -> Unit,
    private val onClickBookmark: (bookmark: Bookmark) -> Unit
) : ListAdapter<Bookmark, BookmarkAdapter.BookmarkViewHolder>(diffCallback) {

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Bookmark>() {
            override fun areItemsTheSame(oldItem: Bookmark, newItem: Bookmark): Boolean {
                return oldItem.isbn == newItem.isbn && oldItem.title == newItem.title
            }

            override fun areContentsTheSame(oldItem: Bookmark, newItem: Bookmark): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class BookmarkViewHolder(private val binding: ItemBookmarkBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.ivBook.setOnClickListener {
                click(onClickBook)  // 브라우저 열기
            }

            binding.ivBookmark.setOnClickListener {
                click(onClickBookmark)  // 북마크 삭제 유도
            }
        }

        fun bind(data: Bookmark) {
            with(binding) {
                bookmark = data
            }
        }

        private fun click(task: (Bookmark) -> Unit) {
            adapterPosition.takeIf { pos ->
                pos != RecyclerView.NO_POSITION
            }?.let { position ->
                task.invoke(getItem(position))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkViewHolder {
        return BookmarkViewHolder(
            ItemBookmarkBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: BookmarkViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}