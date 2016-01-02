package com.example.sqlbrite.todo.ui.lists;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.sqlbrite.todo.R;
import com.example.sqlbrite.todo.TodoApp;
import com.example.sqlbrite.todo.db.TodoList;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.squareup.sqlbrite.BriteDatabase;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

import static butterknife.ButterKnife.findById;

/**
 * Created by Juan on 12/28/2015.
 */
public class NewListFragment extends DialogFragment{

    public static NewListFragment newInstance(){
        return new NewListFragment();
    }

    private final PublishSubject<String> createClicked = PublishSubject.create();

    @Inject
    BriteDatabase db;

    @Override
    public void onAttach( Context context ){
        super.onAttach( context );
        TodoApp.objectGraph( context ).inject(this);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog( Bundle savedInstanceState ){
        final Context context = getActivity();
        View view = LayoutInflater.from( context ).inflate(R.layout.new_list, null);
        EditText name = findById(view, android.R.id.input);
        Observable.combineLatest(createClicked, RxTextView.textChanges(name),
                new Func2<String, CharSequence, String>() {
                    @Override
                    public String call(String ignored, CharSequence text) {
                        return text.toString();
                    }
                }) //
                .observeOn(Schedulers.io())
                .subscribe(new Action1<String>() {
                    @Override public void call(String name) {
                        db.insert(TodoList.TABLE, new TodoList.Builder().name(name).build());
                    }
                });

        return new AlertDialog.Builder(context) //
                .setTitle(R.string.new_list)
                .setView(view)
                .setPositiveButton(R.string.create, new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface dialog, int which) {
                        createClicked.onNext("clicked");
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override public void onClick(@NonNull DialogInterface dialog, int which) {
                    }
                })
                .create();
    }

}
