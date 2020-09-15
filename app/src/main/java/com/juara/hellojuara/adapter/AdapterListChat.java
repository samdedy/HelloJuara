package com.juara.hellojuara.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.juara.hellojuara.AppDatabase;
import com.juara.hellojuara.Chatting;
import com.juara.hellojuara.ListBiodata;
import com.juara.hellojuara.R;
import com.juara.hellojuara.TambahData;
import com.juara.hellojuara.model.Biodata;
import com.juara.hellojuara.model.ChatModel;

import java.util.List;

public class AdapterListChat extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ChatModel> items;
    private DatabaseReference mDatabase;
    private Context ctx;

    public AdapterListChat(Context context, List<ChatModel> items) {
        this.items = items;
        ctx = context;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        public TextView txtNama;
        public TextView txtPesan;
        public TextView txtTimestamp;

        public OriginalViewHolder(View v) {
            super(v);
            txtNama = v.findViewById(R.id.txtNama);
            txtPesan = v.findViewById(R.id.txtPesan);
            txtTimestamp = v.findViewById(R.id.txtTimestamp);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat, parent, false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof OriginalViewHolder) {
            mDatabase = FirebaseDatabase.getInstance().getReference();
            OriginalViewHolder view = (OriginalViewHolder) holder;

            ChatModel chatModel = items.get(position);
            view.txtNama.setText(chatModel.getNama());
            view.txtPesan.setText(chatModel.getPesan());
            view.txtTimestamp.setText(chatModel.getTimestamp());
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}