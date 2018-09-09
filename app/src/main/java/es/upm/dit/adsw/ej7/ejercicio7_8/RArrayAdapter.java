package es.upm.dit.adsw.ej7.ejercicio7_8;

/*
 * Autores de la práctica:
 * Alberto Jiménez Aliste
 * Alejandro Marcos Arias
 * Mario Penavades Suárez
 * Daniel Suárez Souto
 */

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class RArrayAdapter extends ArrayAdapter<RssItem> {
    private List<RssItem> data;
    private int layoutId;
    private Context mContext;

    public RArrayAdapter(Context mContext, int layoutId, List<RssItem> data) {
        super(mContext, layoutId, data);
        this.layoutId = layoutId;
        this.mContext = mContext;
        this.data = data;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(this.mContext);
            convertView = inflater.inflate(this.layoutId, parent, false);
        }
        TextView fecha = (TextView) convertView.findViewById(R.id.rssItemDate);
        TextView descripcion = (TextView) convertView.findViewById(R.id.rssItemDescription);
        RssItem item = (RssItem) this.data.get(position);
        ((TextView) convertView.findViewById(R.id.rssItemTitle)).setText(item.title);
        fecha.setText(item.date);
        //Elimina las etiquetas html que aparezcan, por ejemplo, en SlashDat
        descripcion.setText(Html.fromHtml(item.description));
        return convertView;
    }
}
