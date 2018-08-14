package com.raju.mvvm.data.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.databinding.BaseObservable
import android.databinding.Bindable
import android.os.Parcel
import android.os.Parcelable
import android.support.annotation.NonNull
import com.google.gson.annotations.SerializedName
import com.raju.mvvm.data.model.base.ListItem
import android.databinding.BindingAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.raju.mvvm.R


/**
 * Created by Rajashekhar Vanahalli on 16/07/18.
 */
@Entity
class User : ListItem, Parcelable, BaseObservable {

    @PrimaryKey
    @NonNull
    @Bindable
    @SerializedName("login")
    var name: String? = null

    @Bindable
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
