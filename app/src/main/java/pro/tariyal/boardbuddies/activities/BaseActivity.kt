package pro.tariyal.boardbuddies.activities

import android.app.Dialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import pro.tariyal.boardbuddies.R

open class BaseActivity : AppCompatActivity() {
    private var doubleBackToExitPressedOnce=false
    private lateinit var mProgressDialog:Dialog
    private lateinit var tv_progress_text:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)

    }
    fun showCustomProgressDialog(text:String){
        mProgressDialog= Dialog(this)
        mProgressDialog!!.setContentView(R.layout.dialog_progress)

        mProgressDialog!!.show()
    }
    fun hideProgressDialog(){
        mProgressDialog.dismiss()
    }
    fun getCurrentUserId():String{
        return FirebaseAuth.getInstance().currentUser!!.uid
    }
    fun doubleBackToExit(){
        if(doubleBackToExitPressedOnce){
            super.onBackPressed()
            return
        }
        this.doubleBackToExitPressedOnce=true
        Toast.makeText(this,resources.getString(R.string.please_click_back_again_to_exit),Toast.LENGTH_SHORT).show()

        Handler().postDelayed({doubleBackToExitPressedOnce=false},1000)
    }
    fun showErrorSnackBar(message: String){
        val snackBar=Snackbar.make(findViewById(android.R.id.content),message, Snackbar.LENGTH_LONG)
        val snackbarView=snackBar.view
        snackbarView.setBackgroundColor(ContextCompat.getColor(this,R.color.snackbar_error_color))
        snackBar.show()
    }

}