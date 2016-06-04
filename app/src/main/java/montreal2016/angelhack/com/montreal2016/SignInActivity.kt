package montreal2016.angelhack.com.montreal2016

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_sign_in.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import org.jetbrains.anko.onClick
import org.jetbrains.anko.startActivity

class SignInActivity : AppCompatActivity(), AnkoLogger {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        val app = application as Montreal2016App

        signIn.onClick {
            val name = name.text.toString()
            if (name.isNullOrEmpty()) return@onClick

            app.netService.login(name).subscribe({ res ->
                app.name = res.string()
                startActivity<MainActivity>()
            }, { throwable ->
                error("Something bad happened", throwable)
            })
        }
    }
}
