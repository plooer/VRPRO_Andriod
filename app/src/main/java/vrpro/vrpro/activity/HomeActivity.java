package vrpro.vrpro.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import vrpro.vrpro.R;
import vrpro.vrpro.adapter.ListOrderAdapter;
import vrpro.vrpro.util.SQLiteUtil;

public class HomeActivity extends AppCompatActivity {

    private final String LOG_TAG = "SetProfileActivity";
    private ListView listOrderListView;
    private Toolbar mActionBarToolbar;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mActionBarToolbar);
        getSupportActionBar().setTitle("VRPRO");

        sharedPref = this.getSharedPreferences(getString(R.string.preference_key), Context.MODE_PRIVATE);

        listOrderListView = (ListView) findViewById(R.id.listViewOrder);

        ListOrderAdapter listOrderAdapter = new ListOrderAdapter(this, "test");
        listOrderListView.setAdapter(listOrderAdapter);
        listOrderListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                gotoCreateOrderActivity(String.valueOf(position));
            }
        });

        Button gotoCreateOrderBtn = (Button) findViewById(R.id.gotoCreateOrder);
        gotoCreateOrderBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(LOG_TAG, "click to create new order");
                gotoCreateOrderActivity("CREATE NEW ORDER");
        }
        });

        SQLiteUtil sqlLite = new SQLiteUtil(this);
        sqlLite.getWritableDatabase();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.goto_set_profile_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.btnProfile:
                Log.i(LOG_TAG, "click to set profile");
                gotoSetProfileActicity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void gotoCreateOrderActivity(String position) {
        Log.i(LOG_TAG,"gotoCreateOrderActivity >>>. " + position );
        Intent myIntent = new Intent(HomeActivity.this, CreateOrderActivity.class);
        editor = sharedPref.edit();
        if(position.equals("CREATE NEW ORDER")){
//            myIntent.putExtra("quatationNo","CREATE NEW ORDER");
            editor.putString("quatationNo", "CREATE NEW ORDER");
        }else{
//            myIntent.putExtra("quatationNo","60#0-VR1043");
            editor.putString("quatationNo", "60#0-VR1043");
        }

        editor.commit();
//      data.putString("_id", topicModelList.get(position).get("_id").toString());
        startActivity(myIntent);
    }


    private void gotoSetProfileActicity() {
        Intent myIntent = new Intent(this, SetProfileActivity.class);
        this.startActivity(myIntent);
    }

}
