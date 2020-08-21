package com.juara.hellojuara;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.juara.hellojuara.adapter.AdapterListBasic;
import com.juara.hellojuara.model.Biodata;
import com.juara.hellojuara.utility.SharedPrefUtil;

import java.lang.reflect.Type;
import java.util.List;

public class ListBiodata extends AppCompatActivity implements AdapterListBasic.OnItemClickListener {

    RecyclerView lstBiodata;
    private AppDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_biodata);

        lstBiodata = findViewById(R.id.lstBiodata);
        mDb = AppDatabase.getInstance(getApplicationContext());

        new Thread(new Runnable() {
            @Override
            public void run() {
                loadDatabase();
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

    AdapterListBasic adapter;

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

    @Override
    public void onItemClick(View view, Biodata obj, int position) {
        ImageView v = view.findViewById(R.id.imgBiodata);
        v.setImageResource(R.drawable.ic_close);
        lstBiodata.invalidate();
    }
}