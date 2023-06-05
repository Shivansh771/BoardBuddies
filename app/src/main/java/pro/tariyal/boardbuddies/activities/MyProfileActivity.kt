package pro.tariyal.boardbuddies.activities

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide

import pro.tariyal.boardbuddies.R
import pro.tariyal.boardbuddies.activities.firebase.FirestoreClass
import pro.tariyal.boardbuddies.activities.models.User
import java.io.IOException

class MyProfileActivity : BaseActivity() {


    private var mSelectedImageURI: Uri?=null
    private lateinit var toolbar:Toolbar
    private lateinit var navUserImage:ImageView
    private lateinit var name:EditText
    private lateinit var email:EditText
    private lateinit var mobile:EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_profile)
        toolbar=findViewById(R.id.toolbar_my_profile_activity)
        navUserImage=findViewById(R.id.profileUserImage)
        name=findViewById(R.id.et_name)
        email=findViewById(R.id.et_email)
        mobile=findViewById(R.id.et_mobile)
        setupActionBar()
        FirestoreClass().loadUserData(this)
        navUserImage.setOnClickListener {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED
            ) {
                // TODO (Step 8: Call the image chooser function.)
                // START
                showImageChooser()
                // END
            } else {
                /*Requests permissions to be granted to this application. These permissions
                 must be requested in your manifest, they should not be granted to your app,
                 and they should have protection level*/
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    READ_STORAGE_PERMISSION_CODE
                )
            }
        }
    }

    private fun showImageChooser(){
        val galleryIntent=Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK
            && requestCode == PICK_IMAGE_REQUEST_CODE
            && data!!.data != null
        ) {
            // The uri of selection image from phone storage.
            mSelectedImageURI = data.data

            try {
                // Load the user image in the ImageView.
                Glide
                    .with(this@MyProfileActivity)
                    .load(Uri.parse(mSelectedImageURI.toString())) // URI of the image
                    .centerCrop() // Scale type of the image.
                    .placeholder(R.drawable.ic_user_place_holder) // A default place holder
                    .into(navUserImage) // the view in which the image will be loaded.
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
    // END

    // TODO (Step 5: Check the result of runtime permission after the user allows or deny based on the unique code.)
    // START
    /**
     * This function will identify the result of runtime permission after the user allows or deny permission based on the unique code.
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == READ_STORAGE_PERMISSION_CODE) {
            //If permission is granted
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // TODO (Step 9: Call the image chooser function.)
                // START
                showImageChooser()
                // END
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(
                    this,
                    "Oops, you just denied the permission for storage. You can also allow it from settings.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
    // END

    /**
     * A function to setup action bar
     */
    private fun setupActionBar() {

        setSupportActionBar(toolbar)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.white_arrow_24)
            actionBar.title = resources.getString(R.string.my_profile_title)
        }

        toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    /**
     * A function to set the existing details in UI.
     */
    fun setUserDataInUI(user: User) {
        Glide
            .with(this@MyProfileActivity)
            .load(user.image)
            .centerCrop()
            .placeholder(R.drawable.ic_user_place_holder)
            .into(navUserImage)

        name.setText(user.name)
        email.setText(user.email)
        if (user.mobile != 0L) {
            mobile.setText(user.mobile.toString())
        }
    }

    // TODO (Step 7: Create a function for image selection from phone storage.)
    // START
    /**
     * A function for user profile image selection from phone storage.
     */

// END

    // TODO (Step 3: Create a companion object and add a constant for Read Storage runtime permission.)
    // START
    /**
     * A companion object to declare the constants.
     */
    companion object {
        //A unique code for asking the Read Storage Permission using this we will be check and identify in the method onRequestPermissionsResult
        private const val READ_STORAGE_PERMISSION_CODE = 1

        // TODO (Step 6: Add a constant for image selection from phone storage)
        // START
        private const val PICK_IMAGE_REQUEST_CODE = 2
        // END
    }
    // END
}