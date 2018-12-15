package info.juanmendez.introfirebase.ui.books;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableBoolean;

import java.util.ArrayList;
import java.util.List;

import info.juanmendez.introfirebase.BR;
import info.juanmendez.introfirebase.model.Book;

/**
 * Created by juan on 12/14/17.
 */

public class BooksViewModel extends BaseObservable{

    private List<Book> mBooks = new ArrayList<>();
    private Book mBook = new Book();

    @Bindable
    public final ObservableBoolean isLoading = new ObservableBoolean(true);


    @Bindable
    public List<Book> getBooks() {
        return mBooks;
    }

    public void setBooks(List<Book> books) {
        mBooks.clear();
        mBooks.addAll( books );
        notifyPropertyChanged( BR.books);
    }


    @Bindable
    public Book getBookPicked() {
        return mBook;
    }

    public void setBookPicked(Book bookPicked) {
        this.mBook = bookPicked;
        notifyPropertyChanged(BR.bookPicked);
    }
}