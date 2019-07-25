package com.example.oxfordemytology;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    String url;
    TextView t1;
    EditText editText;
    String word;
    MyDB myDB;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        t1 = findViewById(R.id.textView);
        editText = findViewById(R.id.editText);
        word = editText.getText().toString();
        button = findViewById(R.id.button2);
        myDB = new MyDB(getApplicationContext());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),search_list.class);
                startActivity(intent);
            }
        });

    }
    public void onclick(View v){
        InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        im.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);

        url = dictionaryEntries();
        request r = new request(this, t1);

        r.execute(url);
        myDB.addData(word);
    }
    private String dictionaryEntries() {
        final String language = "en";
        final String word = editText.getText().toString();
        Log.i("imp", word);
       // final String fields = "etymologies";
        //final String strictMatch = "false";
        final String word_id = word.toLowerCase();
        return "https://od-api.oxforddictionaries.com:443/api/v2/entries/" + language + "/" + word_id;
    }
}
