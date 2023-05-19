package pro.tariyal.boardbuddies.activities

import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings.Global.getString
import android.text.TextUtils
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.ktx.Firebase
import pro.tariyal.boardbuddies.R
import pro.tariyal.boardbuddies.activities.firebase.FirestoreClass
import pro.tariyal.boardbuddies.activities.models.User
import pro.tariyal.boardbuddies.databinding.ActivityIntroBinding
import pro.tariyal.boardbuddies.databinding.ActivitySignUpBinding

class SignUpActivity : BaseActivity() {
    private lateinit var toolbar:Toolbar
    private lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        toolbar=findViewById(R.id.toolbar_sign_up_activity)
        setUpActionBar()
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        binding.btnSignUp.setOnClickListener{
            registerUser()
        }

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

    private fun registerUser(){
        val name:String=binding.etName.text.toString().trim{it<=' '}
        val email:String=binding.etEmail.text.toString().trim{it<=' '}
        val password:String=binding.etPassword.text.toString()
        if(validateForm(name,email, password)){
            showProgressDialog(resources.getString(R.string.please_wait))
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password).addOnCompleteListener { task ->
                hideProgressDialog()
                if (task.isSuccessful) {
                    val firebaseUser: FirebaseUser = task.result!!.user!!
                    val registeredEmail = firebaseUser.email!!
                   val user= User(firebaseUser.uid,name,email)
                    FirestoreClass().registerUser(this,user)

                } else {
                    Toast.makeText(this, "Registration failed", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    private fun validateForm(name:String,email:String,password:String) : Boolean{
        return when{
            TextUtils.isEmpty(name)->{showErrorSnackBar("Please enter a name")
            false}
            TextUtils.isEmpty(email)->{showErrorSnackBar("Please enter an email address")
            false}
            TextUtils.isEmpty(password)->{showErrorSnackBar("Please enter an password ")
                false}

            else -> {return true}
        }
    }

    fun userRegisteredSuccess() {
        Toast.makeText(
            this,
            "Thank You for registering with us",
            Toast.LENGTH_SHORT
        ).show()
    hideProgressDialog()
        FirebaseAuth.getInstance().signOut()
        finish()
    }
}