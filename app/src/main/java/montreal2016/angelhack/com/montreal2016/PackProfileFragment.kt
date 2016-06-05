package montreal2016.angelhack.com.montreal2016

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_pack_profile.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import org.jetbrains.anko.onClick
import org.jetbrains.anko.support.v4.startActivity
import rx.android.schedulers.AndroidSchedulers

/**
 * Created by daniel on 2016-06-05.
 */

class PackProfileFragment : Fragment(), AnkoLogger {
    companion object {
        val ARG_PACK = "pack"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_pack_profile, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val pack: Pack? = arguments?.getParcelable(ARG_PACK)
        val app = activity.application as Montreal2016App
        if (pack == null) {
            profile.visibility = View.GONE
            app.netService.whichPack(app.username).observeOn(AndroidSchedulers.mainThread()).subscribe({ pack ->
                if (pack.hasPack) {
                    name.text = pack.packName
                    description.text = pack.packDescription
                    actionButton.text = "Chat"
                    actionButton.onClick { startActivity<ChatActivity>(ChatActivity.EXTRA_PACK to pack.packName) }
                    Picasso.with(activity).load("http://placekitten.com/400/${390 + pack.packName.hashCode() % 20}").into(profilePicture)
                    profile.visibility = View.VISIBLE
                    memberCount.text = "${pack.packMembers.size} Member${if (pack.packMembers.size == 1) "" else "s" }"
                    members.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    val adapter = UserAdapter()
                    adapter.users = pack.packMembers
                    members.adapter = adapter
                } else {
                    noPack.visibility = View.VISIBLE
                }
            }, { throwable ->
                error("something bad happened", throwable)
            })
        } else {
            name.text = pack.packName
            description.text = pack.packDescription
            Picasso.with(activity).load("http://placekitten.com/400/${390 + pack.packName.hashCode() % 20}").into(profilePicture)
            actionButton.onClick {
                app.netService.joinPack(app.username, pack.packName).subscribe({ res ->
                    (activity as PackProfileActivity).reload()
                }, {})
            }
            memberCount.text = "${pack.packUsers.size} Member${if (pack.packUsers.size == 1) "" else "s" }"
            members.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            val adapter = UserAdapter()
            adapter.users = pack.packUsers
            members.adapter = adapter
        }
    }

    inner class UserAdapter : RecyclerView.Adapter<UserAdapter.UserHolder>() {

        var users: MutableList<String> = mutableListOf()

        override fun onBindViewHolder(holder: UserHolder, position: Int) {
            holder.bind(users[position])
        }

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): UserHolder? {
            val view = LayoutInflater.from(context).inflate(R.layout.list_item_user, parent, false)
            return UserHolder(view)
        }

        override fun getItemCount(): Int = users.size

        inner class UserHolder(view: View) : RecyclerView.ViewHolder(view) {
            fun bind(user: String) {
                val image = itemView.findViewById(R.id.profilePicture) as ImageView
                val name = itemView.findViewById(R.id.username) as TextView
                Picasso.with(this@PackProfileFragment.context)
                    .load("http://placekitten.com/128/${123 + user.hashCode() % 10}")
                    .into(image)
                name.text = user
            }
        }

    }
}
