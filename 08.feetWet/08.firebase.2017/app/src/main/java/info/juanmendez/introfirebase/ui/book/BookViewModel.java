package info.juanmendez.introfirebase.ui.book;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import info.juanmendez.introfirebase.BR;
import info.juanmendez.introfirebase.model.Book;

/**
 * Created by juan on 12/8/17.
 */

public class BookViewModel extends BaseObservable{
    private Book mBook;

    @Bindable
    public Book getBook() {
        return mBook;
    }

    public void setBook(Book book) {
        mBook = book;
        notifyPropertyChanged(BR.book);
    }
}