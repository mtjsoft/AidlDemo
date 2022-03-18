package cn.mtjsoft.aidlclient

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.os.RemoteException
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import cn.mtjsoft.aidlservice.AidlController
import cn.mtjsoft.aidlservice.bean.Book

class MainActivity : AppCompatActivity() {

    private lateinit var logTextView: TextView

    private var connected = false

    private var bookList: List<Book>? = null

    private var bookController: AidlController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        logTextView = findViewById(R.id.tv_log)
        findViewById<Button>(R.id.btn_getBookList).setOnClickListener(clickListener)
        findViewById<Button>(R.id.btn_addBook_inOut).setOnClickListener(clickListener)
        findViewById<Button>(R.id.btn_clear).setOnClickListener(clickListener)
        bindService()
    }

    private val serviceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            bookController = AidlController.Stub.asInterface(service)
            connected = true
            logTextView.append("onServiceConnected: $name\n")
        }

        override fun onServiceDisconnected(name: ComponentName) {
            connected = false
            logTextView.append("onServiceDisconnected: $name\n")
        }
    }

    private val clickListener: View.OnClickListener = View.OnClickListener { v ->
        when (v.id) {
            R.id.btn_getBookList -> if (connected) {
                try {
                    bookList = bookController!!.bookList
                } catch (e: RemoteException) {
                    e.printStackTrace()
                }
                log()
            }
            R.id.btn_addBook_inOut -> if (connected) {
                logTextView.append("向服务器以InOut方式添加了一本新书" + "\n")
                val book = Book("这是一本来自客户端的新书 InOut")
                try {
                    logTextView.append(book.name + "\n")
                    bookController!!.addBookInOut(book)
                    logTextView.append("新书名：" + book.name + "\n")
                } catch (e: RemoteException) {
                    e.printStackTrace()
                }
            }
            R.id.btn_clear -> {
                logTextView.text = ""
            }
        }
    }

    private fun bindService() {
        val intent = Intent()
        intent.component = ComponentName("cn.mtjsoft.aidlservice", "cn.mtjsoft.aidlservice.service.AIDLService")
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
    }

    private fun log() {
        bookList?.mapIndexed { index, book ->
            logTextView.append(book.toString() + "\n")
        }
    }
}