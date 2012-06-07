package library;

import java.io.InputStream;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import test.test3.DashboardActivity;
import test.test3.LoginActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;

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

        CommonFunctions commonFunctions = new CommonFunctions();       
        jObj = commonFunctions.parseJSON(commonFunctions.httpPost(loginURL, params));
		
		return 1;
	}
	
	@Override
	protected void onPostExecute(Integer headerCode)
	{
		progressDialog.dismiss();

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
                    UserFunctions userFunction = new UserFunctions();
                    userFunction.logoutUser(loginActivity.getApplicationContext());
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
