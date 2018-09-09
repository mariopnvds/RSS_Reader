package es.upm.dit.adsw.ej7.ejercicio7_8;

/*
 * Autores de la práctica:
 * Alberto Jiménez Aliste
 * Alejandro Marcos Arias
 * Mario Penavades Suárez
 * Daniel Suárez Souto
 */

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.content.Intent;
import android.net.Uri;

public class ListViewActivity extends Activity {
    private static final String TAG;
    private RssItem item;

    protected void onCreate(Bundle savedInstaceState) {
        super.onCreate(savedInstaceState);
        setContentView(R.layout.activity_listview);
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(new RArrayAdapter(this, R.layout.activity_detailed, FilteredRssItem.getEntries()));
        listView.setOnItemClickListener(new OnItemClickListenerListViewItem());
    }

    private class OnItemClickListenerListViewItem implements OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Log.i(ListViewActivity.TAG, "Se ha metido en el browser");
            RssItem objeto = FilteredRssItem.getEntries().get(position);
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(objeto.link));
            startActivity(browserIntent);
        }
    }

    static {
        TAG = ListViewActivity.class.getName();
    }
}
