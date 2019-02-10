package vishaljai.fuzzy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    Button sup;
    Button lgn;

    @Override
    public void onBackPressed() {
        finishAffinity();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sup=(Button)findViewById(R.id.sign);
        lgn=(Button)findViewById(R.id.log);
        sup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent obj=new Intent(MainActivity.this,signup.class);
                startActivity(obj);
            }
        });
        lgn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent obj=new Intent(MainActivity.this,login.class);
                startActivity(obj);
            }
        });
    }


}
