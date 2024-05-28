package models

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class ProfileModel: RealmObject {
    @PrimaryKey
    var id: Int = 0
    var imageUrl: String = ""
    var name: String = ""
    var jobTitle: String = ""
    var mail: String = ""
    var phoneNumber: Int = 1
}
