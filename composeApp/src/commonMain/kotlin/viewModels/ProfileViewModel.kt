package viewModels

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import database.MongoDB
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import models.ArticleModel
import models.ProfileModel
import repository.ArticleRepository

class ProfileViewModel(private val mongoDB: MongoDB) : ScreenModel {

    private var _uiState = MutableStateFlow<ProfileModel?>(null)
    val uiState: StateFlow<ProfileModel?> = _uiState

    init {
        screenModelScope.launch {
            mongoDB.getUserData().collectLatest {
                _uiState.value = it
            }
        }
    }

    fun saveUserData(
        name: String,
        jobTitle: String,
        mail: String,
        phoneNumber: Int
    ) = screenModelScope.launch {
        mongoDB.saveUserData(ProfileModel().apply {
            this.name = name
            this.jobTitle = jobTitle
            this.mail = mail
            this.phoneNumber = phoneNumber
        })
    }
}

data class UIState(
    var userName: String?,
    var userHeadline: String?,
    var userMail: String?,
    var userPhoneNumber: Int?,
    var userImage: String?,
)
