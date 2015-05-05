package com.app.mypro;

/**
 * Created by AK Viki on 4/3/2015.
 */
public interface ConditionalEvent
{

    abstract boolean startEvent() throws Exception;


    abstract boolean checkCondition() throws Exception;


    abstract void onTimeout() throws Exception;


    abstract String getOperationName();
}