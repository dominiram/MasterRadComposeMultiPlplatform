package models

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class ProfileModel: RealmObject {
    @PrimaryKey
    var id: ObjectId = ObjectId()
    var imageUrl: String = ""
    var name: String = ""
    var jobTitle: String = ""
    var mail: String = ""
    var phoneNumber: Int = 1
}
