package com.app.mypro;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Userlist extends Activity implements OnClickListener, OnItemClickListener {

    private ListView NameList;


    // We need some kind of Adapter to made the connection between ListView UI component and SQLite data set.
    private ListAdapter userlistAdapter;

    // We need this while we read the query using Cursor and pass data
    private ArrayList<UserdetailsPojo> pojoArrayList;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userlist);

        // Initialize UI components
        NameList = (ListView) findViewById(R.id.listView);
        NameList.setOnItemClickListener(this);



        pojoArrayList = new ArrayList<UserdetailsPojo>();
        // For the third argument, we need a List that contains Strings.
        //We decided to display undergraduates names on the ListView.
        //Therefore we need to create List that contains undergraduates names
        userlistAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, populateList());

        NameList.setAdapter(userlistAdapter);

    }

    @Override
    public void onClick(View v) {
        Intent usri = new Intent(Userlist.this, Home.class);
        startActivity(usri);
    }

    // To create a List that contains undergraduate names, we have to read the SQLite database
    //We are going to do it in the separate method
    public List<String> populateList(){

        // We have to return a List which contains only String values. Lets create a List first
        List<String> NameList = new ArrayList<String>();

        // First we need to make contact with the database we have created using the DbHelper class
        AndroidOpenDbHelper openHelperClass = new AndroidOpenDbHelper(this);

        // Then we need to get a readable database
        SQLiteDatabase sqliteDatabase = openHelperClass.getReadableDatabase();

        // We need a a guy to read the database query. Cursor interface will do it for us
        //(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy)
        Cursor cursor = sqliteDatabase.query(AndroidOpenDbHelper.TABLE_NAME_USERLIST, null, null, null, null, null, null);
        // Above given query, read all the columns and fields of the table

        startManagingCursor(cursor);

        // Cursor object read all the fields. So we make sure to check it will not miss any by looping through a while loop
        while (cursor.moveToNext()) {
            // In one loop, cursor read one undergraduate all details
            // Assume, we also need to see all the details of each and every undergraduate
            // What we have to do is in each loop, read all the values, pass them to the POJO class
            //and create a ArrayList of undergraduates

            String urName = cursor.getString(cursor.getColumnIndex(AndroidOpenDbHelper.COLUMN_NAME_AVAIL_NAME));


            // Finish reading one raw, now we have to pass them to the POJO
            UserdetailsPojo urPojoClass = new UserdetailsPojo();
            urPojoClass.setName(urName);


            // Lets pass that POJO to our ArrayList which contains undergraduates as type
            pojoArrayList.add(urPojoClass);

            // But we need a List of String to display in the ListView also.
            //That is why we create "uGraduateNamesList"
            NameList.add(urName);
        }

        // If you don't close the database, you will get an error
        sqliteDatabase.close();

        return NameList;
    }

    // If you don't write the following code, you wont be able to see what you have just insert to the database
    @Override
    protected void onResume() {
        super.onResume();
        userlistAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, populateList());
        NameList.setAdapter(userlistAdapter);
    }

    // On ListView you just see the name of the undergraduate, not any other details
    // Here we provide the solution to that. When the user click on a list item, he will redirect to a page where
    //he can see all the details of the undergraduate
    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

        Toast.makeText(getApplicationContext(), "Clicked on :" + arg2, Toast.LENGTH_SHORT).show();

        // We want to redirect to another Activity when the user click an item on the ListView
        Intent usintent = new Intent(Userlist.this, Call.class);

        // We have to identify what object, does the user clicked, because we are going to pass only clicked object details to the next activity
        // What we are going to do is, get the ID of the clicked item and get the values from the ArrayList which has
        //same array id.
        UserdetailsPojo clickedObject =  pojoArrayList.get(arg2);

        // We have to bundle the data, which we want to pass to the other activity from this activity
        Bundle dataBundle = new Bundle();
        dataBundle.putString("clickedName", clickedObject.getName());

        // Attach the bundled data to the intent
        usintent.putExtras(dataBundle);

        // Start the Activity
        startActivity(usintent);

    }
}