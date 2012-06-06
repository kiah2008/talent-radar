package test.test3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
 
import library.DatabaseHandler;
import library.LoginTask;
 
public class LoginActivity extends Activity {
    Button btnLogin;
    Button btnLinkToRegister;
    EditText inputEmail;
    EditText inputPassword;
    public TextView loginErrorMsg;
    
    //private static String registerURL = "http://172.20.20.103/test_android/users/registerFromApp/";
    protected ProgressDialog progressDialog;
    
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
 
        // Importing all assets like buttons, text fields
        inputEmail = (EditText) findViewById(R.id.loginEmail);
        inputPassword = (EditText) findViewById(R.id.loginPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLinkToRegister = (Button) findViewById(R.id.btnLinkToRegisterScreen);
        loginErrorMsg = (TextView) findViewById(R.id.login_error);
 
        // Login button Click Event
        btnLogin.setOnClickListener(new View.OnClickListener() {
 
            public void onClick(View view) {
            	
            	String email = inputEmail.getText().toString();
                String password = inputPassword.getText().toString();
            	
            	ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
    			progressDialog.setMessage("Logging in...");
    			progressDialog.setCancelable(false);
    			
    			LoginTask loginTask = new LoginTask(LoginActivity.this, progressDialog);
    			loginTask.execute(email, password);
    			
            }
        });
 
        // Link to Register Screen
        btnLinkToRegister.setOnClickListener(new View.OnClickListener() {
 
            public void onClick(View view) {
                /*Intent i = new Intent(getApplicationContext(),
                        RegisterActivity.class);
                startActivity(i);*/
                finish();
            }
        });
    }
    
    public class PostTask extends AsyncTask<ArrayList<NameValuePair>, String, InputStream> {

    	List<NameValuePair> data = new ArrayList<NameValuePair>();
    	
    	@Override
        protected void onPreExecute()
        {
    		super.onPreExecute();
    	    progressDialog.show();
        }

    	
    	@Override
        protected InputStream doInBackground(ArrayList<NameValuePair>... params) {
            // Making HTTP request
            
    		InputStream resultContent = null;
    		
    		try {
                // defaultHttpClient
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(params[0].get(2).toString().split("=")[1]);
                httpPost.setEntity(new UrlEncodedFormEntity(params[0]));
     
                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                resultContent = httpEntity.getContent();
     
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        	//If you want to do something on the UI use progress update
            //publishProgress("progress");
            
            return resultContent;
        }
    	
    	@Override
        protected void onPostExecute(InputStream result)
        {
    		super.onPostExecute(result);
            progressDialog.dismiss();
        }
    	
        /*protected void onProgressUpdate(String... progress) {
            StringBuilder str = new StringBuilder();
                for (int i = 1; i < progress.length; i++) {
                    str.append(progress[i] + " ");
                }

        }*/
    }
}