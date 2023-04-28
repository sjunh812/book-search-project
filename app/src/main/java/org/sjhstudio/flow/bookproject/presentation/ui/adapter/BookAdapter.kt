package org.sjhstudio.flow.bookproject.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.sjhstudio.flow.bookproject.R
import org.sjhstudio.flow.bookproject.databinding.ItemBookBinding
import org.sjhstudio.flow.bookproject.domain.model.Book
import org.sjhstudio.flow.bookproject.presentation.util.click

class BookAdapter(
    private val onClickBook: (book: Book) -> Unit,
    private val insertBookMark: (book: Book) -> Unit,
    private val deleteBookmark: (book: Book) -> Unit
) : ListAdapter<Book, BookAdapter.BookViewHolder>(diffCallback) {

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Book>() {
            override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
                return oldItem.isbn == newItem.isbn && oldItem.title == newItem.title
            }

            override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
                return oldItem == newItem
            }
        }
    }

    @OptIn(FlowPreview::class)
    inner class BookViewHolder(
        private val binding: ItemBookBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                adapterPosition.takeIf { pos ->
                    pos != RecyclerView.NO_POSITION
                }?.let { position ->
                    val book = getItem(position)
                    val isExpand = book.isExpand

                    book.isExpand = !isExpand
                    notifyItemChanged(position)
                }
            }

            binding.ivBook.setOnClickListener {
                adapterPosition.takeIf { pos ->
                    pos != RecyclerView.NO_POSITION
                }?.let { position ->
                    onClickBook.invoke(getItem(position))
                }
            }

            CoroutineScope(Dispatchers.Main).launch {
                binding.ivBookmark.click()
                    .debounce(250)
                    .onEach {
                        adapterPosition.takeIf { pos ->
                            pos != RecyclerView.NO_POSITION
                        }?.let { position ->
                            val book = getItem(position)

                            if (book.isBookmark) {
                                binding.ivBookmark.setImageResource(R.drawable.ic_bookmark_border_24)
                                deleteBookmark.invoke(book)
                            } else {
                                binding.ivBookmark.setImageResource(R.drawable.ic_bookmark_24)
                                insertBookMark.invoke(book)
                            }

                            book.isBookmark = !book.isBookmark
                        }
                    }.collect()
            }
        }

        fun bind(data: Book) {
            with(binding) {
                book = data
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        return BookViewHolder(
            ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}