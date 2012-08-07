package test.push;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import com.google.android.gcm.GCMRegistrar;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //GCMRegistrar.checkDevice(this);
        //GCMRegistrar.checkManifest(this);
        final String regId = GCMRegistrar.getRegistrationId(this);
        Log.v("GCMTest", regId);
        if (regId.equals("")) {
          GCMRegistrar.register(this, "322895693348");
        } else {
          Log.v("GCMTest", "Already registered");
        }
        
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
