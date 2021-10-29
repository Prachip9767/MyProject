package MoneyManager.adapters.remote.requests.responses

import com.google.gson.annotations.SerializedName

data class SignupResponse(
    @SerializedName("token")
    val token: String,
    @SerializedName("user")
    val signupUser: SignupUser
)