package com.volianskyi.taras.testfirebase;

import android.app.ProgressDialog;
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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnAddData;
    private EditText etMail;
    private EditText etPass;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        btnAddData = (Button) findViewById(R.id.btnAddDataMainActivity);
        etMail = (EditText) findViewById(R.id.etMailMainActivity);
        etPass = (EditText) findViewById(R.id.etPassMainActivity);
        btnAddData.setOnClickListener(this);



    }

    @Override
    public void onClick(View view) {
        registerUser();
    }

    private void registerUser() {
        String mail = etMail.getText().toString().trim();
        String pass = etPass.getText().toString().trim();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(mail);

        myRef.setValue(pass);

        if (TextUtils.isEmpty(mail)) {
            Toast.makeText(this, "Enter email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(pass)) {
            Toast.makeText(this, "Enter password", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("Registration of user...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(mail, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Registread successful", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Registread Unsuccessful", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}
