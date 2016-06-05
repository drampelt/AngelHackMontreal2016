package montreal2016.angelhack.com.montreal2016

import android.app.Activity
import android.database.DataSetObserver
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.KeyEvent
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import kotlinx.android.synthetic.main.activity_chat.*
import org.jetbrains.anko.onClick
import org.jetbrains.anko.opaque
import org.jetbrains.anko.textColor

class ChatActivity : AppCompatActivity() {

    companion object {
        val EXTRA_PACK = "pack"
    }

    private lateinit var chatRef: DatabaseReference
    private lateinit var app: Montreal2016App

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val pack = intent.getStringExtra(EXTRA_PACK)
        chatRef = FirebaseDatabase.getInstance().getReference("chat-$pack")

        app = application as Montreal2016App
        val adapter = ChatAdapter(chatRef.limitToLast(50), R.layout.list_item_chat, app.username)
        chatMessages.adapter = adapter
        adapter.registerDataSetObserver(object : DataSetObserver() {
            override fun onChanged() {
                super.onChanged()
                chatMessages.setSelection(adapter.count - 1)
            }
        })

        sendButton.onClick { sendMessage() }
        messageField.setOnEditorActionListener { textView, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_NULL && keyEvent.action == KeyEvent.ACTION_DOWN) {
                sendMessage()
            }
            return@setOnEditorActionListener true
        }
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

    private fun sendMessage() {
        val message = messageField.text.toString()
        if (!message.isEmpty()) {
            val chat = Chat(app.username, message)
            chatRef.push().setValue(chat)
            messageField.setText("")
        }
    }

    inner class ChatAdapter(ref: Query, layout: Int, val username: String) : FirebaseListAdapter<Chat>(ref, Chat::class.java, layout, this@ChatActivity) {
        override fun populateView(v: View, model: Chat) {
            val author = v.findViewById(R.id.author) as TextView
            val message = v.findViewById(R.id.message) as TextView
            author.text = "${model.author}: "
            if (model.author == username) {
                author.textColor = ContextCompat.getColor(this@ChatActivity, R.color.colorAccent)
            } else {
                author.textColor = ContextCompat.getColor(this@ChatActivity, R.color.colorPrimary)
            }
            message.text = model.message
        }

    }
}
