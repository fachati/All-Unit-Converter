package com.fachati.allUnitConverter;

/**
 * Created by ismailfachati on 19/03/2017.
 */
public class ConverterItem {

    private int icon;
    private String text;

    public ConverterItem( String text,int icon) {
        this.icon = icon;
        this.text = text;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
