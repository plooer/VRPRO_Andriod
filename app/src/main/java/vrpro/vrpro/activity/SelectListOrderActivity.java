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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import vrpro.vrpro.Model.EachOrderModel;
import vrpro.vrpro.R;
import vrpro.vrpro.util.SQLiteUtil;

public class SelectListOrderActivity extends AppCompatActivity {

    private final String LOG_TAG = "SelectListOrderActivity";

    private Toolbar mActionBarToolbar;
    private String floor;
    private String position;
    private String DW;
    private String typeOfM;
    private String specialWord;
    private ArrayList<String>  specialReq;
    private Integer posFloor;
    private Integer posPosition;
    private Integer DWPosition;
    private Integer posTypeOfM;
    private Integer posSpecialWord;
    private Integer sizeOfspecialReq;
    private Double totalPrice;
    private String quatationNo;
    private String shared_quatationNo;
    EditText txtWidth;
    EditText txtHeight;
    private SQLiteUtil sqlLite;
    private SharedPreferences sharedPref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_list_order);

        mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mActionBarToolbar);
        getSupportActionBar().setTitle("รายการ");

        sharedPref = this.getSharedPreferences("vrpro.vrpro", Context.MODE_PRIVATE);
        shared_quatationNo = sharedPref.getString("quatationNo",null);
        Log.i(LOG_TAG,"shared_quatationNo : " + shared_quatationNo);

        txtWidth = (EditText) findViewById(R.id.txtWidthEachOrder);
        txtHeight = (EditText) findViewById(R.id.txtHeightEachOrder);

        // getIntent() is a method from the started activity
