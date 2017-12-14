package info.juanmendez.introfirebase.ui.books;

import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import info.juanmendez.introfirebase.BR;
import info.juanmendez.introfirebase.databinding.BookItemBinding;
import info.juanmendez.introfirebase.model.Book;

/**
 * Created by juan on 12/14/17.
 */
public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.BookHolder> {

    private LayoutInflater mInflater;
    private BooksViewModel mViewModel;
    private Observable.OnPropertyChangedCallback mCallback;

    public BooksAdapter(LayoutInflater inflater, BooksViewModel viewModel) {
        mInflater = inflater;
        mViewModel = viewModel;

        mViewModel.addOnPropertyChangedCallback(mCallback = new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int brId) {
                if( brId == BR.books){
                    notifyDataSetChanged();
                }
            }
        });
    }

    private List<Book> getBooks(){

        return mViewModel.getBooks();
    }

    @Override
    public BookHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BookItemBinding binding = BookItemBinding.inflate( mInflater, parent, false);
        return new BookHolder( binding.getRoot() );
    }

    @Override
    public void onBindViewHolder(BookHolder holder, int position) {
        final Book book = getBooks().get(position);
        holder.setBook( book );
    }

    @Override
    public int getItemCount() {
        return getBooks().size();
    }

    class BookHolder extends RecyclerView.ViewHolder{

        private BookItemBinding mBookItemBinding;

        public BookHolder(View itemView) {
            super(itemView);
            mBookItemBinding = DataBindingUtil.getBinding( itemView );
            mBookItemBinding.setViewModel( mViewModel );
        }

        public void setBook(@NonNull Book book ){
            mBookItemBinding.setBook( book );
        }
    }

    public void disconnect(){
        if( mCallback != null && mViewModel != null ){
            mViewModel.removeOnPropertyChangedCallback( mCallback );
        }
    }
}
