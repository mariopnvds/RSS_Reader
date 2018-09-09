package es.upm.dit.adsw.ej7.ejercicio7_8;

/*
 * Autores de la práctica:
 * Alberto Jiménez Aliste
 * Alejandro Marcos Arias
 * Mario Penavades Suárez
 * Daniel Suárez Souto
 */

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.List;
import static es.upm.dit.adsw.ej7.ejercicio7_8.R.array.arraySpinner;

public class MainActivity extends AppCompatActivity {
    private Spinner searchSpinner;
    private Button readButton;
    private ProgressBar progressBar;
    private EditText editText;
    private static final String TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.searchSpinner = (Spinner) findViewById(R.id.searchSpinner);
        this.readButton = (Button) findViewById(R.id.readButton);
        this.editText = (EditText) findViewById(R.id.editText);
        this.progressBar = (ProgressBar) findViewById(R.id.progressBar);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, arraySpinner, R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        this.searchSpinner.setAdapter(adapter);
        readButton.setOnClickListener(new ReadOnClickListener());
    }

    private class ReadOnClickListener implements OnClickListener {
        private ReadOnClickListener() {}

        @Override
        public void onClick(View view) {
            try{
                String url = MainActivity.this.getResources().getStringArray(R.array.arrayUrl)[MainActivity.this.searchSpinner.getSelectedItemPosition()];
                String palabras = MainActivity.this.editText.getText().toString();
                new RetrieveRssTask().execute(new String[]{url, palabras});
            } catch (Exception e){
                Toast.makeText(MainActivity.this.getBaseContext(), "Error", Toast.LENGTH_LONG).show();
            }
        }
    }

    private class RetrieveRssTask extends AsyncTask<String, Void, Void> {
        private RetrieveRssTask(){}

        protected void onPreExecute(){
            super.onPreExecute();
            MainActivity.this.progressBar.setVisibility(View.VISIBLE);
            Log.e(MainActivity.TAG, "Se ha metido en onPreExecute");
        }

        @Override
        protected Void doInBackground(String... strings) {
            try{
                String url = strings[0];
                String palabras = strings[1];
                List<RssItem> entries = new RssFeedDownloader().loadXmlFromNetwork(url);
                FilteredRssItem.reset(palabras);
                Log.e(MainActivity.TAG, "Está haciendo cosas en doInBackground");
                for (RssItem item : entries) {
                    FilteredRssItem.add(item);
                }
            } catch (Exception e){

            }
            return null;
        }

        protected void onPostExecute(Void v){
            super.onPostExecute(v);
            MainActivity.this.progressBar.setVisibility(View.INVISIBLE);
            Log.e(MainActivity.TAG, "Se ha metido en onPostExecute");
            Intent intent = new Intent(MainActivity.this, ListViewActivity.class);
            MainActivity.this.startActivity(intent);
        }
    }
        static {
            TAG = MainActivity.class.getName();
        }



}
