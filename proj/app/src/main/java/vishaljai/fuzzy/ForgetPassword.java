package vishaljai.fuzzy;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPassword extends AppCompatActivity {
private EditText txtforget;
//private Button forget;
    FirebaseAuth firebaseAuth;

    @Override
    public void onBackPressed() {
        finishAffinity();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        txtforget=(EditText)findViewById(R.id.id_forg);
       // forget=(Button)findViewById(R.id.btn_forget);
        firebaseAuth=FirebaseAuth.getInstance();

    }

    public void btn_forget_link(View v) {

String useremail=txtforget.getText().toString().toLowerCase();

if (TextUtils.isEmpty(useremail))
{

    Toast.makeText(ForgetPassword.this,"Please write your valid Email Address",Toast.LENGTH_SHORT).show();
}
else {
    firebaseAuth.sendPasswordResetEmail(useremail).addOnCompleteListener(new OnCompleteListener<Void>() {
        @Override
        public void onComplete(@NonNull Task<Void> task) {

            if(task.isSuccessful())
            {
                Toast.makeText(ForgetPassword.this,"mail sent successfully check ur mail",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ForgetPassword.this,login.class));
            }
            else{
                String message=task.getException().getMessage();
                Toast.makeText(ForgetPassword.this,"Error occured"+message,Toast.LENGTH_SHORT).show();
            }
        }
    });
}

    }
}
