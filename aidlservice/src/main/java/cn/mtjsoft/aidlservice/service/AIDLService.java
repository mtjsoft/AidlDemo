package cn.mtjsoft.aidlservice.service;

/**
 * @author mtj
 * @date 2021/7/13
 * @desc 进程通信服务
 * @email mtjsoft3@gmail.com
 */

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import cn.mtjsoft.aidlservice.AidlController;
import cn.mtjsoft.aidlservice.bean.Book;

import java.util.ArrayList;
import java.util.List;

public class AIDLService extends Service {
    private final String TAG = "Server";

    private List<Book> bookList;

    public AIDLService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        bookList = new ArrayList<>();
        initData();
        Log.d(TAG, "AIDLService服务已onCreate");
    }

    private void initData() {
        bookList.add(new Book("Book Name"));
    }

    private final Binder mBinder = new AidlController.Stub() {
        @Override
        public List<Book> getBookList() {
            return bookList;
        }

        @Override
        public void addBookInOut(Book book) {
            if (book != null) {
                book.setName(book.getName() + "——服务器追加文字");
                bookList.add(book);
            } else {
                Log.e(TAG, "接收到了一个空对象 InOut");
            }
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
