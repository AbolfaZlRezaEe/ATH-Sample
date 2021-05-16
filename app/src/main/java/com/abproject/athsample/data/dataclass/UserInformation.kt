package com.abproject.athsample.data.dataclass

import timber.log.Timber

object UserInformation {
    var isExisting: Boolean = false
        private set
    var firstName: String? = null
        private set
    var lastName: String? = null
        private set
    var email: String? = null
        private set
    var username: String? = null
        private set


    fun updateIsExisting(existing: Boolean) {
        this.isExisting = existing
    }

    fun updateUserInformation(
        username: String,
        email: String,
        firstName: String,
        lastName: String
    ) {
        this.firstName = firstName
        this.lastName = lastName
        this.email = email
        this.username = username
    }

    fun clearInformation() {
        isExisting = false
        firstName = null
        lastName = null
        email = null
        username = null
    }
}