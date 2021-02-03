package com.example.asiancountries;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou;
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYouListener;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private List<Country> allCountries;
    private Context context;

    public Adapter(List<Country> allCountries, Context context) {
        this.allCountries = allCountries;
        this.context = context;
    }

    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.single_country_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {

        String name = allCountries.get(position).getName();
        String flag = allCountries.get(position).getFlag();

        holder.countryName.setText(name);

        GlideToVectorYou
                .init()
                .with(context)
                .withListener(new GlideToVectorYouListener() {
                    @Override
                    public void onLoadFailed() {
                        //Toast.makeText(CountryInfoActivity.this, "Load failed", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResourceReady() {
                        //Toast.makeText(CountryInfoActivity.this, "Image ready", Toast.LENGTH_SHORT).show();
                    }
                })
                .setPlaceHolder(R.drawable.ic_baseline_photo_24, R.drawable.ic_baseline_photo_24)
                .load(Uri.parse(flag), holder.countryFlagImageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent countryIntent = new Intent(context, CountryInfoActivity.class);
                countryIntent.putExtra("name", allCountries.get(position).getName());
                context.startActivity(countryIntent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return allCountries.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView countryName;
        private ImageView countryFlagImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            countryName = itemView.findViewById(R.id.countryNameTexttView);
            countryFlagImageView = itemView.findViewById(R.id.countryFlagImageView);

        }
    }
}
