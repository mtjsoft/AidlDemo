// BookController.aidl
package cn.mtjsoft.aidlservice;

import cn.mtjsoft.aidlservice.bean.Book;

interface AidlController {
        List<Book> getBookList();

        void addBookInOut(inout Book book);
}