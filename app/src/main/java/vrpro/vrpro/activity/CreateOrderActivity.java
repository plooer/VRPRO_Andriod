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
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import vrpro.vrpro.Model.EachOrderModel;
import vrpro.vrpro.R;
import vrpro.vrpro.adapter.ListEachOrderAdapter;
import vrpro.vrpro.util.SQLiteEachOrderListUtil;
import vrpro.vrpro.util.SQLiteUtil;

public class CreateOrderActivity extends AppCompatActivity {

    private final String LOG_TAG = "CreateOrderActivity";
    private Toolbar mActionBarToolbar;
    private ListView listEachOrderListView;
    private SQLiteUtil sqlLite;
    private String quatationNo;
    private String quatationDate;
    private String projectName;
    private String customerName;
    private String customerAdress;
    private String customerPhone;
    private String customerEmail;
    private Double totalPrice;
    private String remarks;

    private TextView txtQuatationNo;
    private TextView txtQuatationDate;
    private EditText txtProjectName;
    private EditText txtCustomerName;
    private EditText txtCustomerAdress;
    private EditText txtCustomerPhone;
    private EditText txtCustomerEmail;
    private EditText txtRemarks;

    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(LOG_TAG,"CreateOrderActivity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_order);

        mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mActionBarToolbar);
        getSupportActionBar().setTitle("ใบเสนอราคา");

        sharedPref = this.getSharedPreferences(getString(R.string.preference_key), Context.MODE_PRIVATE);

        txtQuatationNo = (TextView) findViewById(R.id.txtQuatationNo);
        txtQuatationDate = (TextView) findViewById(R.id.txtQuatationDate);
        txtProjectName = (EditText) findViewById(R.id.txtProjectName);
        txtCustomerName = (EditText) findViewById(R.id.txtCustomerName);
        txtCustomerAdress = (EditText) findViewById(R.id.txtCustomerAddress);
        txtCustomerPhone = (EditText) findViewById(R.id.txtCustomerPhone);
        txtCustomerEmail = (EditText) findViewById(R.id.txtCustomerEmail);
        txtRemarks = (EditText) findViewById(R.id.txtRemarks);




        // getIntent() is a method from the started activity
//        Intent myIntent = getIntent(); // gets the previously created intent
//        quatationNo = myIntent.getStringExtra("quatationNo");

        String shared_quatationNo = sharedPref.getString("quatationNo",null);
        Log.i(LOG_TAG,"quatationNo : " + shared_quatationNo);

        if(shared_quatationNo.equals("CREATE NEW ORDER")){
            Log.i(LOG_TAG,"Create New Order");
            txtQuatationNo.setText("xx#x-VRxxxx");
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String currentDate = sdf.format(new Date());
            txtQuatationDate.setText(currentDate);
            editor = sharedPref.edit();
            editor.putString("quatationNo", "60#0-VR1043");
            editor.commit();
        }else {
            Log.i(LOG_TAG, "Open Order Quatation No : " + shared_quatationNo);
            getDataToListView("60#0-VR1043");
        }

        Button summaryPriceBtn = (Button) findViewById(R.id.summaryPrice);
        summaryPriceBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(LOG_TAG, "summary price");
            }
        });

        Button saveOrder = (Button) findViewById(R.id.saveOrder);
        saveOrder.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isInputsEmpty()){
                    Toast.makeText(CreateOrderActivity.this, "กรอกข้อมูลให้ครบทุกช่อง", Toast.LENGTH_SHORT).show();
                }else{
                    Log.i(LOG_TAG,"SaveOrder");
                    quatationNo = txtQuatationNo.getText().toString();
                    quatationDate =  txtQuatationDate.getText().toString();
                    projectName = txtProjectName.getText().toString();
                    customerName = txtCustomerName.getText().toString();
                    customerAdress = txtCustomerAdress.getText().toString();
                    customerPhone = txtCustomerPhone.getText().toString();
                    customerEmail = txtCustomerEmail.getText().toString();
                    totalPrice = 0.0;
                    remarks = txtRemarks.getText().toString();
                    Log.i(LOG_TAG,"quatationNo : " + quatationNo);
                    Log.i(LOG_TAG,"quatationDate : " + quatationDate);
                    Log.i(LOG_TAG,"projectName : " + projectName);
                    Log.i(LOG_TAG,"customerName : " + customerName);
                    Log.i(LOG_TAG,"customerAdress : " + customerAdress);
                    Log.i(LOG_TAG,"customerPhone : " + customerPhone);
                    Log.i(LOG_TAG,"customerEmail : " + customerEmail);
                    Log.i(LOG_TAG,"totalPrice : " + totalPrice);
                    Log.i(LOG_TAG,"remarks : " + remarks);
                }


            }
        });

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.
                INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }

    private void getDataToListView(String quatationNo) {
        List<EachOrderModel> eachOrderModelList = new ArrayList<EachOrderModel>();
        sqlLite = new SQLiteUtil(this);

        eachOrderModelList  = sqlLite.getEachOrderList(quatationNo);

        listEachOrderListView = (ListView) findViewById(R.id.listViewEachOrder);

        ListEachOrderAdapter listEachOrderAdaper = new ListEachOrderAdapter(this, eachOrderModelList);
        listEachOrderListView.setAdapter(listEachOrderAdaper);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.create_each_order, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.btnCreateEachOrder:
                Log.i(LOG_TAG, "click to create each order");
                gotoSelectListActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private boolean isInputsEmpty() {
        return isEmpty(txtProjectName) || isEmpty(txtCustomerName) || isEmpty(txtCustomerAdress) || isEmpty(txtCustomerPhone) || isEmpty(txtCustomerEmail) || isEmpty(txtRemarks);
    }

    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }

    private void gotoSelectListActivity() {
        Intent myIntent = new Intent(this, SelectListOrderActivity.class);
        myIntent.putExtra("quatationNo",quatationNo);
        this.startActivity(myIntent);
    }


}
