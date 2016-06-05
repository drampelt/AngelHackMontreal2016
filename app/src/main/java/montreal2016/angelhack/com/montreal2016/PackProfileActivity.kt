package montreal2016.angelhack.com.montreal2016

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import org.jetbrains.anko.support.v4.withArguments

class PackProfileActivity : AppCompatActivity() {

    companion object {
        val EXTRA_PACK = "pack"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pack_profile)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val pack: Pack = intent.getParcelableExtra(EXTRA_PACK)

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, PackProfileFragment().withArguments(
                PackProfileFragment.ARG_PACK to pack
            ))
            .commit()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun reload() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, PackProfileFragment())
            .commit()
    }
}
