package es.upm.dit.adsw.ej7.ejercicio7_8;

/*
 * Autores de la práctica:
 * Alberto Jiménez Aliste
 * Alejandro Marcos Arias
 * Mario Penavades Suárez
 * Daniel Suárez Souto
 */

import java.util.ArrayList;
import java.util.List;
import android.util.Log;
/**
 * RSS items filtrados.
 * Los que contienen todas las palabras de busqueda.
 */
public class FilteredRssItem {
    private static final List<RssItem> ITEMS = new ArrayList<RssItem>();
    private static final String TAG;

    private static String[] filters = new String[0];

    /**
     * Resetea la lista.
     * Elimina los item anteriores y establece nuevos filtros.
     *
     * @param words lista de palabras filtro: deben aparecer.
     */
    public static void reset(String words) {
        Log.e(FilteredRssItem.TAG, "Reseteando FilteredRssItem");
        if (words == null || words.isEmpty())
            filters = new String[0];
        else
            filters = words.toLowerCase().split(" ");
        ITEMS.clear();
    }

    /**
     * Incorpora un nuevo item si contiene todas las palabras filtro.
     *
     * @param item nuevo item a intentar
     */
    public static void add(RssItem item) {
        String title = item.title.toLowerCase();
        for (String filter : filters) {
            if (!title.contains(filter))
                return;
        }
        ITEMS.add(item);
    }

    /**
     * Getter.
     *
     * @return items que cumplen con los requisitos.
     */
    public static List<RssItem> getEntries() {
        return ITEMS;
    }
    static {
        TAG = MainActivity.class.getName();
    }
}

