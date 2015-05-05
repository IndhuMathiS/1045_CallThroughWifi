package com.app.mypro;

/**
 * Created by AK Viki on 4/3/2015.
 */
public class ObjectHolder<Type>
{
    // Pole wartoœci
    private Type value = null;

    // Ustawienie nowej wartoœci
    public void setObject(Type newObject)
    {
        value = newObject;
    }

    // Pobranie aktualnej wartoœci
    public Type getObject()
    {
        return value;
    }
}