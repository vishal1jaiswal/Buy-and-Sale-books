package vishaljai.fuzzy;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

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

    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);
        listviewbooks=(ListView)findViewById(R.id.id_listview);

       bookslist=new ArrayList<>();
        databaseBooks=FirebaseDatabase.getInstance().getReference("Books");
        mStorageRef = FirebaseStorage.getInstance().getReference();
        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println("posi::"+i);
                System.out.println("ii"+view);
            }
        });*/

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
                final String[] stremail=new String[bookslist.size()];
                for(int i=0;i<bookslist.size();i++)
                {
                    stremail[i]=bookslist.get(i).getUserId();
                }
                try{

                    System.out.println("start::");



                  // ArrayAdapter<String> mAdapter=new ArrayAdapter<String>(Buy.this,R.layout.list_layout,R.id.tname,stremail);
                  //  listviewbooks.setAdapter(mAdapter);
                    listviewbooks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            System.out.println("userId::"+stremail[i].toString());
                            Toast.makeText(Buy.this,stremail[i],Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(Buy.this, MessageActivity.class);
                            intent.putExtra("userid",stremail[i]);
                           startActivity(intent);
                        }
                    });
                }
                catch (Exception e)
                {
                    System.out.println("catchu");
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
