package ht.bunexe.menfp.simpletodo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
     // declaration des variables
    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    ListView lv_Items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       this.initComponent();

    }
   public void initComponent(){
       // Initialisation des variables
       // recuper toutes les taches du fichier
       readItems();
//       items = new ArrayList<>();

       itemsAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,items);
       lv_Items = (ListView) findViewById(R.id.lv_items);
       lv_Items.setAdapter(itemsAdapter);

       setUpListViewListener();
   }

   public boolean isTaskExist(String newTask){
        return this.items.contains(newTask);
   }

    /**
     * Cette Fonction permet d'ajouter une nouvelle tache à faire
     * @return void
     * @param v
     */
    public void AddItem(View v){
        EditText etnewItem  = findViewById(R.id.tvNewItem);
        String newItemValue  = etnewItem.getText().toString();
        if(!newItemValue.isEmpty()){
            if(!this.isTaskExist(newItemValue)) {
                itemsAdapter.add(newItemValue);
                writeItems();
            }else
                Toast.makeText(getApplicationContext(),"Cette tache existe deja",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(),"Ce champ ne doit pas etre vide",Toast.LENGTH_SHORT).show();
        }
         etnewItem.setText("");
    }

    public void setUpListViewListener(){
//        Log.i("MainActivity","Activer un ecouter sur le champ List");
        lv_Items.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Log.i("MainActivity","Supprimer de la tache '"+items.get(i)+"' de la liste a la position "+i);
                items.remove(i);
                itemsAdapter.notifyDataSetChanged();
                writeItems();
                return true;
            }
        });
    }

    public File getFileDir(){
        return  new File(getFilesDir(),"todo.txt");
    }

     private void readItems(){
        try {
            items = new ArrayList<>(FileUtils.readLines(getFileDir(), Charset.defaultCharset()));
        }catch (IOException ioe){
           Log.e("MainActivity","Erreur de lire les Taches",ioe);
           items = new ArrayList<>();
        }
     }

    private void writeItems(){
        try {
           FileUtils.writeLines(getFileDir(), items);
        }catch (IOException ioe){
            Log.e("MainActivity","Erreur d'ecriture des Taches",ioe);
        }
    }

}
