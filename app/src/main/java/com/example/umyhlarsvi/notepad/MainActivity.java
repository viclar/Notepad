package com.example.umyhlarsvi.notepad;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    GridView grid;
    public GsonObject myList;
    Gson gson;
    ArrayAdapter adapter;
    Intent intent;
    SharedPreferences prefs;
    final static String KEY_NAME = "Key";
    final static String SAVE_FILE = "File";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        grid = (GridView)findViewById(R.id.gridview1);
        gson = new Gson();
        Ladda();

        adapter = new MinArrayAdapter(this, R.layout.notes, myList.arraylist);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Andra(myList.arraylist.get(position).text,position);
            }
        });


        Button butt = (Button)findViewById(R.id.add);

        butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SkapaNy();
            }
        });


    }

    public void SkapaNy() {
        intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
    }

    public void Andra(String pos, int index){
        intent = new Intent(this, SecondActivity.class);
        intent.putExtra("change",pos);
        intent.putExtra("index",index);
        startActivity(intent);
    }



    public void Ladda(){
        prefs = getSharedPreferences(SAVE_FILE,0);
        String jsonkod = prefs.getString(KEY_NAME, null);
        if(jsonkod == null){
            Log.i("TAG", "I null: " + jsonkod);
            myList = new GsonObject();
        }
        else{
            Log.i("TAG", "Jsonkod = " + jsonkod);
            myList = gson.fromJson(jsonkod, GsonObject.class);
        }

    }

    public void Spara(){
        if (myList != null) {
            Log.i("TAG","SAVING!!!");
            String jsonstring = gson.toJson(myList);
            prefs = getSharedPreferences(SAVE_FILE, 0);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(KEY_NAME, jsonstring);
            editor.commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class MinArrayAdapter extends ArrayAdapter<Tag> {

        public MinArrayAdapter(Context context, int resource, ArrayList<Tag> objects) {
            super(context, resource, objects);
        }
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View v = inflater.inflate(R.layout.notes, grid, false);
            TextView title = (TextView)v.findViewById(R.id.titletext);
            TextView datum = (TextView)v.findViewById(R.id.datum);
            TextView remove = (TextView)v.findViewById(R.id.removebtn);
            title.setTag(myList.arraylist.get(position));
            title.setText(myList.arraylist.get(position).text);
            datum.setText(myList.arraylist.get(position).datum);


            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myList.arraylist.remove(position);
                    Spara();
                    adapter.notifyDataSetChanged();
                }
            });


            return v;
        }
    }


}
