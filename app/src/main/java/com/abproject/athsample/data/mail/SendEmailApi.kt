package com.abproject.athsample.data.mail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.abproject.athsample.util.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.Exception
import javax.mail.AuthenticationFailedException
import javax.mail.MessagingException

/**
 * Created by Abolfazl on 5/19/21
 */
class SendEmailApi {
    val _emailStatus = MutableLiveData<Resource<String>>()
    val emailStatus: LiveData<Resource<String>> get() = _emailStatus

    private val job = Job()

    /**
     * for sending Email Please Check Out Tips Section in the Readme file or check this answer
     * https://stackoverflow.com/a/33801654/15683720
     */
    fun sendEmail(userEmail: String) =
        CoroutineScope(IO + job).launch {
            try {
                var code = ""
                for (i in 1..5) {
                    code += "${(0 until 10).random()}"
                }
                val recipient = arrayListOf(userEmail)
                val mail = Mail(
                    user = "Enter your Email here..",
                    password = "Enter your email password here..",
                    from = "Enter your Email here again!",
                    body = "We have sent you this email in response to your request to reset your password on ATH-Sample app. \n" +
                            "To reset your password, please enter this code in the application \n" +
                            "code -> $code",
                    to = recipient,
                    subject = "Resetting you password for ATH-Sample app"
                )
                _emailStatus.postValue(Resource.Loading())
                if (mail.sendEmail()) {
                    _emailStatus.postValue(
                        Resource.Success(
                            code,
                            "Email sent. Please check your Email..."
                        )
                    )
                } else {
                    _emailStatus.postValue(
                        Resource.Error(
                            null,
                            "Sending email was failed! Please try again."
                        )
                    )
                }
            } catch (authenticate: AuthenticationFailedException) {
                Timber.e("SendMailApi -> Email Authentication failed! Please check your email or password and try again.")
            } catch (messageException: MessagingException) {
                Timber.e("SendMailApi -> $messageException")
                _emailStatus.postValue(
                    Resource.Error(
                        null,
                        "Sending email was failed! Please try again."
                    )
                )
            } catch (exception: Exception) {
                Timber.e("SendMailApi -> $exception")
                _emailStatus.postValue(Resource.Error(null, "Unexpected error occurred!"))
            }
        }

}
