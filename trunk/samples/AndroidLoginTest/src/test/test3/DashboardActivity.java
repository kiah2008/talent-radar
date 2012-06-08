package test.test3;

import library.UserFunctions;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
 

public class DashboardActivity extends Activity {
    //UserFunctions userFunctions;
    Button btnLogout, btnViewMap;
    UserFunctions userFunctions;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * Dashboard Screen for the application
         * */
        // Check login status in database
        userFunctions = new UserFunctions();
        if(userFunctions.isUserLoggedIn(getApplicationContext())){
        // user already logged in show databoard
            setContentView(R.layout.dashboard);
            btnLogout = (Button) findViewById(R.id.btnLogout);
 
            btnLogout.setOnClickListener(new View.OnClickListener() {
 
                public void onClick(View arg0) {
                    // TODO Auto-generated method stub
                    userFunctions.logoutUser(getApplicationContext());
                    Intent login = new Intent(getApplicationContext(), LoginActivity.class);
                    login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(login);
                    // Closing dashboard screen
                    finish();
                }
            });
            
            btnViewMap = (Button) findViewById(R.id.btnViewMap);
            
            btnViewMap.setOnClickListener(new View.OnClickListener() {
 
                public void onClick(View arg0) {
                    // TODO Auto-generated method stub
                    Intent viewMap = new Intent(getApplicationContext(), MapActivity.class);
                    viewMap.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(viewMap);
                    // Closing dashboard screen
                    finish();
                }
            });
 
        }else{
            // user is not logged in show login screen
            Intent login = new Intent(getApplicationContext(), LoginActivity.class);
            login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(login);
            // Closing dashboard screen
            finish();
        }
    }
}