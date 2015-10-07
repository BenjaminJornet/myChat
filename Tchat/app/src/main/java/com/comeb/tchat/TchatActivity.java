package com.comeb.tchat;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by côme on 24/09/2015.
 */
public class TchatActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);


        final ListView listview = (ListView) findViewById(R.id.list);
        final ArrayList list = new ArrayList<Elem>();
        list.add(new ElemLeft("Toto", "bonjour"));
        list.add(new ElemLeft("Toto", "bonjour"));
        list.add(new ElemRight("Titi", "aurevoir"));
        list.add(new ElemRight("Titi", "aurevoir"));


    final MyAdapter arrayAdapter = new MyAdapter(this,list);

    listview.setAdapter(arrayAdapter);
        final EditText ed=(EditText)findViewById(R.id.edit);
        ImageView v=(ImageView)findViewById(R.id.send);
        v.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                String message = ed.getText().toString();
                if(!message.isEmpty()) {
                    list.add(new ElemRight("Titi", ed.getText().toString()));
                    ed.setText("");
                    arrayAdapter.notifyDataSetChanged();
                }
            }
        });
    }
}
