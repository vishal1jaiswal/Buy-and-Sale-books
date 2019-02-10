package vishaljai.fuzzy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.test.AndroidTestRunner;
import android.util.AndroidException;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import java.lang.String;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class FrontPage extends AppCompatActivity {
    private static final String TAG = "FrontPage";
    private static final int REQUEST_CODE = 1;

FirebaseAuth firebaseAuth;

    @Override
    public void onBackPressed() {
        finishAffinity();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front_page);
        firebaseAuth=FirebaseAuth.getInstance();
        verifyPermissions();
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
}




