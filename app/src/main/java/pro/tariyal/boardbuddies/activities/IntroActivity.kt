package pro.tariyal.boardbuddies.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import pro.tariyal.boardbuddies.databinding.ActivityIntroBinding

class IntroActivity : BaseActivity() {
    private lateinit var binding: ActivityIntroBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding=ActivityIntroBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        binding.btnSignUpIntro.setOnClickListener{
            startActivity(Intent(this, SignUpActivity::class.java))
        }
        binding.btnSignInIntro.setOnClickListener{
            startActivity(Intent(this, SignInActivity::class.java))
        }
    }
}