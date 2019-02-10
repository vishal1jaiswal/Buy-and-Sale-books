package vishaljai.fuzzy;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class Buy extends AppCompatActivity {

    ListView listviewbooks;
    DatabaseReference databaseBooks;
    StorageReference mStorageRef;
    List<Books> bookslist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);

        listviewbooks=(ListView)findViewById(R.id.id_listview);
       bookslist=new ArrayList<>();
        databaseBooks=FirebaseDatabase.getInstance().getReference("Books");
        mStorageRef = FirebaseStorage.getInstance().getReference();

    }

    @Override
    protected void onStart() {
        super.onStart();
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Uploading Data");
        progressDialog.setMessage("Please wait");
        progressDialog.show();
        databaseBooks.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                bookslist.clear();

                for (DataSnapshot bookSnapshot : dataSnapshot.getChildren())
                {
                    Books books=bookSnapshot.getValue(Books.class);
                    bookslist.add(books);
                }
                book_list adapter=new book_list(Buy.this,bookslist);
                progressDialog.dismiss();
                listviewbooks.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
