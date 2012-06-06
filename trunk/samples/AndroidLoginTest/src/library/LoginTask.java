package library;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import test.test3.DashboardActivity;
import test.test3.LoginActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

public class LoginTask extends AsyncTask<String, Void, Integer> {

	private static String loginURL = "http://172.20.20.103/PHPServiceLoginTest/users/loginFromApp/";
	
	static JSONObject jObj = null;
    static String json = "";
    // JSON Response node names
    private static String KEY_SUCCESS = "success";
    private static String KEY_ERROR = "error";
    private static String KEY_ERROR_MSG = "error_msg";
    private static String KEY_UID = "uid";
    private static String KEY_NAME = "name";
    private static String KEY_EMAIL = "email";
    private static String KEY_CREATED_AT = "created_at";
    
	private ProgressDialog progressDialog;
	private LoginActivity loginActivity;
	private int id = -1;
	private InputStream resultContent = null;
	
	public LoginTask(LoginActivity loginActivity, ProgressDialog progressDialog)
	{
		this.loginActivity = loginActivity;
		this.progressDialog = progressDialog;
	}
	
	@Override
	protected void onPreExecute()
	{
		progressDialog.show();
	}
	
	@Override
	protected Integer doInBackground(String... arg0)
	{
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("email", arg0[0]));
        params.add(new BasicNameValuePair("password", arg0[1]));

		try {
            // defaultHttpClient
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(loginURL);
            httpPost.setEntity(new UrlEncodedFormEntity(params));
 
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
		
		return 1;
	}
	
	@Override
	protected void onPostExecute(Integer headerCode)
	{
		progressDialog.dismiss();
		
		try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
            		resultContent, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "n");
            }
            resultContent.close();
            json = sb.toString();
            Log.e("JSON", json);
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }
 
        // try parse the string to a JSON object
        try {
            jObj = new JSONObject(json);
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }
		
		// check for login response
        try {
            if (jObj.getString(KEY_SUCCESS) != null) {
            	loginActivity.loginErrorMsg.setText("");
                String res = jObj.getString(KEY_SUCCESS);
                if(Integer.parseInt(res) == 1){
                    // user successfully logged in
                    // Store user details in SQLite Database
                    DatabaseHandler db = new DatabaseHandler(loginActivity.getApplicationContext());
                    JSONObject json_user = jObj.getJSONObject("user");

                    // Clear all previous data in database
                    //userFunction.logoutUser(getApplicationContext());
                    db.addUser(json_user.getString(KEY_NAME), json_user.getString(KEY_EMAIL), jObj.getString(KEY_UID), json_user.getString(KEY_CREATED_AT));                       

                    // Launch Dashboard Screen
                    Intent dashboard = new Intent(loginActivity.getApplicationContext(), DashboardActivity.class);

                    // Close all views before launching Dashboard
                    dashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    loginActivity.startActivity(dashboard);

                    // Close Login Screen
                    loginActivity.finish();
                }else{
                    // Error in login
                	loginActivity.loginErrorMsg.setText("Incorrect username/password");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
	}
}
