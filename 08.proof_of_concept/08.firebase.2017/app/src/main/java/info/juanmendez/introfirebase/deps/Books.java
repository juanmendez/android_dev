package info.juanmendez.introfirebase.deps;

import org.androidannotations.annotations.EBean;

import info.juanmendez.introfirebase.model.Book;

/**
 * Created by juan on 12/8/17.
 */
@EBean(scope = EBean.Scope.Singleton)
public class Books {
    private Book mBookEdited;

    public Book getBookEdited() {
        return mBookEdited;
    }

    public void setBookEdited(Book bookEdited) {
        mBookEdited = bookEdited;
    }
}
