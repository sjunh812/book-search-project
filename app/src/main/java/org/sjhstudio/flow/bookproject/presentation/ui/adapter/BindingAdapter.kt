package org.sjhstudio.flow.bookproject.presentation.ui.adapter

import android.text.Html
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import org.junit.runner.Description
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

@BindingAdapter("descriptionVisibility")
fun TextView.bindDescriptionVisibility(isExpand: Boolean) {
    isVisible = isExpand
}

@BindingAdapter("description")
fun TextView.bindDescription(description: String?) {
    if (!description.isNullOrEmpty()) {
        text = Html.fromHtml(description, Html.FROM_HTML_MODE_LEGACY)
    }
}