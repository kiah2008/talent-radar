package library;

import android.content.Context;
 
public class UserFunctions {
 
    // constructor
    public UserFunctions(){
    }
 
    /**
     * function make Login Request
     * @param email
     * @param password
     * */
    /*public JSONObject loginUser(String email, String password){
        // Building Parameters
    	ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("email", email));
        params.add(new BasicNameValuePair("password", password));
        params.add(new BasicNameValuePair("url", loginURL));
        JSONObject json = jsonParser.getJSONFromUrl(params);
        // return json
        // Log.e("JSON", json.toString());
        return json;
    }*/

 
    /**
     * Function get Login status
     * */
    public boolean isUserLoggedIn(Context context){
        DatabaseHandler db = new DatabaseHandler(context);
        int count = db.getRowCount();
        if(count > 0){
            // user logged in
            return true;
        }
        return false;
    }
 
    /**
     * Function to logout user
     * Reset Database
     * */
    public boolean logoutUser(Context context){
        DatabaseHandler db = new DatabaseHandler(context);
        db.resetTables();
        return true;
    }
 
}