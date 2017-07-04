package vrpro.vrpro.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import vrpro.vrpro.Model.ProfileSaleModel;
import vrpro.vrpro.R;
import vrpro.vrpro.util.SQLiteUtil;

public class SetProfileActivity extends AppCompatActivity {

    private final String LOG_TAG = "SetProfileActivity";
    private EditText txtSaleName;
    private EditText txtPhoneNumber;
    private EditText txtSetQuatationNo;
    private EditText txtSetQuatationRunningNo;
    private SQLiteUtil sqlLite;
    private ProfileSaleModel profileSaleModel;
    private ProfileSaleModel profileSaleModelFromDB;
    Toolbar mActionBarToolbar;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(LOG_TAG,"SetProfileActivity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_profile);

        mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mActionBarToolbar);
        getSupportActionBar().setTitle("Profile");

        sharedPref = this.getSharedPreferences(getString(R.string.preference_key), Context.MODE_PRIVATE);

        txtSaleName = (EditText) findViewById(R.id.txtsaleName);
        txtPhoneNumber = (EditText) findViewById(R.id.txtphoneNumber);
        txtSetQuatationNo = (EditText) findViewById(R.id.txtSetQuatationNo);
        txtSetQuatationRunningNo = (EditText) findViewById(R.id.txtSetQuatationRunningNo);

        initialProfile();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.
                INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }

    private void initialProfile() {
        TextView txtvwUserName = (TextView) findViewById(R.id.viewUsername);
        TextView txtvwUserPhone = (TextView) findViewById(R.id.viewPhoneNumber);
        TextView txtvwQuatationNo = (TextView) findViewById(R.id.viewQuatationNo);
        TextView txtvwQuatationRunningNo = (TextView) findViewById(R.id.viewQuatationRunningNo);

        sqlLite = new SQLiteUtil(this);
        profileSaleModelFromDB = sqlLite.getProfileSale();
        if(profileSaleModelFromDB.getSaleName() != null){
            Log.i(LOG_TAG,"Ever set profile in DB");
            txtvwUserName.setText(profileSaleModelFromDB.getSaleName());
            txtvwUserPhone.setText(profileSaleModelFromDB.getSalePhone());
            txtvwQuatationNo.setText(profileSaleModelFromDB.getQuatationNo());
            txtvwQuatationRunningNo.setText(String.valueOf(profileSaleModelFromDB.getQuatationRunningNo()));
        }else{
            Log.i(LOG_TAG,"Not set profile in DB");
            txtvwUserName.setText("");
            txtvwUserPhone.setText("");
            txtvwQuatationNo.setText("");
            txtvwQuatationRunningNo.setText("");
        }
    }

    public void submit(View view) {
        if (isInputsEmpty()) {
            Toast.makeText(this, "Please enter your name, phone number and quataion.", Toast.LENGTH_SHORT).show();

        } else {
            Log.i(LOG_TAG, "sale name : " + txtSaleName.getText().toString() + " phone number : " + txtPhoneNumber.getText().toString()+ " quatation no : " + txtSetQuatationNo.getText().toString() + "running no : " + txtSetQuatationRunningNo.getText().toString());

            sqlLite = new SQLiteUtil(this);
            profileSaleModelFromDB = sqlLite.getProfileSale();
            Log.i(LOG_TAG,"ID: " + profileSaleModelFromDB.getID());
            if(profileSaleModelFromDB.getSaleName() != null){
                Log.i(LOG_TAG,"Update DB");
                updateProfileToDB();
            }else{
                Log.i(LOG_TAG,"Insert DB");
                insertProfileToDB();
            }
            editor = sharedPref.edit();
            editor.putString("quatationNoDefine", txtSetQuatationNo.getText().toString());
            editor.putInt("quatationRunningNoDefine", Integer.parseInt(txtSetQuatationRunningNo.getText().toString()));
            editor.commit();

            gotoHomeActicity();
        }
    }

    private void updateProfileToDB() {
        profileSaleModel = new ProfileSaleModel();
        profileSaleModel.setSaleName(txtSaleName.getText().toString());
        profileSaleModel.setSalePhone(txtPhoneNumber.getText().toString());
        profileSaleModel.setQuatationNo(txtSetQuatationNo.getText().toString());
        profileSaleModel.setQuatationRunningNo(Integer.parseInt(txtSetQuatationRunningNo.getText().toString()));
        profileSaleModel.setID(profileSaleModelFromDB.getID());
        sqlLite = new SQLiteUtil(this);
        sqlLite.updateProfileSale(profileSaleModel);
    }

    private void insertProfileToDB() {
        profileSaleModel = new ProfileSaleModel();
        profileSaleModel.setSaleName(txtSaleName.getText().toString());
        profileSaleModel.setSalePhone(txtPhoneNumber.getText().toString());
        profileSaleModel.setQuatationNo(txtSetQuatationNo.getText().toString());
        profileSaleModel.setQuatationRunningNo(Integer.parseInt(txtSetQuatationRunningNo.getText().toString()));
        sqlLite = new SQLiteUtil(this);
        sqlLite.setProfileSale(profileSaleModel);
    }

    private boolean isInputsEmpty() {
        return isEmpty(txtSaleName) || isEmpty(txtPhoneNumber) || isEmpty(txtSetQuatationNo) || isEmpty(txtSetQuatationRunningNo);
    }

    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }

    private void gotoHomeActicity() {
        Intent myIntent = new Intent(this, HomeActivity.class);
        myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(myIntent);
        finish();
    }

    private void hideKeyboard(){
        View view = this.getCurrentFocus();
        if (view != null) {
            Log.i(LOG_TAG, "view null hide keyboard");
            InputMethodManager imm = (InputMethodManager)this.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}
