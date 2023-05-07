package pro.tariyal.boardbuddies.activities

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.widget.TextView
import pro.tariyal.boardbuddies.R
import pro.tariyal.boardbuddies.activities.firebase.FirestoreClass

class SplashActivity : AppCompatActivity() {
    private lateinit var tvAppName:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        tvAppName=findViewById(R.id.app_name)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)
        val typeFace:Typeface= Typeface.createFromAsset(assets,"ep-stellari-display.ttf")
        tvAppName.typeface=typeFace

        Handler().postDelayed({
            var currentUserID=FirestoreClass().getCurrentUserId()
            if(currentUserID.isNotEmpty()){
                startActivity(Intent(this,MainActivity::class.java))

            }else{
            startActivity(Intent(this, IntroActivity::class.java))}
                              finish()},1000)
    }
}