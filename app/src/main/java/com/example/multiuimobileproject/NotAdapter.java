package com.example.multiuimobileproject;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
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

        holder.binding.rvNotumid.setText(notsArrayList.get(position).notum);
        holder.binding.rvtarihid.setText(notsArrayList.get(position).tarihim);
        holder.binding.rvsaatid.setText(notsArrayList.get(position).saatim);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(holder.itemView.getContext(),NotalActivity.class);
                intent.putExtra("info","old");
                intent.putExtra("notid",notsArrayList.get(position).id);
                holder.itemView.getContext().startActivity(intent);
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