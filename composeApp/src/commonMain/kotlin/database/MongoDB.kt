package database

import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import models.ProfileModel

class MongoDB {

    private var realm: Realm? = null

    init {
        configureRealm()
    }

    private fun configureRealm() {
        if (realm == null || realm!!.isClosed()) {
            val config = RealmConfiguration.Builder(schema = setOf(ProfileModel::class))
                .compactOnLaunch()
                .build()

            realm = Realm.open(config)
        }
    }

    fun getUserData(): Flow<ProfileModel> {
        return realm?.query<ProfileModel>(query = "id == 1", false)?.asFlow()?.map {
            if (it.list.size > 0) it.list[it.list.size - 1] else ProfileModel()
        } ?: flow { ProfileModel() }
    }

    suspend fun saveUserData(profileModel: ProfileModel) {
        realm?.write {
            runCatching {
                query<ProfileModel>("id == 1", false).first().find()?.let {
                    findLatest(it)?.let { model ->
                        model.userImage = profileModel.userImage
                        model.name = profileModel.name
                        model.jobTitle = profileModel.jobTitle
                        model.mail = profileModel.mail
                        model.phoneNumber = profileModel.phoneNumber
                    }
                } ?: copyToRealm(profileModel)
            }
        }
    }
}
