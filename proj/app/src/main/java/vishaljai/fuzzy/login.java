package vishaljai.fuzzy;

import android.app.ProgressDialog;
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

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class login extends AppCompatActivity {


    EditText txtemailss,txtpass;
    Button buttonLogin;
    FirebaseAuth firebaseAuth;
    DatabaseReference reference;

    private static final String TAG = "Raven";


    GoogleSignInClient mGoogleSignInClient;
    private final static int RC_SIGN_IN=2;
   // FirebaseAuth mAuth;
    SignInButton button;

    FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    public void onStart() {
        super.onStart();
        //FirebaseUser currentUser = mAuth.getCurrentUser();


      /*  if(currentUser!=null)
        {
            startActivity(new Intent(MainActivity.this,FrontPage.class));
        }
    }*/
        firebaseAuth.addAuthStateListener(mAuthListener);

    }

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
       buttonLogin=(Button)findViewById(R.id.btn_llogin) ;
       buttonLogin.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               String txtEmail=txtemailss.getText().toString();
               String txtPass=txtpass.getText().toString();
               if (TextUtils.isEmpty(txtEmail) || TextUtils.isEmpty(txtPass))
               {
                   Toast.makeText(login.this,"All Fields are required",Toast.LENGTH_SHORT).show();
               }
               else if (txtPass.length()<6)
               {
                   Toast.makeText(login.this,"Password must be at least 6 characters",Toast.LENGTH_SHORT).show();
               }
               else{
                   btnUserLogin_click(txtEmail,txtPass);
               }
           }
       });
       // sharedPreferences=getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        firebaseAuth=FirebaseAuth.getInstance();
        FirebaseUser user=firebaseAuth.getCurrentUser();
        if (user!=null)
        {
            finish();
            startActivity(new Intent(login.this,FrontPage.class));
        }



        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {
                    startActivity(new Intent(login.this, FrontPage.class));

                }
            }

        };



        button=(SignInButton) findViewById(R.id.sign_in_button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressDialog=ProgressDialog.show(login.this,"Please Wait...","Processing ...",true);
                signIn();
            }
        });

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);



    }





    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                // System.out.println("getAccount--");
                System.out.println("getAccount--"+account.getAccount().name);

                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(final GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);

        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        final ProgressDialog progressDialog=ProgressDialog.show(login.this,"Please Wait...","Processing ...",true);
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            String email=acct.getAccount().name;
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
                                        System.out.println("account-- "+acct.getAccount().name);
                                        Log.d(TAG, "signInWithCredential:success");
                                        Toast.makeText(login.this,"SignUp Successful",Toast.LENGTH_SHORT).show();
                                       // Intent i=new Intent(signup.this,login.class);
                                      //  i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                       // startActivity(i);
                                        finish();
                                    }
                                }
                            });
                            // Sign in success, update UI with the signed-in user's information


                            //updateUI(user);
                        } else {
                            progressDialog.dismiss();
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(login.this,"Authentication Failed.",Toast.LENGTH_SHORT).show();
                            //Snackbar.make(findViewById(R.id.main_layout), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });
    }












//on button click login

    public void btnUserLogin_click(String email,String password) {

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
                    finish();
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
