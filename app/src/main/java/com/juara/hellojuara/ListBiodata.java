package com.juara.hellojuara;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.juara.hellojuara.adapter.AdapterListBasic;
import com.juara.hellojuara.model.Biodata;
import com.juara.hellojuara.utility.SharedPrefUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ListBiodata extends AppCompatActivity implements AdapterListBasic.OnItemClickListener {

    RecyclerView lstBiodata;
    private List<Biodata> biodataList;
    private AppDatabase mDb;
    private Button btnCari;
    private EditText txtCari;
    private AdapterListBasic adapter;
    String textCari;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_biodata);

        txtCari = findViewById(R.id.txtCari);
        btnCari = findViewById(R.id.btnCari);
        lstBiodata = findViewById(R.id.lstBiodata);
        lstBiodata.setHasFixedSize(true);
        lstBiodata.setLayoutManager(new LinearLayoutManager(this));

        mDb = AppDatabase.getInstance(getApplicationContext());

        btnCari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textCari = txtCari.getText().toString();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        loadDatabase(textCari);
                    }
                }).start();
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                loadDataFirebase();
            }
        }).start();
    }

    public List<Biodata> loadData(){
        List<Biodata> biodataList = null;
        String dataJson = SharedPrefUtil.getInstance(ListBiodata.this).getString("data_input");
        if (!dataJson.isEmpty()) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<Biodata>>() {}.getType();
            biodataList = gson.fromJson(dataJson, type);
            for (Biodata data : biodataList) {
                Log.i("Contact Details", data.getNama() + "-" + data.getAlamat() + "-" + data.getEmail());
            }
        }

        return biodataList;
    }

    public void loadDatabase(){
        List<Biodata> biodataList = null;
        biodataList = mDb.biodataDAO().getAll();
        adapter = new AdapterListBasic(ListBiodata.this, biodataList);
        adapter.setOnItemClickListener(ListBiodata.this);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                lstBiodata.setLayoutManager(new LinearLayoutManager(ListBiodata.this));
                lstBiodata.setItemAnimator(new DefaultItemAnimator());
                lstBiodata.setAdapter(adapter);
            }
        });
    }

    public void loadDatabase(String cari){
        List<Biodata> biodataList = null;
        biodataList = mDb.biodataDAO().findByNama(cari);
        adapter = new AdapterListBasic(ListBiodata.this, biodataList);
        adapter.setOnItemClickListener(ListBiodata.this);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                lstBiodata.setLayoutManager(new LinearLayoutManager(ListBiodata.this));
                lstBiodata.setItemAnimator(new DefaultItemAnimator());
                lstBiodata.setAdapter(adapter);
            }
        });
    }

    public void loadDataFirebase(){
        biodataList = new ArrayList<>();
        final DatabaseReference nm = FirebaseDatabase.getInstance().getReference("biodata");
        nm.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for (DataSnapshot npsnapshot : dataSnapshot.getChildren()){
                        Biodata b = npsnapshot.getValue(Biodata.class);
                        biodataList.add(b);
                    }
                    adapter = new AdapterListBasic(ListBiodata.this,biodataList);
                    lstBiodata.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onItemClick(View view, Biodata obj, int position) {
        ImageView v = view.findViewById(R.id.imgBiodata);
        v.setImageResource(R.drawable.ic_close);
        lstBiodata.invalidate();
    }
}