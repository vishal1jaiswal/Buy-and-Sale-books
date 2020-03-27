package vishaljai.fuzzy;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class signup extends AppCompatActivity {

    private EditText textemail,textpass;
   private Button reg,log;
   private FirebaseAuth firebaseAuth;
   DatabaseReference reference;
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
        reg=(Button)findViewById(R.id.btn_signup);
        firebaseAuth=FirebaseAuth.getInstance();
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent obj1=new Intent(signup.this,login.class);
                        startActivity(obj1);
            }
        });
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtEmail=textemail.getText().toString();
                String txtPass=textpass.getText().toString();
                if (TextUtils.isEmpty(txtEmail) || TextUtils.isEmpty(txtPass))
                {
                    Toast.makeText(signup.this,"All Fields are required",Toast.LENGTH_SHORT).show();
                }
                else if (txtPass.length()<6)
                {
                    Toast.makeText(signup.this,"Password must be at least 6 characters",Toast.LENGTH_SHORT).show();
                }
                else{
                    btnRegisterclick(txtEmail,txtPass);
                }
            }
        });

    }



    public void btnRegisterclick(final String email, final String password)
    {
        final ProgressDialog progressDialog=ProgressDialog.show(signup.this,"Please wait ....."," Processing ...",true);
        (firebaseAuth.createUserWithEmailAndPassword(textemail.getText().toString(),textpass.getText().toString())).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if(task.isSuccessful())
                {
                    FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
                    String userId=firebaseUser.getUid().trim().toString();
                    reference= FirebaseDatabase.getInstance().getReference("Users").child(userId);

                    HashMap<String,String> hashMap=new HashMap<>();
                    hashMap.put("id",userId);
                    hashMap.put("emailId",email);
                    hashMap.put("imageUrl","default");

                    reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful())
                            {
                                Toast.makeText(signup.this,"Registration Successful",Toast.LENGTH_SHORT).show();
                                Intent i=new Intent(signup.this,login.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(i);
                                finish();
                            }
                        }
                    });


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
