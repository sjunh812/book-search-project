package org.sjhstudio.flow.bookproject.presentation.ui.adapter

import android.annotation.SuppressLint
import android.text.Html
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import org.sjhstudio.flow.bookproject.R
import org.sjhstudio.flow.bookproject.presentation.util.discountFormat
import org.sjhstudio.flow.bookproject.presentation.util.newDateFormat
import org.sjhstudio.flow.bookproject.presentation.util.originDateFormat
import org.sjhstudio.flow.bookproject.presentation.util.recentSearchDateFormat
import java.util.*

@BindingAdapter("imageUrl")
fun ImageView.bindImageUrl(url: String?) {
    if (!url.isNullOrEmpty()) {
        Glide.with(context)
            .load(url)
            .into(this)
    }
}

@BindingAdapter("author")
fun TextView.bindAuthor(author: String?) {
    if (!author.isNullOrEmpty()) {
        text = author.replace("^", ",")
    }
}

@SuppressLint("SetTextI18n")
@BindingAdapter("discount")
fun TextView.bindDiscount(discount: String?) {
    val discountInt = discount?.toIntOrNull() ?: return
    text = "${discountFormat.format(discountInt)} 원"
}

@BindingAdapter("publishDate")
fun TextView.bindPublishDate(date: String?) {
    if (!date.isNullOrEmpty()) {
        text = newDateFormat.format(originDateFormat.parse(date) ?: return)
    }
}

@BindingAdapter("detailViewVisibility")
fun ConstraintLayout.bindDetailViewVisibility(isExpand: Boolean) {
    isVisible = isExpand
}

@BindingAdapter("description")
fun TextView.bindDescription(description: String?) {
    if (!description.isNullOrEmpty()) {
        text = Html.fromHtml(description, Html.FROM_HTML_MODE_LEGACY)
    }
}

@BindingAdapter("bookmarkImage")
fun ImageView.bindBookmarkImage(isBookmark: Boolean) {
    setImageResource(
        if (isBookmark) R.drawable.ic_bookmark_24 else R.drawable.ic_bookmark_border_24
    )
}

@BindingAdapter("recentSearchDate")
fun TextView.bindRecentSearchDate(time: Long) {
    text = recentSearchDateFormat.format(Date(time))
}