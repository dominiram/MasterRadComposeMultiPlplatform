package repository

import kotlinx.coroutines.flow.Flow
import models.ArticleModel

interface ArticleRepository {
    fun getStaticArticles(): List<ArticleModel>
    fun getArticles(searchText: String?): Flow<List<ArticleModel>>
}
