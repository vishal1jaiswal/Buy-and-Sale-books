package vishaljai.fuzzy;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.lang.*;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class signup extends AppCompatActivity {

    private EditText textemail,textpass;
   private Button reg,log;
   private FirebaseAuth firebaseAuth;
Context context;


    @Override
    public void onBackPressed() {
    finishAffinity();

        Toast.makeText(this,"back key is pressed", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        textpass=(EditText)findViewById(R.id.id_pass);
        textemail=(EditText)findViewById(R.id.id_email);
        log=(Button)findViewById(R.id.btn_login);
        firebaseAuth=FirebaseAuth.getInstance();
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent obj1=new Intent(signup.this,login.class);
                        startActivity(obj1);
            }
        });

    }



    public void btnRegisterclick(View v)
    {
        final ProgressDialog progressDialog=ProgressDialog.show(signup.this,"Please wait ....."," Processing ...",true);
        (firebaseAuth.createUserWithEmailAndPassword(textemail.getText().toString(),textpass.getText().toString())).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if(task.isSuccessful())
                {
                    Toast.makeText(signup.this,"Registration Successful",Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(signup.this,login.class);
                    startActivity(i);
                }
                else
                {
                    Log.e("Error task",task.getException().toString());
                    Toast.makeText(signup.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


}
