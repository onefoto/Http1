package com.example.trandar.http1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private String url;
    private OkHttpClient okHttpClient = new OkHttpClient();
    private ArrayList<Model> array;
    private ProgressDialog pDialog;
    public String is;
    public Button submit;
    public EditText edt_username;
    public EditText edt_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        submit= (Button)findViewById(R.id.btn_submit);
        edt_username=(EditText)findViewById(R.id.edt_user);
        edt_password=(EditText)findViewById(R.id.edt_password);

        new AttemptLogin().execute();

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



    public class  AttemptLogin extends AsyncTask<String, String, String>{
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Load database into android");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
            OkHttpClient okHttpClient = new OkHttpClient();

            Request.Builder builder = new Request.Builder();
            Request request = builder.url("http://192.168.1.94/check2.php").build();

            try {
                Response response = okHttpClient.newCall(request).execute();
                if (response.isSuccessful()) {
                    return response.body().string();
                } else {
                    return "Not Success - code : " + response.code();
                }
            } catch (IOException e) {
                e.printStackTrace();
                return "Error - " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String string) {
            super.onPostExecute(string);
            pDialog.dismiss();
            if (string != null){
                Toast.makeText(MainActivity.this, "Succest", Toast.LENGTH_LONG).show();

            }
            is = string;
            array = new ArrayList<>();

            try{
                JSONArray jArray = new JSONArray(is);
                JSONObject json_data=null;
                for(int i=0;i<jArray.length();i++){
                    json_data = jArray.getJSONObject(i);

                    array.add(new Model(json_data.getString("username"),
                            json_data.getString("password")
                            ));
                }
            }catch(JSONException e1){
                Toast.makeText(getBaseContext(), "No Food Found", Toast.LENGTH_LONG).show();
            }

            Log.d("ar",array.toString());

            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String user;
                    String password;
                    user = edt_username.getText().toString();
                    password=edt_password.getText().toString();

                    Log.d("ar1",user+password);

                    for(int i=0;i<array.size();i++){
                        if(user.equals(array.get(i).getUsername())&&
                                password.equals(array.get(i).getPassword())){

                            Intent intent = new Intent();
                            intent.setClass(MainActivity.this, A1.class);
                            startActivity(intent);

                        }else {
                            Toast.makeText(MainActivity.this, "not found", Toast.LENGTH_SHORT).show();
                        }


                    }
                }
            });


        }


    }
}



