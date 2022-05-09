package com.example.multiuimobileproject;

import static android.content.Context.MODE_PRIVATE;

import android.app.Person;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.SyncStateContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.multiuimobileproject.databinding.RecyclerRowBinding;
import com.example.multiuimobileproject.databinding.RecyclerRowBinding;

import java.util.ArrayList;

public class NotAdapter extends RecyclerView.Adapter<NotAdapter.NotHolder> //alt enter+ implements method
{

    ArrayList<Nots> notsArrayList;



    public NotAdapter(ArrayList<Nots> notsArrayList)
    {
        this.notsArrayList =notsArrayList;
    }
    @NonNull
    @Override
    public NotHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerRowBinding recyclerRowBinding= RecyclerRowBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new NotHolder(recyclerRowBinding);

    }

    @Override
    public void onBindViewHolder(@NonNull NotHolder holder, int position) {

        Context context = holder.itemView.getContext();
        holder.binding.rvNotumid.setText(notsArrayList.get(holder.getAdapterPosition()).notum);
        holder.binding.rvtarihid.setText(notsArrayList.get(holder.getAdapterPosition()).tarihim);
        holder.binding.rvsaatid.setText(notsArrayList.get(holder.getAdapterPosition()).saatim);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(holder.itemView.getContext(),NotalActivity.class);
                intent.putExtra("info","old");
                intent.putExtra("notid",notsArrayList.get(holder.getAdapterPosition()).id);
                holder.itemView.getContext().startActivity(intent);
            }
        });


        holder.binding.kisiyisilmeImageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                new AlertDialog.Builder(context)
                        .setTitle("Notu Sil")
                        .setMessage("Notu Listeden Kaldırmak İstermisiniz?")
                        .setNegativeButton("Hayır", null)
                        .setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // SQLiteDatabase

                                //listeden silme

                                notsArrayList.remove(holder.getAdapterPosition());
                                notifyItemRemoved(holder.getAdapterPosition());


                            }
                        }).show();



              //  Cursor cursor=database.rawQuery("DELETE FROM notlar WHERE id=?",new String[]);
            }
        });
    }

    @Override
    public int getItemCount() {
        return notsArrayList.size();
    }

    public class NotHolder extends RecyclerView.ViewHolder{
        private RecyclerRowBinding binding;

        public NotHolder(RecyclerRowBinding binding) {


            super(binding.getRoot());
            this.binding=binding;
        }
    }


}