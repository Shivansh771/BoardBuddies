package pro.tariyal.boardbuddies.activities.firebase

import android.app.Activity
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import pro.tariyal.boardbuddies.activities.MainActivity
import pro.tariyal.boardbuddies.activities.SignInActivity
import pro.tariyal.boardbuddies.activities.SignUpActivity
import pro.tariyal.boardbuddies.activities.models.User
import pro.tariyal.boardbuddies.activities.utils.Constants

class FirestoreClass {
    private val mFireStore=FirebaseFirestore.getInstance()
//
  fun registerUser(activity:SignUpActivity,userInfo:User){
      mFireStore.collection(Constants.USERS).document(getCurrentUserId()).set(userInfo, SetOptions.merge()).addOnSuccessListener {
          activity.userRegisteredSuccess()
      }.addOnFailureListener{
          e->
          Log.e(activity.javaClass.simpleName,"Error")
      }
  }

    fun signInUser(activity: Activity){
        mFireStore.collection(Constants.USERS).document(getCurrentUserId()).get().addOnSuccessListener { document->
            val logginInUser=document.toObject(User::class.java)!!
            when(activity){
                is SignInActivity->{
                    activity.signInSuccess(logginInUser)

                }
                is MainActivity->{
                    activity.updateNavigationUserDetails(logginInUser)
                }
            }
        }.addOnFailureListener{
            e->
        }
            when(activity) {
                is SignInActivity -> {
                    activity.hideProgressDialog()

                }
                is MainActivity -> {
                    activity.hideProgressDialog()
                }
            }
            Log.e("Signinuser","Error")

    }
    fun getCurrentUserId():String{

        var currentUser=FirebaseAuth.getInstance().currentUser
        var currentUSerID=""
        if(currentUser!=null){
            currentUSerID=currentUser.uid
        }
        return currentUSerID
    }


}