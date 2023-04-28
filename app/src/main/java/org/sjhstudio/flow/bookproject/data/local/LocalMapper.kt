package org.sjhstudio.flow.bookproject.data.local

import org.sjhstudio.flow.bookproject.data.local.model.BookmarkEntity
import org.sjhstudio.flow.bookproject.data.local.model.RecentSearchEntity
import org.sjhstudio.flow.bookproject.domain.model.Bookmark
import org.sjhstudio.flow.bookproject.domain.model.RecentSearch

fun mapperToBookmarkList(entities: List<BookmarkEntity>): List<Bookmark> {
    return entities.map { entity ->
        entity.toBookmark()
    }
}

fun mapperToBookmarkEntity(bookmark: Bookmark): BookmarkEntity {
    return BookmarkEntity(
        bookmark.isbn,
        bookmark.title,
        bookmark.link,
        bookmark.image,
        bookmark.author,
        bookmark.discount,
        bookmark.publisher,
        bookmark.publishDate,
        bookmark.description,
        bookmark.date
    )
}

fun mapperToRecentSearchList(entities: List<RecentSearchEntity>): List<RecentSearch> {
    return entities.map { entity ->
        entity.toRecentSearch()
    }
}

fun mapperToRecentSearchEntity(recentSearch: RecentSearch): RecentSearchEntity {
    return RecentSearchEntity(recentSearch.word, recentSearch.date)
}