package pro.tariyal.boardbuddies.activities

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.google.firebase.auth.FirebaseAuth
import pro.tariyal.boardbuddies.R
import pro.tariyal.boardbuddies.activities.firebase.FirestoreClass
import pro.tariyal.boardbuddies.activities.models.User
import pro.tariyal.boardbuddies.databinding.ActivitySignInBinding

class SignInActivity : BaseActivity() {
    private lateinit var toolbar:Toolbar
    private lateinit var auth:FirebaseAuth
    private lateinit var binding:ActivitySignInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        auth=FirebaseAuth.getInstance()
        super.onCreate(savedInstanceState)
        binding=ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        toolbar=findViewById(R.id.toolbar_sign_in_activity)
        setUpActionBar()
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        binding.btnSignIn.setOnClickListener{
            signInUser()
        }

    }
    fun signInSuccess(){
        hideProgressDialog()
        startActivity(Intent(this,MainActivity::class.java))
        this.finish()
    }
    private fun setUpActionBar(){
        setSupportActionBar(toolbar)
        val actionBar=supportActionBar
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setDisplayHomeAsUpEnabled(true)

            actionBar.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24dp)
        }
        toolbar.setNavigationOnClickListener{onBackPressed()}

    }
    private fun signInUser(){
        val email: String=binding.etEmail.text.toString().trim{it<=' '}
        val password:String=binding.etPassword.text.toString().trim{it<=' ' }
        if(validateForm(email,password)){
            showProgressDialog(resources.getString(R.string.please_wait))
            auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this){task->
                hideProgressDialog()
                if(task.isSuccessful){
                    Log.d(TAG,"signInWithEmail:success")
                    FirestoreClass().signInUser(this@SignInActivity)
                }
                else{
                    Log.w(TAG,"signinWithEmail:Failure",task.exception)
                    Toast.makeText(baseContext,"Authentication failed.",Toast.LENGTH_SHORT).show()
                }
            }
        }

    }
    private fun validateForm(email:String,password:String) : Boolean{
        return when{

            TextUtils.isEmpty(email)->{showErrorSnackBar("Please enter an email address")
                false}
            TextUtils.isEmpty(password)->{showErrorSnackBar("Please enter an password ")
                false}

            else -> {return true}
        }
    }
}