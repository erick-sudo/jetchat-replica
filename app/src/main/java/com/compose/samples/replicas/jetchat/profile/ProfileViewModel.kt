package com.compose.samples.replicas.jetchat.profile

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Immutable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.compose.samples.replicas.jetchat.data.colleagueProfile
import com.compose.samples.replicas.jetchat.data.meProfile

class ProfileViewModel: ViewModel() {

    private var userId: String = ""

    fun setUserId(newUSerId: String?) {
        if(newUSerId != userId) {
            userId = newUSerId ?: meProfile.userId
        }

        _userData.value = if(userId == meProfile.userId || userId == meProfile.displayName) {
            meProfile
        } else {
            colleagueProfile
        }
    }

    private val _userData = MutableLiveData<ProfileScreenState>()
    val userData: LiveData<ProfileScreenState> = _userData
}

@Immutable
data class ProfileScreenState(
    val userId: String,
    @DrawableRes val photo: Int?,
    val name: String,
    val status:  String,
    val displayName: String,
    val position: String,
    val twitter: String = "",
    val timeZone: String?,
    val commonChannels: String?
) {
    fun isMe() = userId == meProfile.userId
}