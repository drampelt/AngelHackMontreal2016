package montreal2016.angelhack.com.montreal2016

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_pack_profile.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import rx.android.schedulers.AndroidSchedulers
import java.util.*

/**
 * Created by daniel on 2016-06-05.
 */

class PackProfileFragment : Fragment(), AnkoLogger {
    companion object {
        val ARG_NAME = "name"
        val ARG_DESCRIPTION = "description"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_pack_profile, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val eName: String? = arguments?.getString(ARG_NAME, null)
        val eDesc: String? = arguments?.getString(ARG_DESCRIPTION, null)
        val app = activity.application as Montreal2016App
        if (eName == null) {
            profile.visibility = View.GONE
            app.netService.whichPack(app.username).observeOn(AndroidSchedulers.mainThread()).subscribe({ pack ->
                if (pack.hasPack) {
                    name.text = pack.packName
                    description.text = pack.packDescription
                    actionButton.text = "Leave Pack"
                    Picasso.with(activity).load("http://placekitten.com/400/${390 + Random().nextInt(20)}").into(profilePicture)
                    profile.visibility = View.VISIBLE
                } else {
                    noPack.visibility = View.VISIBLE
                }
            }, { throwable ->
                error("something bad happened", throwable)
            })
        } else {
            name.text = eName
            description.text = eDesc
            Picasso.with(activity).load("http://placekitten.com/400/${390 + Random().nextInt(20)}").into(profilePicture)

        }
    }
}
