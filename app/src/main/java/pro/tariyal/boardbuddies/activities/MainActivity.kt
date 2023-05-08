package pro.tariyal.boardbuddies.activities

import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import com.bumptech.glide.Glide
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.auth.User
import com.google.firebase.ktx.Firebase
import pro.tariyal.boardbuddies.R
import pro.tariyal.boardbuddies.activities.firebase.FirestoreClass
import pro.tariyal.boardbuddies.databinding.ActivityMainBinding

class MainActivity : BaseActivity() ,NavigationView.OnNavigationItemSelectedListener{
    private lateinit var toolbar: Toolbar
    private lateinit var binding:ActivityMainBinding

    private lateinit var tvUserName:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        toolbar=findViewById(R.id.toolbar_main_activity)
        setupActionBar()
        binding.navView.setNavigationItemSelectedListener(this)
        //tvUserName=findViewById(R.id.tv_username)
        FirestoreClass().signInUser(this)
    }
    private fun setupActionBar(){

        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_action_navigation_menu)
        toolbar.setNavigationOnClickListener(){
            //toggle drawer
            toggleDrawer()
        }

    }
    private fun toggleDrawer(){
        if(binding.drawerLayout.isDrawerOpen(GravityCompat.START)){
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }else{
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }
    }

    override fun onBackPressed() {
        if(binding.drawerLayout.isDrawerOpen(GravityCompat.START)){
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }else{
            doubleBackToExit()
        }

    }
    fun updateNavigationUserDetails(user:pro.tariyal.boardbuddies.activities.models.User){
            tvUserName.text="Hi "+user.name

    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_my_profile->{
                Toast.makeText(this@MainActivity,"My profile",Toast.LENGTH_SHORT).show()

            }
            R.id.nav_sign_out->{
                FirebaseAuth.getInstance().signOut()
               val intent=Intent(this,IntroActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
            }
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)

   return true
    }

}