package viewModels

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import database.MongoDB
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import models.ProfileModel

class ProfileViewModel(private val mongoDB: MongoDB) : ScreenModel {

    private var _uiState = MutableStateFlow<UIState?>(null)
    val uiState: StateFlow<UIState?> = _uiState

    init {
        screenModelScope.launch {
            mongoDB.getUserData().collectLatest {
                _uiState.value = UIState(
                    userImage = it.imageUrl,
                    name = it.name,
                    jobTitle = it.jobTitle,
                    mail = it.mail,
                    phoneNumber = it.phoneNumber
                )
            }
        }
    }

    fun saveUserData() = screenModelScope.launch {
        mongoDB.saveUserData(ProfileModel().apply {
            id = 1
            _uiState.value?.name?.let { this.name = it }
            _uiState.value?.jobTitle?.let { this.jobTitle = it }
            _uiState.value?.mail?.let { this.mail = it }
            _uiState.value?.phoneNumber?.let { this.phoneNumber = it }
        })
    }

    fun setFields(
        name: String? = null,
        jobTitle: String? = null,
        mail: String? = null,
        phoneNumber: Int? = null
    ) = screenModelScope.launch {
        _uiState.apply {
            emit(
                UIState(
                    userImage = this.value?.userImage,
                    name = name.takeIf { !it.isNullOrBlank() } ?: this.value?.name,
                    jobTitle = jobTitle.takeIf { !it.isNullOrBlank() } ?: this.value?.jobTitle,
                    mail = mail,
                    phoneNumber = phoneNumber ?: this.value?.phoneNumber
                )
            )
        }
    }
}

data class UIState(
    var name: String?,
    var jobTitle: String?,
    var mail: String?,
    var phoneNumber: Int?,
    var userImage: String?,
)
