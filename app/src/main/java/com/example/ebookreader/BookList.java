package com.example.ebookreader;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.ColorSpace;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ebookreader.Interface.ItemClickListener;
import com.example.ebookreader.Model.Book;
import com.example.ebookreader.Model.Category;
import com.example.ebookreader.ViewHolder.BookViewHolder;
import com.example.ebookreader.ViewHolder.MenuViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

public class BookList extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference bookList;

    String categoryId="";
    FirebaseRecyclerAdapter<Book, BookViewHolder> firebaseUsersAdapter = null;
    private Query query;
    private FirebaseRecyclerAdapter<Book, BookViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        //Firebase
        database = FirebaseDatabase.getInstance();
        bookList = database.getReference("Book");

        recyclerView = (RecyclerView)findViewById(R.id.recycler_book);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //get intent here
        if (getIntent() != null)
            categoryId=getIntent().getStringExtra("CategoryId");
        if (!categoryId.isEmpty() && categoryId != null){
            loadListBook(categoryId);
        }

    }

    private void loadListBook(String categoryId) {

        FirebaseRecyclerOptions<Book> options =
                new FirebaseRecyclerOptions.Builder<Book>( )
                        .setQuery(query, Book.class)
                        .build( );
        final Query menuId = bookList.orderByChild("MenuId").equalTo(categoryId);

        adapter = new FirebaseRecyclerAdapter<Book, BookViewHolder>(options) {
            @NonNull
            @Override
            public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


                return null;
            }

            @Override
            protected void onBindViewHolder(@NonNull BookViewHolder bookViewHolder, int i, @NonNull Book book) {

                bookViewHolder.book_name.setText(book.getName());
                Picasso.get().load(book.getImage()).into(bookViewHolder.book_image);

                final Book local = book;
                bookViewHolder.setItemClickListener(new ItemClickListener( ) {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Toast.makeText(BookList.this,""+local.getName(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };

        //set adapter
        Log.d("TAG",""+adapter.getItemCount() );
        recyclerView.setAdapter(adapter);



    }

}
