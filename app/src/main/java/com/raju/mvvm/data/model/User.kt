package com.raju.mvvm.data.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable
import android.support.annotation.NonNull
import com.google.gson.annotations.SerializedName
import com.raju.mvvm.data.model.base.ListItem

/**
 * Created by Rajashekhar Vanahalli on 16/07/18.
 */
@Entity
class User : ListItem, Parcelable {
    @PrimaryKey
    @NonNull
    @SerializedName("login")
    var name: String? = null

    @SerializedName("avatar_url")
    var avatarUrl: String? = null

    constructor() {}

    protected constructor(input: Parcel) {
        name = input.readString()
        avatarUrl = input.readString()
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, i: Int) {
        parcel.writeString(name)
        parcel.writeString(avatarUrl)
    }

    companion object {

        val CREATOR: Parcelable.Creator<User> = object : Parcelable.Creator<User> {
            override fun createFromParcel(input: Parcel): User {
                return User(input)
            }

            override fun newArray(size: Int): Array<User?> {
                return arrayOfNulls(size)
            }
        }
    }
}
