package com.fawzy.cars;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    public Context context ;
    public ArrayList<Car> cars ;
    private Listner listner ;

    public ArrayList<Car> getCars() {
        return cars;
    }

    public void setCars(ArrayList<Car> cars) {
        this.cars = cars;
    }

    public Adapter(Context context, ArrayList<Car> cars, Listner listner) {
        this.context = context;
        this.cars = cars;
        this.listner = listner ;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item,parent,false);
        return new ViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Car c = cars.get(position);
        if (c.getImage() != null && !c.getImage().isEmpty()){
            holder.imageView.setImageURI(Uri.parse(c.getImage()));
        }else {
            holder.imageView.setImageResource(R.drawable.icon_car);
        }
        holder.model.setText(c.getModel());
        holder.dpl.setText(String.valueOf(c.getDpl()));
        holder.color.setText(c.getColor());
        try{
            holder.color.setTextColor(Color.parseColor(c.getColor()));
        }catch (Exception e){
            e.printStackTrace();
        }

        holder.des.setText(c.getDescription());
        holder.imageView.setTag(c.getId());


    }

    @Override
    public int getItemCount() {
        return cars.size() ;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView model , color , dpl , des ;
        ImageView  imageView ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            model = itemView.findViewById(R.id.txt1);
            dpl = itemView.findViewById(R.id.txt2);
            color = itemView.findViewById(R.id.txt3);
            des = itemView.findViewById(R.id.txt4);
            imageView = itemView.findViewById(R.id.img);

            // hna hnstd3i al code ali mktob fal main ali na 3aizo yatnfz
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int id  = (int) imageView.getTag();
                    listner.onItemClick(id);
                }
            });
        }
    }



}
