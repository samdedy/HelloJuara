package com.juara.hellojuara;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.juara.hellojuara.model.Biodata;
import com.juara.hellojuara.utility.SharedPrefUtil;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class TambahData extends AppCompatActivity {

    EditText txtNama;
    RadioButton rbPria, rbWanita;
    Spinner spnPekerjaan;
    CalendarView calendarLahir;
    EditText txtAlamat, txtTelepon, txtEmail, txtCatatan;
    private DatabaseReference mDatabase;
    Button btnSimpan, btnBatal;
    int editData;
    String tanggal = "";
    private AppDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_data);

        txtNama = findViewById(R.id.txtNama);
        rbPria = findViewById(R.id.radioPria);
        rbWanita = findViewById(R.id.radioWanita);
        spnPekerjaan = findViewById(R.id.spnPekerjaan);
        calendarLahir = findViewById(R.id.calendarLahir);
        txtAlamat = findViewById(R.id.txtAlamat);
        txtTelepon = findViewById(R.id.txtTelepon);
        txtEmail = findViewById(R.id.txtEmail);
        txtCatatan = findViewById(R.id.txtCatatan);
        btnSimpan = findViewById(R.id.btnSimpan);
        btnBatal = findViewById(R.id.btnBatal);

        mappingEditData();

        btnBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mDb = AppDatabase.getInstance(getApplicationContext());

        calendarLahir.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MMMM-yyyy");
                Date date = new Date(year-1900, month, dayOfMonth);
                tanggal = sdf.format(date);
                Toast.makeText(TambahData.this, tanggal, Toast.LENGTH_SHORT).show();
            }
        });

        String dataJson = SharedPrefUtil.getInstance(TambahData.this).getString("data_input");
        if (!TextUtils.isEmpty(dataJson)){
//            mappingData(dataJson);
        } else {

        }
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public void mappingData(String json){
        Biodata biodata = new Gson().fromJson(json, Biodata.class);

        txtNama.setText(biodata.getNama());

        if (biodata.getJenis_kelamin().equalsIgnoreCase("Pria")){
            rbPria.setChecked(true);
            rbWanita.setChecked(false);
        } else if (biodata.getJenis_kelamin().equalsIgnoreCase("Wanita")){
            rbWanita.setChecked(true);
            rbPria.setChecked(false);
        } else {
            rbPria.setChecked(false);
            rbWanita.setChecked(false);
        }

        List<String> lstPekerjaan = Arrays.asList(getResources().getStringArray(R.array.pekerjaan));

        for(int x=0; x<lstPekerjaan.size(); x++){
            if (lstPekerjaan.get(x).equalsIgnoreCase(biodata.getPekerjaan())){
                spnPekerjaan.setSelection(x);
            }
        }
        txtAlamat.setText(biodata.getAlamat());
        txtTelepon.setText(biodata.getTelepon());
        txtEmail.setText(biodata.getEmail());
        txtCatatan.setText(biodata.getCatatan());

        Date dateDummy = null;
        try {
            dateDummy = new SimpleDateFormat("dd-MMMM-yyyy").parse(biodata.getTanggal_lahir());
        } catch (ParseException e){
            e.printStackTrace();
        }
        calendarLahir.setDate(dateDummy.getTime());
    }

    public void mappingEditData(){
        editData = getIntent().getIntExtra("flag",0);
        if (editData == 110){
            txtNama.setText(getIntent().getStringExtra("nama"));

            if (getIntent().getStringExtra("jenis_kelamin").equalsIgnoreCase("Pria")){
                rbPria.setChecked(true);
                rbWanita.setChecked(false);
            } else if (getIntent().getStringExtra("jenis_kelamin").equalsIgnoreCase("Wanita")){
                rbWanita.setChecked(true);
                rbPria.setChecked(false);
            } else {
                rbPria.setChecked(false);
                rbWanita.setChecked(false);
            }

            List<String> lstPekerjaan = Arrays.asList(getResources().getStringArray(R.array.pekerjaan));

            for(int x=0; x<lstPekerjaan.size(); x++){
                if (lstPekerjaan.get(x).equalsIgnoreCase(getIntent().getStringExtra("pekerjaan"))){
                    spnPekerjaan.setSelection(x);
                }
            }
            txtAlamat.setText(getIntent().getStringExtra("alamat"));
            txtTelepon.setText(getIntent().getStringExtra("telepon"));
            txtEmail.setText(getIntent().getStringExtra("email"));
            txtCatatan.setText(getIntent().getStringExtra("catatan"));

            Date dateDummy = null;
            try {
                dateDummy = new SimpleDateFormat("dd-MMMM-yyyy").parse(getIntent().getStringExtra("tanggal_lahir"));
            } catch (ParseException e){
                e.printStackTrace();
            }
            calendarLahir.setDate(dateDummy.getTime());
        }
    }

    public List<Biodata> getModelArrayString(String json){
        Gson gson = new Gson();
        Type type = new TypeToken<List<Biodata>>(){}.getType();
        List<Biodata> biodataList = gson.fromJson(json, type);
        for (Biodata data : biodataList){
            Log.i("Contact Details", data.getNama()+"-"+data.getAlamat()+"-"+data.getEmail());
        }
        return biodataList;
    }

    public boolean checkMandatory(){
        boolean pass = true;
        if (TextUtils.isEmpty(txtNama.getText().toString())){
            pass = false;
            txtNama.setError("Masukkan nama, mandatory");
        }

        if (TextUtils.isEmpty(txtEmail.getText().toString()) || !Patterns.EMAIL_ADDRESS.matcher(txtEmail.getText().toString()).matches()){
            pass = false;
            txtEmail.setError("Masukkan email dengan format yang benar");
        }

        return pass;
    }

    public void simpan(View view){
        if (checkMandatory()){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Biodata biodata = null;
                    biodata = mDb.biodataDAO().findByTelepon(txtTelepon.getText().toString());
                    if (biodata != null && editData != 110){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                showErrorDialogDifferentContent();
                            }
                        });
                    } else {
//                        mDb.biodataDAO().insertAll(generateObjectData());
                        //mDatabase.setValue(generateObjectData());// menimpa data di firebase
                        mDatabase.child("biodata").child(generateObjectData().getTelepon()).setValue(generateObjectData()); // add per ID key Telepon, namun apabila nilai id / parent nya sama maka akan mengupdate data
                        finish();
                    }
                }
            }).start();
        } else {
            showErrorDialog();
        }
    }

    public Biodata generateObjectData(){
        Biodata biodata = new Biodata();
        biodata.setNama(txtNama.getText().toString());

        if (rbPria.isChecked()){
            biodata.setJenis_kelamin("Pria");
        } else if (rbWanita.isChecked()){
            biodata.setJenis_kelamin("Wanita");
        } else {
            biodata.setJenis_kelamin("Tidak diketahui");
        }

        biodata.setPekerjaan(spnPekerjaan.getSelectedItem().toString());
        biodata.setTanggal_lahir(tanggal);
        biodata.setAlamat(txtAlamat.getText().toString());
        biodata.setEmail(txtEmail.getText().toString());
        biodata.setTelepon(txtTelepon.getText().toString());
        biodata.setCatatan(txtCatatan.getText().toString());
        return biodata;
    }

    public void showErrorDialog(){
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(TambahData.this);
        alertDialog.setTitle("Peringatan");
        alertDialog.setMessage("Mohon isi field yang mandatory")
                .setIcon(R.drawable.ic_close)
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(TambahData.this, "Cancel ditekan", Toast.LENGTH_SHORT).show();
                    }
                });

        AlertDialog alert = alertDialog.create();
        alert.show();
    }

    public void showErrorDialogDifferentContent(){
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(TambahData.this);
        alertDialog.setTitle("Peringatan");
        alertDialog.setMessage("Mohon masukkan telepon yang berbeda")
                .setIcon(R.drawable.ic_close)
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).setNegativeButton("Batal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(TambahData.this, "Cancel ditekan", Toast.LENGTH_SHORT).show();
            }
        });

        AlertDialog alert = alertDialog.create();
        alert.show();
    }

    public void showJsonDialog(String json){
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(TambahData.this);
        alertDialog.setTitle("Json");
        alertDialog.setMessage("Jsonnya adalah : " +json)
                .setIcon(R.drawable.ic_about)
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

        AlertDialog alert = alertDialog.create();
        alert.show();
    }
}