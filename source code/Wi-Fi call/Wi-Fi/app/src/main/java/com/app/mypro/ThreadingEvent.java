package com.app.mypro;

/**
 * Created by AK Viki on 4/3/2015.
 */
public interface ThreadingEvent
{
    // Uruchomienie operacji w nowym w¹tku
    abstract void startThread() throws Exception;

    // Pobranie nazwy operacji
    abstract String getOperationName();
}