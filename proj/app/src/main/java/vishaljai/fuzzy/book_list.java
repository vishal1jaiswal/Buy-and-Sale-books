package vishaljai.fuzzy;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import vishaljai.fuzzy.Model.User;

public class book_list extends ArrayAdapter<Books> {

    FirebaseUser firebaseUser;
    DatabaseReference reference;
    private Activity context;
    private List<Books> list_book;
    public book_list(Activity context,List<Books> list_book)
    {
        super(context,R.layout.list_layout,list_book);
        this.context=context;
        this.list_book=list_book;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View listViewItem=inflater.inflate(R.layout.list_layout,null,true);
        TextView textbookname=(TextView)listViewItem.findViewById(R.id.tname);
        TextView textpublish=(TextView)listViewItem.findViewById(R.id.tpublish);
        TextView textyear=(TextView)listViewItem.findViewById(R.id.tyear);
        TextView textaddress=(TextView)listViewItem.findViewById(R.id.taddress);
        final TextView textEmail=(TextView)listViewItem.findViewById((R.id.temailId));
        ImageView imgview=(ImageView)listViewItem.findViewById(R.id.timage);

        Books books=list_book.get(position);



        try {
            reference = FirebaseDatabase.getInstance().getReference("Users").child(books.getUserId());
            //reference.child(firebaseUser.getEmail());
            System.out.println("reference::"+reference);
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    User user = dataSnapshot.getValue(User.class);

                        String emailId=user.getEmailId();
                        textEmail.setText(emailId);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }


        textbookname.setText(books.getBookname());
        textpublish.setText(books.getBookpublish());
        textyear.setText(books.getBookyear());
        textaddress.setText(books.getBookaddress());
       // textEmail.setText(emailId);//set emailId here
        Glide.with(context).load(list_book.get(position).getUrl()).into(imgview);

        return listViewItem;
    }
}

