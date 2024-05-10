package models

data class ArticleModel(
    val id: Int,
    val views: Int,
    val title: String,
    val subtitle: String,
    val body: String,
    val author: String,
    val authorImageUrl: String,
    val category: String,
    val imageUrl: String,
    val createdAt: String,
)
