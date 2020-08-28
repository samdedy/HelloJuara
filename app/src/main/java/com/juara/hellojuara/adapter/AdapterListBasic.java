package com.juara.hellojuara.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.juara.hellojuara.ListBiodata;
import com.juara.hellojuara.R;
import com.juara.hellojuara.TambahData;
import com.juara.hellojuara.model.Biodata;

import java.util.ArrayList;
import java.util.List;

public class AdapterListBasic extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Biodata> items = new ArrayList<>();
    private DatabaseReference mDatabase;
    private Context ctx;
    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, Biodata obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public AdapterListBasic(Context context, List<Biodata> items) {
        this.items = items;
        ctx = context;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView txtNama;
        public TextView txtAlamat;
        public TextView txtTelepon;
        public TextView txtPekerjaan;
        public CardView parentLayout;
        public Button btnDelete, btnEdit;



        public OriginalViewHolder(View v) {
            super(v);
            image = v.findViewById(R.id.imgBiodata);
            txtNama = v.findViewById(R.id.txtNama);
            txtAlamat = v.findViewById(R.id.txtAlamat);
            txtPekerjaan = v.findViewById(R.id.txtPekerjaan);
            txtTelepon = v.findViewById(R.id.txtTelepon);
            parentLayout = v.findViewById(R.id.layout_utama);
            btnDelete = v.findViewById(R.id.btnDelete);
            btnEdit = v.findViewById(R.id.btnEdit);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof OriginalViewHolder) {
            mDatabase = FirebaseDatabase.getInstance().getReference();
            OriginalViewHolder view = (OriginalViewHolder) holder;

            Biodata biodata = items.get(position);
            view.txtNama.setText(biodata.getNama());
            view.txtAlamat.setText(biodata.getAlamat());
            view.txtTelepon.setText(biodata.getTelepon());
            view.txtPekerjaan.setText(biodata.getPekerjaan());

            if (position % 2 == 0){
                view.image.setImageResource(R.drawable.ic_graph);
            } else {
                view.image.setImageResource(R.drawable.ic_about);
            }
            view.parentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(view, items.get(position), position);
                    }
                }
            });
            view.btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mDatabase.child("biodata").child(items.get(holder.getAdapterPosition()).getTelepon()).setValue(null);
                    Toast.makeText(holder.itemView.getContext(),"data "+items.get(holder.getAdapterPosition()).getTelepon()+" terhapus",Toast.LENGTH_SHORT).show();
//                    notifyDataSetChanged();
                    Intent myIntent = new Intent(holder.itemView.getContext(), ListBiodata.class);
                    holder.itemView.getContext().startActivity(myIntent);
                }
            });
            view.btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent myIntent = new Intent(holder.itemView.getContext(), TambahData.class);
                    myIntent.putExtra("flag", 110);
                    myIntent.putExtra("nama", items.get(holder.getAdapterPosition()).getNama());
                    myIntent.putExtra("jenis_kelamin", items.get(holder.getAdapterPosition()).getJenis_kelamin());
                    myIntent.putExtra("pekerjaan", items.get(holder.getAdapterPosition()).getPekerjaan());
                    myIntent.putExtra("tanggal_lahir", items.get(holder.getAdapterPosition()).getTanggal_lahir());
                    myIntent.putExtra("alamat", items.get(holder.getAdapterPosition()).getAlamat());
                    myIntent.putExtra("telepon", items.get(holder.getAdapterPosition()).getTelepon());
                    myIntent.putExtra("email", items.get(holder.getAdapterPosition()).getEmail());
                    myIntent.putExtra("catatan", items.get(holder.getAdapterPosition()).getCatatan());
                    holder.itemView.getContext().startActivity(myIntent);
//                    mDatabase.child("biodata").child(items.get(holder.getAdapterPosition()).getTelepon()).setValue(null);
//                    Toast.makeText(holder.itemView.getContext(),"data "+items.get(holder.getAdapterPosition()).getTelepon()+" terupdate",Toast.LENGTH_SHORT).show();
//                    notifyDataSetChanged();

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}