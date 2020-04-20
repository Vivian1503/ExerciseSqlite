package com.example.exercisesqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;

public class TambahData extends AppCompatActivity {

    int id_To_Update = 0;
    private DBHelper mydb;
    EditText etNama, etPhone, etEmail, etAlamat;
    String email;
    String formatEmail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_data);

        etNama   = (EditText) findViewById(R.id.addnama);
        etPhone  = (EditText) findViewById(R.id.addNoTelp);
        etEmail  = (EditText) findViewById(R.id.addEmail);
        etAlamat = (EditText) findViewById(R.id.addAlamat);

        mydb  = new DBHelper(this);
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            int Value = extras.getInt("id");

            if(Value>0){
                //means this is the view part not the add contact part.
                Cursor rs = mydb.getData(Value);
                id_To_Update = Value;
                rs.moveToFirst();

                String name   = rs.getString(rs.getColumnIndex(DBHelper.COLUMN_NAMA));
                String phone  = rs.getString(rs.getColumnIndex(DBHelper.COLUMN_PHONE));
                String email  = rs.getString(rs.getColumnIndex(DBHelper.COLUMN_EMAIL));
                String alamat = rs.getString(rs.getColumnIndex(DBHelper.COLUMN_ALAMAT));

                if (!rs.isClosed()){
                    rs.close();
                }

                Button btnSimpan = (Button) findViewById(R.id.btn1);
                btnSimpan.setVisibility(View.INVISIBLE);

                etNama.setText((CharSequence)name);
                etNama.setFocusable(false);
                etNama.setClickable(false);

                etPhone.setText((CharSequence)phone);
                etPhone.setFocusable(false);
                etPhone.setClickable(false);

                etEmail.setText((CharSequence)email);
                etEmail.setFocusable(false);
                etEmail.setClickable(false);

                etAlamat.setText((CharSequence)alamat);
                etAlamat.setFocusable(false);
                etAlamat.setClickable(false);
            }
        }
    }

    public void run(View view){
        email = etEmail.getText().toString().trim();
        if (etNama.getText().toString().equals("")||
                etPhone.getText().toString().equals("")||
                etEmail.getText().toString().equals("")||
                etAlamat.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(),"Data Harus Diisi Semua!", Toast.LENGTH_LONG).show();
        }
        else if (email.matches(formatEmail)){
            mydb.insertContact(etNama.getText().toString(), etPhone.getText().toString(), etEmail.getText().toString(), etAlamat.getText().toString());
            Toast.makeText(getApplicationContext(), "Insert Data Berhasil", Toast.LENGTH_LONG).show();

            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
        }
        else{
            Toast.makeText(getApplicationContext(),"Format Email Salah", Toast.LENGTH_LONG).show();
        }
    }
}
