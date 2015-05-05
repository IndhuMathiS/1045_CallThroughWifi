package com.app.mypro;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class Home extends Activity implements OnClickListener {


    private EditText Name;
    private Button cancelButton;
    private Button saveButton;
    private Button show;

    private ArrayList userdetailsPojoObjArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Name = (EditText) findViewById(R.id.name_edt);
        show = (Button) findViewById(R.id.save);
       show.setOnClickListener(new OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(Home.this,Userlist.class);
               startActivity(intent);
           }
       });
        cancelButton = (Button) findViewById(R.id.cancel);
        cancelButton.setOnClickListener(this);
        saveButton = (Button) findViewById(R.id.save);
        saveButton.setOnClickListener(this);

        userdetailsPojoObjArrayList = new ArrayList();


    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.cancel){
            finish();
        }else if(v.getId() == R.id.save){
            // Get the values provided by the user via the UI
            String providedname = Name.getText().toString();


            // Pass above values to the setter methods in POJO class
            UserdetailsPojo userdetailsPojoObj= new UserdetailsPojo();
            userdetailsPojoObj.setName(providedname);


            // Add an undergraduate with his all details to a ArrayList
           userdetailsPojoObjArrayList.add(userdetailsPojoObj);

            // Inserting undergraduate details to the database is doing in a separate method
            insertName(userdetailsPojoObj);

            // Release from the existing UI and go back to the previous UI
            finish();
        }


    }


    public void insertName(UserdetailsPojo parauserdetailsPojoObj){

        // First we have to open our DbHelper class by creating a new object of that
        AndroidOpenDbHelper androidOpenDbHelperObj = new AndroidOpenDbHelper(this);

        // Then we need to get a writable SQLite database, because we are going to insert some values
        // SQLiteDatabase has methods to create, delete, execute SQL commands, and perform other common database management tasks.
        SQLiteDatabase sqliteDatabase = androidOpenDbHelperObj.getWritableDatabase();

        // ContentValues class is used to store a set of values that the ContentResolver can process.
        ContentValues contentValues = new ContentValues();

        // Get values from the POJO class and passing them to the ContentValues class
        contentValues.put(AndroidOpenDbHelper.COLUMN_NAME_AVAIL_NAME, parauserdetailsPojoObj.getName());


        // Now we can insert the data in to relevant table
        // I am going pass the id value, which is going to change because of our insert method, to a long variable to show in Toast
        long affectedColumnId = sqliteDatabase.insert(AndroidOpenDbHelper.TABLE_NAME_USERLIST, null, contentValues);

        // It is a good practice to close the database connections after you have done with it
        sqliteDatabase.close();

        // I am not going to do the retrieve part in this post. So this is just a notification for satisfaction ;-)
        Toast.makeText(this, "Name saved into list", Toast.LENGTH_LONG).show();
        Intent intent_uer = new Intent(Home.this,Userlist.class);
        startActivity(intent_uer);


    }
    // intent comes here ....

}