//        Intent myIntent = getIntent(); // gets the previously created intent
//        quatationNo = myIntent.getStringExtra("quatationNo");


        setFloorSpinner();
        setPositionSpinner();
        setDWSpinner();
        setTypeOfMSpinner();

        pressInsertOrder();
    }

    private void pressInsertOrder() {
        Button summaryPriceBtn = (Button) findViewById(R.id.addEachOrder);

        summaryPriceBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox checkbox = new CheckBox(SelectListOrderActivity.this);
                if(isInputsEmpty()){
                    Toast.makeText(SelectListOrderActivity.this, "Please select all condition", Toast.LENGTH_SHORT).show();
                }else{
                    Log.i(LOG_TAG, "Record Select List >>> ");
                    specialReq = new ArrayList<String>();
                    for(int i=0;i<sizeOfspecialReq;i++){
                        checkbox = (CheckBox) findViewById(i);
                        if(checkbox.isChecked()){
                            specialReq.add(String.valueOf(checkbox.getText()));
                            Log.i(LOG_TAG,">>>>>>> " + checkbox.getText());
                        }
                    }
                    insertEachOrderListToDB();
                    gotoCreateOrderActivity();
                    Log.i(LOG_TAG,"Floor : " + floor + " position : " + position + " DW : " + DW +" typeOfM : " + typeOfM + " specialWord : " + specialWord + " specialReq : " + specialReq);
                    Log.i(LOG_TAG, "width : " + txtWidth.getText().toString() + " height : " + txtHeight.getText().toString());
                }

            }
        });
    }

    @Override
    public void onStart() {
        try {
            super.onStart();
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage(), e);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.
                INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }



    private void insertEachOrderListToDB() {
        EachOrderModel eachOrderModel = new EachOrderModel();
        eachOrderModel.setQuatationNo(shared_quatationNo);
        eachOrderModel.setFloor(floor);
        eachOrderModel.setPosition(position);
        eachOrderModel.setDw(DW);
        eachOrderModel.setTypeOfM(typeOfM);
        eachOrderModel.setSpecialWord(specialWord);
        eachOrderModel.setSpecialReq(specialReq);
        eachOrderModel.setWidth(Double.parseDouble(txtWidth.getText().toString()));
        eachOrderModel.setHeight(Double.parseDouble(txtHeight.getText().toString()));
        eachOrderModel.setTotolPrice(0.0);
        sqlLite = new SQLiteUtil(SelectListOrderActivity.this);
        sqlLite.setEachOrderList(eachOrderModel);
    }


    private void setSelectedButtonSpecial(ArrayList<String> groupSpeacial) {
        sizeOfspecialReq = groupSpeacial.size();
        LinearLayout specialOrderLinear = (LinearLayout) findViewById(R.id.specialOrderLinear);
        specialOrderLinear.setOrientation(LinearLayout.VERTICAL);
        specialOrderLinear.removeAllViews();
        RadioGroup.LayoutParams params
                = new RadioGroup.LayoutParams(this, null);
        params.setMargins(0, 20, 0, 20);
        CheckBox specialCheckbox;
        int i=0;
        for(String temp : groupSpeacial){
            specialCheckbox = new CheckBox(this);
            specialCheckbox.setLayoutParams(params);
            specialCheckbox.setTextSize(20);
            specialCheckbox.setText(temp);
            specialCheckbox.setId(i);
            specialOrderLinear.addView(specialCheckbox);
            i++;
        }
    }

    private void setSpecialSpinnerCase(String[] specialItems) {
        Spinner specialDropdown = (Spinner)findViewById(R.id.spinnerSpecialCase);
        specialDropdown.setVisibility(View.VISIBLE);
        ArrayAdapter<String> specialAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, specialItems);
        specialDropdown.setAdapter(specialAdapter);
        specialDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                specialWord = String.valueOf(parent.getItemAtPosition(pos));
                posSpecialWord = parent.getSelectedItemPosition();
                Log.i(LOG_TAG,"Special Dropdown >>>>> position : " + posSpecialWord + " item : " + specialWord);

            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setTypeOfMSpinner() {
        Spinner typeOfMDropdown = (Spinner)findViewById(R.id.spinnerTypeOfM);
        String[] typeOfMItems = getResources().getStringArray(R.array.type_of_m_array);
//        String[] typeOfMItems = new String[]{"ประเภทมุ้ง","มุ้งกรอบเหล็กเปิด", "มุ้งกรอบเหล็กเลื่อน", "มุ้งประตูเปิด","มุ้งเลื่อน","มุ้งเปิด","มุ้ง Fix","มุ้งจีบพับเก็บ"};
        ArrayAdapter<String> typeOfMAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, typeOfMItems);
        typeOfMDropdown.setAdapter(typeOfMAdapter);
        typeOfMDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String[] specialItems;
                ArrayList<String> groupSpeacial = new ArrayList<String>();
                typeOfM = String.valueOf(parent.getItemAtPosition(pos));
                posTypeOfM = parent.getSelectedItemPosition();
                posSpecialWord = 99; //fix bug validate
                specialWord = "";
                Log.i(LOG_TAG,"TypeOfM >>>>> position : " + posTypeOfM + " item : " + typeOfM);
                if(typeOfM.equals("มุ้งกรอบเหล็กเปิด")){
                    specialItems = getResources().getStringArray(R.array.color_mung_array);
//                    specialItems = new String[]{"สี","สีขาว", "สีดำ", "สีน้ำตาล"};
                    setSpecialSpinnerCase(specialItems);
                    groupSpeacial.addAll(Arrays.asList(getResources().getStringArray(R.array.special_req_of_mung_krob_lek_perd_array)));
//                    groupSpeacial.add("กลับทาง");
//                    groupSpeacial.add("ขอสับ");
//                    groupSpeacial.add("Door Closer");
//                    groupSpeacial.add("อะคริลิคใส(0.65 ม.)");
//                    groupSpeacial.add("อะคริลิคใส(1.00 ม.)");
//                    groupSpeacial.add("อะคริลิคใส(เต็มบาน)");
//                    groupSpeacial.add("Door for Pets");
                }else if(typeOfM.equals("มุ้งกรอบเหล็กเลื่อน")){
                    specialItems = getResources().getStringArray(R.array.color_mung_array);
//                    specialItems = new String[]{"สี","สีขาว", "สีดำ", "สีน้ำตาล"};
                    setSpecialSpinnerCase(specialItems);
                    groupSpeacial.addAll(Arrays.asList(getResources().getStringArray(R.array.special_req_of_mung_krob_lek_leuan_array)));
//                    groupSpeacial.add("กลับทาง");
//                    groupSpeacial.add("มือจับฝัง");
//                    groupSpeacial.add("ขอสับ");
//                    groupSpeacial.add("อะคริลิคใส(0.65 ม.)");
//                    groupSpeacial.add("อะคริลิคใส(1.00 ม.)");
//                    groupSpeacial.add("อะคริลิคใส(เต็มบาน)");
//                    groupSpeacial.add("Door for Pets");
                }else if(typeOfM.equals("มุ้งประตูเปิด")){
                    setSpecialDropdownInvisible();
                    groupSpeacial.addAll(Arrays.asList(getResources().getStringArray(R.array.special_req_of_mung_pratoo_perd_array)));
//                    groupSpeacial.add("กลับทาง");
//                    groupSpeacial.add("เพิ่มกรอบ");
//                    groupSpeacial.add("ขอสับ");
//                    groupSpeacial.add("Door for Pets");
//                    groupSpeacial.add("Pets Screen(0.65 ม.)");
//                    groupSpeacial.add("Pets Screen(1.00 ม.)");
//                    groupSpeacial.add("Pets Screen(เต็มบาน)");
                }else if(typeOfM.equals("มุ้งเลื่อน")){
                    specialItems = getResources().getStringArray(R.array.special_mung_leuan_array);
//                    specialItems = new String[]{"รูปแบบพิเศษ","A", "S", "SA"};
                    setSpecialSpinnerCase(specialItems);
                    groupSpeacial.addAll(Arrays.asList(getResources().getStringArray(R.array.special_req_of_mung_leuan_array)));
//                    groupSpeacial.add("กลับทาง");
//                    groupSpeacial.add("เพิ่มราง");
//                    groupSpeacial.add("เพิ่มกรอบ");
//                    groupSpeacial.add("ขอสับ");
//                    groupSpeacial.add("Door for Pets");
//                    groupSpeacial.add("Pets Screen(0.65 ม.)");
//                    groupSpeacial.add("Pets Screen(1.00 ม.)");
//                    groupSpeacial.add("Pets Screen(เต็มบาน)");
                }else if(typeOfM.equals("มุ้งเปิด")){
                    setSpecialDropdownInvisible();
                    groupSpeacial.addAll(Arrays.asList(getResources().getStringArray(R.array.special_req_of_mung_perd_array)));
//                    groupSpeacial.add("กลับทาง");
//                    groupSpeacial.add("เพิ่มกรอบ");
//                    groupSpeacial.add("บานเกร็ด");
//                    groupSpeacial.add("ขอสับ");
//                    groupSpeacial.add("Door for Pets");
//                    groupSpeacial.add("Pets Screen(0.65 ม.)");
//                    groupSpeacial.add("Pets Screen(1.00 ม.)");
//                    groupSpeacial.add("Pets Screen(เต็มบาน)");
                }else if(typeOfM.equals("มุ้ง Fix")){
//                    specialItems = new String[]{"รูปแบบพิเศษ","ลูกบิด", "แม่เหล็ก"};
                    specialItems = getResources().getStringArray(R.array.special_mung_fix_array);
                    setSpecialSpinnerCase(specialItems);
                    groupSpeacial.addAll(Arrays.asList(getResources().getStringArray(R.array.special_req_of_mung_fix_array)));
//                    groupSpeacial.add("กลับทาง");
//                    groupSpeacial.add("เพิ่มกรอบ");
//                    groupSpeacial.add("บานเกร็ด");
//                    groupSpeacial.add("Door for Pets");
//                    groupSpeacial.add("Pets Screen(0.65 ม.)");
//                    groupSpeacial.add("Pets Screen(1.00 ม.)");
//                    groupSpeacial.add("Pets Screen(เต็มบาน)");
                }else if(typeOfM.equals("มุ้งจีบพับเก็บ")){
//                    specialItems = new String[]{"รูปแบบพิเศษ","รางเตี้ย", "เก็บราง"};
                    specialItems = getResources().getStringArray(R.array.special_mung_pub_array);
                    setSpecialSpinnerCase(specialItems);
                    groupSpeacial.addAll(Arrays.asList(getResources().getStringArray(R.array.special_req_of_mung_pub_array)));
//                    groupSpeacial.add("กลับทาง");
                }
                    setSelectedButtonSpecial(groupSpeacial);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setSpecialDropdownInvisible() {
        Spinner specialDropdown = (Spinner)findViewById(R.id.spinnerSpecialCase);
        specialDropdown.setVisibility(View.INVISIBLE);
    }

    private void setDWSpinner() {
        Spinner DWDropdown = (Spinner)findViewById(R.id.spinnerDW);
        String[] DWItems = getResources().getStringArray(R.array.dw_array);
//        String[] DWItems = new String[]{"ประเภท","ประตู","หน้าต่าง"};
        ArrayAdapter<String> DWAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, DWItems);
        DWDropdown.setAdapter(DWAdapter);
        DWDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                DW = String.valueOf(parent.getItemAtPosition(pos));
                DWPosition = parent.getSelectedItemPosition();
                Log.i(LOG_TAG,"Position >>>>> position : " + DWPosition + " item : " + DW);

            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }


    private void setPositionSpinner() {
        Spinner positionDropdown = (Spinner)findViewById(R.id.spinnerPosition);
        String[] positionItems = getResources().getStringArray(R.array.position_array);
//        String[] positionItems = new String[]{"ตำแหน่ง","รับแขก","นอนล่าง","โรงรถ","น้ำล่าง","ครัว","บันได","นอนใหญ่","น้ำนอนใหญ่","นอนหน้า","น้ำนอนหน้า","นอนหลังซ้าย","น้ำนอนหลังซ้าย","นอนหน้าซ้าย","น้ำนอนหน้าซ้าย","นอนหน้าขวา","น้ำนอนหน้าขวา","นอนหลังขวา","น้ำนอนหลังขวา","นอนกลาง","น้ำนอนกลาง","โถงกลาง","น้ำบน"};
        ArrayAdapter<String> postionAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, positionItems);
        positionDropdown.setAdapter(postionAdapter);
        positionDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                position = String.valueOf(parent.getItemAtPosition(pos));
                posPosition = parent.getSelectedItemPosition();
                Log.i(LOG_TAG,"Position >>>>> position : " + position + " item : " + posPosition);

            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setFloorSpinner() {
        Spinner floorDropdown = (Spinner)findViewById(R.id.spinnerFloor);

//        String[] floorItems = new String[]{"ชั้น","1", "2", "3","4","5"};
        String[] floorItems = getResources().getStringArray(R.array.floor_array);
        ArrayAdapter<String> floorAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, floorItems);
        floorDropdown.setAdapter(floorAdapter);
        floorDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                floor = String.valueOf(parent.getItemAtPosition(pos));
                posFloor = parent.getSelectedItemPosition();
                Log.i(LOG_TAG,"Floor >>>>> position : " + posFloor + " item : " + floor);

            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
    private boolean isInputsEmpty() {
        return posFloor == 0 || posPosition == 0 || DWPosition == 0 || posTypeOfM == 0 || posSpecialWord == 0 || isEmpty(txtWidth) || isEmpty(txtHeight);
    }

    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }

    private void gotoCreateOrderActivity() {
        Intent myIntent = new Intent(this, CreateOrderActivity.class);
        myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(myIntent);
        finish();
    }

}
