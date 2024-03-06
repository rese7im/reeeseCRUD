package com.example.reeese;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String SERVER_URL = "http://192.168.1.8/CRUDPHP/create.php"; // Assuming your server is running locally
    private static final String UPDATE_URL = "http://192.168.1.8/CRUDPHP/update.php"; // Assuming your server is running locally
    private static final String DELETE_URL = "http://192.168.1.8/CRUDPHP/delete.php"; // Assuming your server is running locally



    EditText id, name, email;
    Button submitButton, updateButton, deleteButton;
    String TempID, TempName, TempEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        id = findViewById(R.id.editText);
        name = findViewById(R.id.editText1);
        email = findViewById(R.id.editText2);
        submitButton = findViewById(R.id.button);
        updateButton = findViewById(R.id.update);
        deleteButton = findViewById(R.id.delete);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetData();
                if (TempName.isEmpty() || TempEmail.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter name and email", Toast.LENGTH_SHORT).show();
                } else {
                    InsertData();
                }
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetData();
                if (TempID.isEmpty() || TempName.isEmpty() || TempEmail.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter ID, name, and email", Toast.LENGTH_SHORT).show();
                } else {
                    UpdateData();
                }
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetData();
                if (TempID.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter ID", Toast.LENGTH_SHORT).show();
                } else {
                    DeleteData();
                }
            }
        });
    }

    public void GetData() {
        TempID = id.getText().toString();
        TempName = name.getText().toString();
        TempEmail = email.getText().toString();
    }
    public void InsertData() {
        class SendPostReqAsyncTask extends AsyncTask<Void, Void, String> {
            @Override
            protected String doInBackground(Void... voids) {
                String IDHolder = TempID;
                String NameHolder = TempName;
                String EmailHolder = TempEmail;

                try {
                    URL url = new URL(SERVER_URL);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);

                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                    HashMap<String, String> postDataParams = new HashMap<>();
                    postDataParams.put("id", IDHolder);
                    postDataParams.put("name", NameHolder);
                    postDataParams.put("email", EmailHolder);

                    StringBuilder postData = new StringBuilder();
                    for (Map.Entry<String, String> param : postDataParams.entrySet()) {
                        if (postData.length() != 0)
                            postData.append('&');
                        postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                        postData.append('=');
                        postData.append(URLEncoder.encode(param.getValue(), "UTF-8"));
                    }

                    bufferedWriter.write(postData.toString());
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();

                    int responseCode = httpURLConnection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        return "Data Inserted Successfully";
                    } else {
                        return "Error: " + responseCode;
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                if (result != null) {
                    Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "Error inserting data", Toast.LENGTH_LONG).show();
                }
            }
        }

        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute();
    }

    public void UpdateData() {
        class SendPostReqAsyncTask extends AsyncTask<Void, Void, String> {
            @Override
            protected String doInBackground(Void... voids) {
                String IDHolder = id.getText().toString();  // Fetching updated ID from EditText directly
                String NameHolder = name.getText().toString();
                String EmailHolder = email.getText().toString();

                try {
                    URL url = new URL(UPDATE_URL);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);

                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                    HashMap<String, String> postDataParams = new HashMap<>();
                    postDataParams.put("id", IDHolder);
                    postDataParams.put("name", NameHolder);
                    postDataParams.put("email", EmailHolder);

                    StringBuilder postData = new StringBuilder();
                    for (Map.Entry<String, String> param : postDataParams.entrySet()) {
                        if (postData.length() != 0)
                            postData.append('&');
                        postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                        postData.append('=');
                        postData.append(URLEncoder.encode(param.getValue(), "UTF-8"));
                    }

                    bufferedWriter.write(postData.toString());
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();

                    int responseCode = httpURLConnection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        return "Data Updated Successfully";
                    } else {
                        return "Error: " + responseCode;
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                if (result != null) {
                    Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "Error updating data", Toast.LENGTH_LONG).show();
                }
            }
        }

        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute();
    }

    public void DeleteData() {
        class SendPostReqAsyncTask extends AsyncTask<Void, Void, String> {
            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(DELETE_URL);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);

                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                    String idParam = "id=" + URLEncoder.encode(TempID, "UTF-8");

                    bufferedWriter.write(idParam);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();

                    int responseCode = httpURLConnection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        return "Data Deleted Successfully";
                    } else {
                        return "Error: " + responseCode;
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                if (result != null) {
                    Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "Error deleting data", Toast.LENGTH_LONG).show();
                }
            }
        }

        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute();
    }
}