package com.example.scott.ichee;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.github.kevinsawicki.http.HttpRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Scott on 15/3/5.
 */
public class SignUpActivity extends Activity {
    EditText signUpEmailText;
    EditText signUpPwText;
    EditText signUpCheckPwText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);

        signUpEmailText = (EditText) findViewById(R.id.signUpEmailText);
        signUpPwText = (EditText) findViewById(R.id.signUpPwText);
        signUpCheckPwText = (EditText) findViewById(R.id.signUpCheckPwText);


        Button signUpOkBtn = (Button) findViewById(R.id.signUpOkBtn);

        signUpOkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DownloadTask().execute("http://140.120.15.86/iChee/user/add");

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
    private class DownloadTask extends AsyncTask<String, Long, JSONObject> {
        protected JSONObject doInBackground(String... urls) {
            Map<String, String> data = new HashMap<String, String>();
            data.put("email", signUpEmailText.getText().toString());
            data.put("password", signUpPwText.getText().toString());
            data.put("password_check", signUpCheckPwText.getText().toString());
            HttpRequest request = HttpRequest.post(urls[0]).form(data);
            JSONObject obj = null;
            String response;
            if (request.ok())
            {
                response = request.body();
                System.out.println(response);
                try {
                    obj = new JSONObject(response);
                    Log.i("dd",obj.toString());
                    //System.out.println(obj.getBoolean("status"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            return obj;
        }

        protected void onProgressUpdate(Long... progress) {
            Log.d("MyApp", "Downloaded bytes: " + progress[0]);
        }

        protected void onPostExecute(JSONObject obj) {

            try {
                boolean status = obj.getBoolean("status");
                Log.d("MyApp", String.valueOf(status));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

}

