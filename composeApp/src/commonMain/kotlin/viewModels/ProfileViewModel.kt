package viewModels

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import models.ArticleModel
import repository.ArticleRepository

class ProfileViewModel(private val articleRepository: ArticleRepository): ScreenModel {

    private var _uiState = MutableStateFlow<UIState?>(null)
    val uiState: StateFlow<UIState?> = _uiState
}

data class UIState(
    var userName: String?,
    var userHeadline: String?,
    var userMail: String?,
    var userPhoneNumber: Int?,
    var userImage: String?,
)
