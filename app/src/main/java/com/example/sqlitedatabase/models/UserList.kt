package com.example.sqlitedatabase.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class UserList(
    var id: Int?,
    var name: String,
    var mobileNumber: String?,
    var email: String?,
    var gender: String,
    var address: String?
):Parcelable





