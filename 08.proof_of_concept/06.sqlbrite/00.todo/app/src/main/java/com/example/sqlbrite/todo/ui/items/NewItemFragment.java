package com.example.sqlbrite.todo.ui.items;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.sqlbrite.todo.R;
import com.example.sqlbrite.todo.TodoApp;
import com.example.sqlbrite.todo.db.TodoItem;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.squareup.sqlbrite.BriteDatabase;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Func2;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

import static butterknife.ButterKnife.findById;

/**
 * Created by Juan on 12/28/2015.
 */
public class NewItemFragment extends DialogFragment {
    private static final String KEY_LIST_ID = "list_id";

    public static NewItemFragment newInstance( long listId ){
        Bundle arguments = new Bundle();
        arguments.putLong(KEY_LIST_ID, listId);

        NewItemFragment fragment = new NewItemFragment();
        fragment.setArguments( arguments );

        return fragment;
    }

    private final PublishSubject<String> createClicked = PublishSubject.create();

    @Inject
    BriteDatabase db;

    private long getListId() {
        return getArguments().getLong(KEY_LIST_ID);
    }

    @Override public void onAttach(Context context) {
        super.onAttach(context);
        TodoApp.objectGraph(context).inject(this);
    }


    @NonNull
    @Override public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Context context = getActivity();
        View view = LayoutInflater.from(context).inflate(R.layout.new_item, null);

        EditText name = findById(view, android.R.id.input);

        Observable.combineLatest(createClicked, RxTextView.textChanges(name),
                new Func2<String, CharSequence, String>() {
                    @Override
                    public String call(String ignored, CharSequence text) {
                        return text.toString();
                    }
                }) //
                .observeOn(Schedulers.io())
                .subscribe(description -> {
                    db.insert(TodoItem.TABLE,
                            new TodoItem.Builder().listId(getListId()).description(description).build());
                });

        return new AlertDialog.Builder(context) //
                .setTitle(R.string.new_item)
                .setView(view)
                .setPositiveButton(R.string.create, (dialog, which) -> {
                    createClicked.onNext("clicked");
                })
                .setNegativeButton(R.string.cancel, (dialog1, which1) -> {})
                .create();
    }
}
