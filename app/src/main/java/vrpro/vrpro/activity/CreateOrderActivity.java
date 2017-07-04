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
import vrpro.vrpro.Model.OrderModel;
import vrpro.vrpro.Model.ProfileSaleModel;
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
    private String remarks;
    private String discount;
    private Double totalPrice;

    private TextView txtQuatationNo;
    private TextView txtQuatationDate;
    private EditText txtProjectName;
    private EditText txtCustomerName;
    private EditText txtCustomerAdress;
    private EditText txtCustomerPhone;
    private EditText txtCustomerEmail;
    private EditText txtRemarks;
    private EditText txtDiscount;
    private TextView txtTotalPrice;

    private OrderModel orderModel;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;
    private String shared_quatationNo;
    private String shared_quatationNoDefine;
    private Integer shared_quatationRunningNoDefine;
    private String shared_everCreareOrder;
    private Integer runningQuataionNo;
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
        txtDiscount = (EditText) findViewById(R.id.txtDiscount);
        txtTotalPrice = (TextView) findViewById(R.id.txtTotalPrice);

        shared_quatationNo = sharedPref.getString("quatationNo",null);
        shared_quatationNoDefine = sharedPref.getString("quatationNoDefine",null);
        shared_quatationRunningNoDefine = sharedPref.getInt("quatationRunningNoDefine",0);
        shared_everCreareOrder = sharedPref.getString("everCreateOrder",null);



        initialData();

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
                    sqlLite = new SQLiteUtil(CreateOrderActivity.this);
                    orderModel = new OrderModel();
                    orderModel = sqlLite.getOrderByQuatationNo(shared_quatationNo);
                    if(orderModel.getQuatationNo() != null){
                        Log.i(LOG_TAG,"Update Order");
                    }else{
                        Log.i(LOG_TAG,"Save Order");
                        saveOrderToDB();
                        setQuatationNoDefineToSharedPref();
                        setEverCreateOrderToSharedPref();
                    }

                }
            }
        });

    }

    private void initialData(){

        Log.i(LOG_TAG,"shared_quatationNo : " + shared_quatationNo);
        Log.i(LOG_TAG,"shared_quatationNoDefine : " + shared_quatationNoDefine);
        Log.i(LOG_TAG,"shared_quatationRunningNoDefine : " + shared_quatationRunningNoDefine);
        Log.i(LOG_TAG,"shared_everCreateOrder : " + shared_everCreareOrder);
        if(shared_quatationNo.equals("CREATE NEW ORDER")){
            Log.i(LOG_TAG,"Create New Order");
            sqlLite = new SQLiteUtil(this);
            ProfileSaleModel profileSaleModelFromDB = sqlLite.getProfileSale();
            Log.i(LOG_TAG,">> getQuatationRunningNo" + profileSaleModelFromDB.getQuatationRunningNo());
            if(shared_everCreareOrder == null){
                Log.i(LOG_TAG,"First Order Of Sale");
                runningQuataionNo = shared_quatationRunningNoDefine;
                txtQuatationNo.setText(shared_quatationNoDefine+shared_quatationRunningNoDefine);
            }else {
                Log.i(LOG_TAG, "Not First Order Of Sale");
//                int runningQuatationNo = Integer.parseInt(shared_quatationNoDefine.substring(shared_quatationNoDefine.length() - 4, shared_quatationNoDefine.length()));
//                runningQuatationNo += 1;
//                String showQautationNo = shared_quatationNoDefine.substring(0, shared_quatationNoDefine.length() - 4) + String.valueOf(runningQuatationNo);
//                Log.i(LOG_TAG, "showQautationNo : " + showQautationNo);
//                txtQuatationNo.setText(showQautationNo);
                runningQuataionNo = shared_quatationRunningNoDefine + 1;
                String showQautationNo = shared_quatationNoDefine + String.valueOf(runningQuataionNo);

                Log.i(LOG_TAG, "showQautationNo : " + showQautationNo);
                txtQuatationNo.setText(showQautationNo);
            }
//            }

//            int runningQuatationNo = shared_quatationRunningNoDefine + 1;
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String currentDate = sdf.format(new Date());
            txtQuatationDate.setText(currentDate);
            setQuatationNoToSharedPref();

        }else {
            Log.i(LOG_TAG, "Open Order Quatation No : " + shared_quatationNo);
            sqlLite = new SQLiteUtil(CreateOrderActivity.this);
            orderModel = new OrderModel();
            orderModel = sqlLite.getOrderByQuatationNo(shared_quatationNo);

            txtQuatationNo.setText(orderModel.getQuatationNo());
            txtQuatationDate.setText(orderModel.getQuatationDate());
            txtProjectName.setText(orderModel.getProjectName());
            txtCustomerName.setText(orderModel.getCustomerName());
            txtCustomerAdress.setText(orderModel.getCustomerAdress());
            txtCustomerPhone.setText(orderModel.getCustomerPhone());
            txtCustomerEmail.setText(orderModel.getCustomerEmail());
            txtRemarks.setText(orderModel.getRemarks());
            txtDiscount.setText(String.valueOf(orderModel.getDiscount()));
            txtTotalPrice.setText(String.valueOf(orderModel.getTotalPrice()));
            getDataToListView(shared_quatationNo);
        }
    }

    private void setQuatationNoToSharedPref() {
        editor = sharedPref.edit();
        editor.putString("quatationNo", txtQuatationNo.getText().toString());
        editor.commit();
    }

    private void setQuatationNoDefineToSharedPref() {
        editor = sharedPref.edit();
        editor.putString("quatationNoDefine", shared_quatationNoDefine);
        editor.putInt("quatationRunningNoDefine",runningQuataionNo);
        editor.commit();
    }

    private void setEverCreateOrderToSharedPref() {
        editor = sharedPref.edit();
        editor.putString("everCreateOrder", "Not First Order Of Sale");
        editor.commit();
    }

    private void saveOrderToDB() {
        quatationNo = txtQuatationNo.getText().toString();
        quatationDate =  txtQuatationDate.getText().toString();
        projectName = txtProjectName.getText().toString();
        customerName = txtCustomerName.getText().toString();
        customerAdress = txtCustomerAdress.getText().toString();
        customerPhone = txtCustomerPhone.getText().toString();
        customerEmail = txtCustomerEmail.getText().toString();

        remarks = txtRemarks.getText().toString();
        discount = txtDiscount.getText().toString();
        totalPrice = Double.parseDouble(txtTotalPrice.getText().toString());

        Log.i(LOG_TAG,"quatationNo : " + quatationNo);
        Log.i(LOG_TAG,"quatationDate : " + quatationDate);
        Log.i(LOG_TAG,"projectName : " + projectName);
        Log.i(LOG_TAG,"customerName : " + customerName);
        Log.i(LOG_TAG,"customerAdress : " + customerAdress);
        Log.i(LOG_TAG,"customerPhone : " + customerPhone);
        Log.i(LOG_TAG,"customerEmail : " + customerEmail);
        Log.i(LOG_TAG,"remarks : " + remarks);
        Log.i(LOG_TAG,"discount : " + discount);
        Log.i(LOG_TAG,"totalPrice : " + totalPrice);

        orderModel = new OrderModel();
        orderModel.setQuatationNo(quatationNo);
        orderModel.setQuatationDate(quatationDate);
        orderModel.setProjectName(projectName);
        orderModel.setCustomerName(customerName);
        orderModel.setCustomerAdress(customerAdress);
        orderModel.setCustomerPhone(customerPhone);
        orderModel.setCustomerEmail(customerEmail);
        orderModel.setRemarks(remarks);
        orderModel.setDiscount(discount);
        orderModel.setTotalPrice(totalPrice);

        sqlLite = new SQLiteUtil(CreateOrderActivity.this);
        sqlLite.setOrder(orderModel);
        gotoHomeActivity();
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

    private void gotoHomeActivity() {
        Intent myIntent = new Intent(this, HomeActivity.class);
        myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(myIntent);
        finish();
    }
}
