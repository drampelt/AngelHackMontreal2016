package montreal2016.angelhack.com.montreal2016

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_profile.*
import java.util.*

class ProfileActivity : AppCompatActivity() {

    companion object {
        val EXTRA_NAME = "name"
        val EXTRA_DESCRIPTION = "description"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val eName = intent.getStringExtra(EXTRA_NAME)
        val eDesc = intent.getStringExtra(EXTRA_DESCRIPTION)
        // TODO api call
        name.text = eName
        description.text = eDesc
        Picasso.with(this).load("http://placekitten.com/400/${390 + Random().nextInt(20)}").into(profilePicture)
    }
}
