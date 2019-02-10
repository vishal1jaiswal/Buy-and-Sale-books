package vishaljai.fuzzy;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class book_list extends ArrayAdapter<Books> {

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
        ImageView imgview=(ImageView)listViewItem.findViewById(R.id.timage);
        Books books=list_book.get(position);
        textbookname.setText(books.getBookname());
        textpublish.setText(books.getBookpublish());
        textyear.setText(books.getBookyear());
        textaddress.setText(books.getBookaddress());
        Glide.with(context).load(list_book.get(position).getUrl()).into(imgview);

        return listViewItem;
    }
}

