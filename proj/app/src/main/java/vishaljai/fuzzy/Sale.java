package vishaljai.fuzzy;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Sale extends AppCompatActivity {

    EditText edit_namebook;
    EditText edit_publication;
    EditText edit_year;
    EditText edit_address;
    ImageView imageView;
    EditText txtImagename;
    Uri imgUri;

    DatabaseReference databaseBook;
    private StorageReference mStorageRef;
    FirebaseAuth firebaseAuth;


    public static final String FB_storage_PATH="image/";
    public static final String FB_Database_PATH="image/";
    public static final int REQUEST_CODE=1234;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale);
        edit_namebook=(EditText)findViewById(R.id.id_bookname);
        edit_publication=(EditText)findViewById(R.id.id_publication);
        edit_year=(EditText)findViewById(R.id.id_year);
        edit_address=(EditText)findViewById(R.id.id_address);
        txtImagename=(EditText)findViewById(R.id.id_imagename);
        imageView=(ImageView)findViewById(R.id.id_image);

        databaseBook=FirebaseDatabase.getInstance().getReference("Books");
        mStorageRef = FirebaseStorage.getInstance().getReference();
    }

    public void upload_button(View view)
    {
        addbook();


    }
    public void addbook()
    {

        firebaseAuth=FirebaseAuth.getInstance();
        final FirebaseUser user=firebaseAuth.getCurrentUser();
        StorageReference ref=mStorageRef.child(FB_storage_PATH + System.currentTimeMillis()+"."+getImageExt(imgUri));
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Uploading Data");
        progressDialog.show();
        ref.putFile(imgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                String userId=user.getUid().trim().toString();
                String namebook=edit_namebook.getText().toString();
                String publication=edit_publication.getText().toString();
                String year=edit_year.getText().toString();
                String address=edit_address.getText().toString();
                String imagename=txtImagename.getText().toString();

                progressDialog.dismiss();
                if(!TextUtils.isEmpty(namebook))
                {

                    String id= databaseBook.push().getKey();
                    Books books=new Books(id,namebook,publication,year,address,imagename,taskSnapshot.getDownloadUrl().toString(),userId);
                    databaseBook.child(id).setValue(books);
                   Toast.makeText(getApplicationContext(),"data uploaded",Toast.LENGTH_SHORT).show();

                    Thread t1=new Thread()
                    {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(2000);
                                Intent b1=new Intent(Sale.this,FrontPage.class);
                                startActivity(b1);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                        }
                    };
                    t1.start();




                }
                else
                {
                    Toast.makeText(getApplicationContext(),"enter book name",Toast.LENGTH_SHORT).show();
                }


            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                    }

                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                        double progress=(100*taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                        progressDialog.setMessage("uploading data"+(int)progress+"%");

                    }
                });



    }

    public void btn_browse(View view) {

        Intent imgbr=new Intent();
        imgbr.setType("image/*");
        imgbr.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(imgbr,"Select image"),REQUEST_CODE);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode== REQUEST_CODE && resultCode== RESULT_OK && data!=null && data.getData()!=null)
        {
            imgUri=data.getData();
            try {
                Bitmap bm=MediaStore.Images.Media.getBitmap(getContentResolver(),imgUri);
                imageView.setImageBitmap(bm);


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public String getImageExt(Uri uri)
    {
        ContentResolver contentResolver=getContentResolver();
        MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }


}
