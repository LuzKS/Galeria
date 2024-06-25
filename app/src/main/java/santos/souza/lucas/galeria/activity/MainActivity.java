package santos.souza.lucas.galeria.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.example.produtos.util.Util;

import santos.souza.lucas.galeria.R;
import santos.souza.lucas.galeria.adapter.MainAdapter;

public class MainActivity extends AppCompatActivity {

    List<String> photos = new ArrayList<>();
    MainAdapter mainAdapter;
    static int RESULT_TAKE_PICTURE = 1;
    String currentPhotoPath;
    static int RESULT_REQUEST_PERMISSION = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //acessam diretório, le a lista de ftos salvas e adicionam na lista de fotos
        File dir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File[] files = dir.listFiles();
        for(int i = 0; i< files.length; i++){
            photos.add(files[i].getAbsolutePath());
        }

        mainAdapter = new MainAdapter(MainActivity.this, photos);
        RecyclerView rvGallery = findViewById(R.id.rvGallery);
        rvGallery.setAdapter(mainAdapter);

        float w = getResources().getDimension(R.dimen.itemWidth);
        int numberOfColumns = Util.calculateNoOfColumns(MainActivity.this, w);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, numberOfColumns);
        rvGallery.setLayoutManager(gridLayoutManager);

        Toolbar toolbar = findViewById(R.id.tbMain);
        setSupportActionBar(toolbar); //configura como actionbar padrao
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater(); //pega e cria as opções do outro arquivo de menu
        inflater.inflate(R.menu.main_activity_tb, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        //sempre q um item for chamado, se icone for clicado, a camera sera disparada
        if (item.getItemId() == R.id.opCamera) {
            dispatchTakePictureIntent();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public  void startPhotoActivity(String photoPath){ //quando usuario clica em fot, o caminho é passado pra photoactivity
        Intent i = new Intent(MainActivity.this, PhotoActivity.class);
        i.putExtra("photo_path", photoPath);
        startActivity(i);
    }

    private void dispatchTakePictureIntent(){

        File f = null;
        try {
            f = createImageFile(); //cria arquivo em pasta pcture e retorna erro se n for possivel
        } catch (IOException e){
            Toast.makeText(MainActivity.this, "Não foi possível criar o arquivo", Toast.LENGTH_LONG).show();
            return;
        }
        currentPhotoPath = f.getAbsolutePath();

        if(f != null){//cria URI e dispara camera, passando uri dps
            Uri fUri = FileProvider.getUriForFile(MainActivity.this, "santos.souza.lucas.galeria.fileprovider", f);
            Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            i.putExtra(MediaStore.EXTRA_OUTPUT, fUri);
            startActivityForResult(i, RESULT_TAKE_PICTURE);
        }
    }
    private File createImageFile() throws IOException{ //cria nomes unicos de foto utilizando data e hora
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp;
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File f = File.createTempFile(imageFileName, ".jpg", storageDir);
        return f;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESULT_TAKE_PICTURE){
            if (resultCode == Activity.RESULT_OK){
                photos.add(currentPhotoPath); //adiciona o local dela na lista de foto
                mainAdapter.notifyItemInserted(photos.size()-1); //avisa q nova foto foi inserida
            }
            else{
                File f = new File(currentPhotoPath);
                f.delete(); // arquivo criado pra conter a foto é deletado se a fto n for tirada
            }
        }
    }

    private void checkForPermissions(List<String> permissions){

    }

}