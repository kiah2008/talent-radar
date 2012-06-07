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
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import test.test3.DashboardActivity;
import test.test3.LoginActivity;
import test.test3.RegisterActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

public class RegisterTask extends AsyncTask<String, Void, Integer> {

	private static String registerURL = "http://172.20.20.103/PHPServiceLoginTest/users/registerFromApp/";
	
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
	private RegisterActivity registerActivity;
	private int id = -1;
	
	public RegisterTask(RegisterActivity registerActivity, ProgressDialog progressDialog)
	{
		this.registerActivity = registerActivity;
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
		params.add(new BasicNameValuePair("name", arg0[0]));
        params.add(new BasicNameValuePair("email", arg0[1]));
        params.add(new BasicNameValuePair("password", arg0[2]));

        CommonFunctions commonFunctions = new CommonFunctions();
        jObj = commonFunctions.parseJSON(commonFunctions.httpPost(registerURL, params));
		
		return 1;
	}
	
	@Override
	protected void onPostExecute(Integer headerCode)
	{
		progressDialog.dismiss();
		
		// check for login response
        try {
            if (jObj.getString(KEY_SUCCESS) != null) {
            	registerActivity.registerErrorMsg.setText("");
                String res = jObj.getString(KEY_SUCCESS);
                if(Integer.parseInt(res) == 1){
                    // user successfully registred
                    // Store user details in SQLite Database
                    DatabaseHandler db = new DatabaseHandler(registerActivity.getApplicationContext());
                    JSONObject json_user = jObj.getJSONObject("user");

                    // Clear all previous data in database
                    UserFunctions userFunction = new UserFunctions();
                    userFunction.logoutUser(registerActivity.getApplicationContext());
                    db.addUser(json_user.getString(KEY_NAME), json_user.getString(KEY_EMAIL), jObj.getString(KEY_UID), json_user.getString(KEY_CREATED_AT));
                    // Launch Dashboard Screen
                    Intent dashboard = new Intent(registerActivity.getApplicationContext(), DashboardActivity.class);
                    // Close all views before launching Dashboard
                    dashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    registerActivity.startActivity(dashboard);
                    // Close Registration Screen
                    registerActivity.finish();
                }else{
                    // Error in registration
                	registerActivity.registerErrorMsg.setText("Error occured in registration");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
	}
}
