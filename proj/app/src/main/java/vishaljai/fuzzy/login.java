package vishaljai.fuzzy;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class login extends AppCompatActivity {


    EditText txtemailss,txtpass;
    Button b1;
    FirebaseAuth firebaseAuth;

  /*  public static final String MyPREFERENCES="MyPrefs";
    public static final String Email="emailKey";
    public static final String Phone="phoneKey";
    SharedPreferences sharedPreferences;*/

    @Override
    public void onBackPressed() {
        finishAffinity();

        Toast.makeText(this,"back key is pressed", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtemailss=(EditText)findViewById(R.id.id_lemail);
        txtpass=(EditText)findViewById(R.id.id_lpass);
      //  b1=(Button)findViewById(R.id.btn_llogin) ;
       // sharedPreferences=getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        firebaseAuth=FirebaseAuth.getInstance();
        FirebaseUser user=firebaseAuth.getCurrentUser();
        if (user!=null)
        {
            finish();
            startActivity(new Intent(login.this,FrontPage.class));
        }
    }

    public void btnUserLogin_click(View v) {

final ProgressDialog progressDialog=ProgressDialog.show(login.this,"Please Wait...","Processing ...",true);
        (firebaseAuth.signInWithEmailAndPassword(txtemailss.getText().toString(),txtpass.getText().toString())).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if(task.isSuccessful())
                {

                  /*  String e=txtemailss.getText().toString();
                    String ph=txtpass.getText().toString();
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString(Email,e);
                    editor.putString(Phone,ph);
                    editor.commit();
*/

                    Toast.makeText(login.this,"Login Successful",Toast.LENGTH_SHORT).show();
                    Intent s=new Intent(login.this,FrontPage.class);
                    startActivity(s);
                }

                else
                {
                    Log.e("Error",task.getException().toString());
                    Toast.makeText(login.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    public void btnForget(View v) {
        Intent fog1=new Intent(login.this,ForgetPassword.class);
        startActivity(fog1);
    }

    public void btnlogkasign(View view) {
        Intent lgre=new Intent(login.this,signup.class);
        startActivity(lgre);
    }
}
