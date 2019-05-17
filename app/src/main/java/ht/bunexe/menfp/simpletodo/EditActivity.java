package ht.bunexe.menfp.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class EditActivity extends AppCompatActivity {
     int indexMainList =-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        setTitle("Edit Todo");
        Intent fromMainIntent = getIntent();
        String todoToEdit = fromMainIntent.getStringExtra("todoToEdit");
        String[] r = todoToEdit.split("-",2);
        indexMainList = Integer.parseInt(r[0]);
        TextView t = (TextView) findViewById(R.id.editTodo);
        t.setText(r[1].toString());
       // t.setText(todoToEdit);
    }

    public void goback(View v){
        TextView t = (TextView) findViewById(R.id.editTodo);
        String newText = String.valueOf(indexMainList).concat("-").concat( t.getText().toString());

        Intent intentMainActivity = new Intent(this,MainActivity.class);
        intentMainActivity.putExtra("TodoEdited",newText);
        setResult(RESULT_OK,intentMainActivity);
        finish();

    }
}
