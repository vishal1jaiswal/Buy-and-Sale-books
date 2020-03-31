package vishaljai.fuzzy;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;
import vishaljai.fuzzy.Model.User;

public class FrontPage extends AppCompatActivity {
    private static final String TAG = "FrontPage";
    private static final int REQUEST_CODE = 1;

    CircleImageView profile_image;
    TextView email;
    FirebaseUser firebaseUser;
    DatabaseReference reference;
    FirebaseAuth firebaseAuth;

    @Override
    public void onBackPressed() {
        finishAffinity();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front_page);
        firebaseAuth = FirebaseAuth.getInstance();
        verifyPermissions();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        profile_image = findViewById(R.id.profile_image);
        email = findViewById(R.id.id_email);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        try {
            reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
            //reference.child(firebaseUser.getEmail());
            System.out.println("reference::"+reference);
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    User user = dataSnapshot.getValue(User.class);
                    if(user.getEmailId().length()>0) {
                        email.setText(user.getEmailId());
                        if (user.getImageUrl().equals("default")) {
                            profile_image.setImageResource(R.mipmap.ic_launcher);
                        } else {
                            Glide.with(FrontPage.this).load(user.getImageUrl()).into(profile_image);

                        }
                    }
                    else
                    {
                        email.setText("");
                        profile_image.setImageResource(R.mipmap.ic_launcher);
                    }
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.logoutmenu:

                firebaseAuth.signOut();
                finish();
                Intent et=new Intent(FrontPage.this,login.class);
                startActivity(et);
                break;

            case R.id.helpmenu:
                Intent ht=new Intent(FrontPage.this,help.class);
                startActivity(ht);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    public void buy(View view) {

        Intent sa=new Intent(FrontPage.this,Buy.class);
        startActivity(sa);
    }

    public void sale(View view) {
        Intent sa=new Intent(FrontPage.this,Sale.class);
        startActivity(sa);

    }

    private void verifyPermissions(){
        Log.d(TAG, "verifyPermissions: asking user for permissions");
        String[] permissions = {android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.CAMERA};

        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                permissions[0]) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this.getApplicationContext(),
                permissions[1]) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this.getApplicationContext(),
                permissions[2]) == PackageManager.PERMISSION_GRANTED){
            Toast.makeText(FrontPage.this,"storage allowed successfully",Toast.LENGTH_SHORT).show();
        }else{
            ActivityCompat.requestPermissions(FrontPage.this,
                    permissions,
                    REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        verifyPermissions();
    }



   /* public void btnChat(View view) {
        Toast.makeText(FrontPage.this,"button chat",Toast.LENGTH_SHORT).show();
    }*/



}




