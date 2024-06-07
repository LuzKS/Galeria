package santos.souza.lucas.galeria.adapter;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import santos.souza.lucas.galeria.activity.MainActivity;
import santos.souza.lucas.galeria.R;
import com.example.produtos.util.Util;

public class MainAdapter extends RecyclerView.Adapter {

    MainActivity mainActivity;
    List<String> photos;

    public MainAdapter(MainActivity mainActivity, List<String> photos{
        this.mainActivity = mainActivity;
        this.photos = photos;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        LayoutInflater inflater = LayoutInflater.from(mainActivity); //cria inflador de layout para criar visualizacao correspondente a cada item de mainactivity
        View v = inflater.inflate(R.layout.list_item, parent, false); //cria os elemntos e guarda em objeto tipo View
        return new MyViewHolder(v); //guarda view em objeto myviewholder e retorna
    }

    @Override
    public int getItemCount(){
        return photos.size(); //retorna tamanho da lista
    }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position){
        ImageView imPhoto = holder.itemView.findViewById(R.id.imItem);
        int w = (int)
        mainActivity.getResources().getDimension((R.dimen.itemWidth));

        int h = (int)
        mainActivity.getResources().getDimension((R.dimen.itemHeigth));

        Bitmap bitmap = Util.getBitmap(photos.get(position), w, h);
        imPhoto.setImageBitmap(bitmap);
        imPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.startPhotoActivity(photos.get(position));
            }
        });
    }

};
