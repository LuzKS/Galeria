package santos.souza.lucas.galeria.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import santos.souza.lucas.galeria.activity.MainActivity;

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
        View v = inflater.inflate(R.layout.item_list, parent, false); //cria os elemntos e guarda em objeto tipo View
        return new MyViewHolder(v); //guarda view em objeto myviewholder e retorna
    }

    @Override
    public int getItemCount(){
        return photos.size(); //retorna tamanho da lista
    }
}

}
