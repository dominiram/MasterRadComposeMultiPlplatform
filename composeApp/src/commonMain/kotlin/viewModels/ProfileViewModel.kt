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
                    userImage = it.userImage,
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
            _uiState.value?.userImage?.let { this.userImage = it }
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
                    mail = mail.takeIf { !it.isNullOrBlank() } ?: this.value?.mail,
                    phoneNumber = phoneNumber ?: this.value?.phoneNumber
                )
            )
        }
    }

    fun saveUserImage(imageBytes: ByteArray) = screenModelScope.launch {
        _uiState.apply {
            emit(
                UIState(
                    userImage = imageBytes,
                    name = this.value?.name,
                    jobTitle = this.value?.jobTitle,
                    mail = this.value?. mail,
                    phoneNumber = this.value?.phoneNumber
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
    var userImage: ByteArray?,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as UIState

        if (name != other.name) return false
        if (jobTitle != other.jobTitle) return false
        if (mail != other.mail) return false
        if (phoneNumber != other.phoneNumber) return false
        if (userImage != null) {
            if (other.userImage == null) return false
            if (!userImage.contentEquals(other.userImage)) return false
        } else if (other.userImage != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name?.hashCode() ?: 0
        result = 31 * result + (jobTitle?.hashCode() ?: 0)
        result = 31 * result + (mail?.hashCode() ?: 0)
        result = 31 * result + (phoneNumber ?: 0)
        result = 31 * result + (userImage?.contentHashCode() ?: 0)
        return result
    }
}
