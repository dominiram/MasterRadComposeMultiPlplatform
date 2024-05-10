package repository

import models.ArticleModel

interface ArticleRepository {
    fun getArticles(): List<ArticleModel>
}
