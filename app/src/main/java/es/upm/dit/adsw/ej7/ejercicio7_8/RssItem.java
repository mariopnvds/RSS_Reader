package es.upm.dit.adsw.ej7.ejercicio7_8;

/*
 * Autores de la práctica:
 * Alberto Jiménez Aliste
 * Alejandro Marcos Arias
 * Mario Penavades Suárez
 * Daniel Suárez Souto
 */


import java.util.Date;

/**
 * Un item RSS.
 */
public class RssItem {
    public final String id;
    public final String title;
    public final String description;
    public final String date;
    public final String link;

    /**
     * Constructor.
     */
    public RssItem(String id, String title, String description, String date, String link) {
        if (id == null || id.isEmpty())
            this.id = "id";
        else
            this.id = id;

        if (title == null || title.isEmpty())
            this.title = "";
        else
            this.title = title;

        if (description == null || description.isEmpty())
            this.description = "";
        else
            this.description = description;

        if (date == null || date.isEmpty())
            this.date = new Date().toString();
        else
            this.date = date;

        if (link == null || link.isEmpty())
            this.link = "";
        else
            this.link = link;
    }

    @Override
    public String toString() {
        return title;
    }
}