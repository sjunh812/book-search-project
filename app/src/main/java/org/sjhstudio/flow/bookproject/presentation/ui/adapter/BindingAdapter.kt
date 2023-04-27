package org.sjhstudio.flow.bookproject.presentation.ui.adapter

import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import org.sjhstudio.flow.bookproject.R
import org.sjhstudio.flow.bookproject.presentation.util.newDateFormat
import org.sjhstudio.flow.bookproject.presentation.util.originDateFormat

@BindingAdapter("imageUrl")
fun ImageView.bindImageUrl(url: String?) {
    if (!url.isNullOrEmpty()) {
        Glide.with(context)
            .load(url)
            .into(this)
    }
}

@BindingAdapter("publishDate")
fun TextView.bindPublishDate(date: String?) {
    if (!date.isNullOrEmpty()) {
        text = newDateFormat.format(originDateFormat.parse(date) ?: return)
    }
}

@BindingAdapter("arrowImage")
fun ImageView.bindArrowImage(isExpand: Boolean) {
    setImageResource(if (isExpand) R.drawable.ic_arrow_up_24 else R.drawable.ic_arrow_down_24)
}

@BindingAdapter("descriptionVisibility")
fun TextView.bindDescriptionVisibility(isExpand: Boolean) {
    isVisible = isExpand
}