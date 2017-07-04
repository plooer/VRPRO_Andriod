package vrpro.vrpro.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.SimpleTimeZone;

import vrpro.vrpro.Model.EachOrderModel;
import vrpro.vrpro.Model.OrderModel;
import vrpro.vrpro.Model.ProfileSaleModel;

/**
 * Created by Plooer on 6/27/2017 AD.
 */

public class SQLiteUtil extends SQLiteOpenHelper {

    private final String LOG_TAG = "SQLiteUtil";
    private static final String DATABASE_NAME = "vrpro.db";
    private static final Integer DATABASE_VERSION = 1;
    private SQLiteDatabase sqLiteDatabase;

    private String PROFILE_SALE_TABLE = "profile_sale_table";
    private String PROFILE_SALE_ID = "ID";
    private String PROFILE_SALE_NAME = "sale_name";
    private String PROFILE_SALE_PHONE = "sale_phone";
    private String PROFILE_QUATATION_NO = "quatation_no";
    private String PROFILE_QUATATION_RUNNING_NO = "quatation_running_no";

    private String ORDER_TABLE = "order_table";
    private String ORDER_ID = "ID";
    private String ORDER_QUATATION_NO = "quatation_no";
    private String ORDER_QUATATION_DATE = "quatation_date";
    private String ORDER_PROJECT_NAME = "project_name";
    private String ORDER_CUSTOMER_NAME = "customer_name";
    private String ORDER_CUSTOMER_ADDRESS = "customer_address";
    private String ORDER_CUSTOMER_PHONE = "customer_phone";
    private String ORDER_CUSTOMER_EMAIL = "customer_email";
    private String ORDER_REMARKS = "remarks";
    private String ORDER_DISCOUNT = "discount";
    private String ORDER_TOTAL_PRICE = "total_price";


    private String EACH_ORDER_TABLE = "each_order_list_table";
    private String EACH_ORDER_ID = "ID";
    private String EACH_ORDER_QUATATION_NO = "quatation_no";
    private String EACH_ORDER_FLOOR = "floor";
    private String EACH_ORDER_POSITION = "position";
    private String EACH_ORDER_DW = "dw";
    private String EACH_ORDER_TYPE_OF_M = "type_of_m";
    private String EACH_ORDER_SPECIAL_WORD = "special_word";
    private String EACH_ORDER_SPECIAL_REQ = "special_req";
    private String EACH_ORDER_WIDTH = "width";
    private String EACH_ORDER_HEIGHT = "height";
    private String EACH_ORDER_TOTAL_PRICE = "total_price";

    public SQLiteUtil(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createProfileSaleTable(db);
    }

    private void createProfileSaleTable(SQLiteDatabase db) {
        createTableProfileSale(db);
        createTableOrder(db);
        createTableEachOrderList(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + PROFILE_SALE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + ORDER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + EACH_ORDER_TABLE);
        onCreate(db);
    }

    private void createTableEachOrderList(SQLiteDatabase db) {
        String CREATE_TABLE_EACH_ORDER_LIST = String.format("CREATE TABLE %s " +
                        "(%s INTEGER PRIMARY KEY  AUTOINCREMENT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT)",
                EACH_ORDER_TABLE,
                EACH_ORDER_ID,
                EACH_ORDER_QUATATION_NO,
                EACH_ORDER_FLOOR,
                EACH_ORDER_POSITION,
                EACH_ORDER_DW,
                EACH_ORDER_TYPE_OF_M,
                EACH_ORDER_SPECIAL_WORD,
                EACH_ORDER_SPECIAL_REQ,
                EACH_ORDER_WIDTH,
                EACH_ORDER_HEIGHT,
                EACH_ORDER_TOTAL_PRICE);

        db.execSQL(CREATE_TABLE_EACH_ORDER_LIST);

        Log.i(LOG_TAG,"Create table each order list complete");
    }

    private void createTableOrder(SQLiteDatabase db) {
        String CREATE_ORDER = String.format("CREATE TABLE %s " +
                        "(%s INTEGER PRIMARY KEY  AUTOINCREMENT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT)",
                ORDER_TABLE,
                ORDER_ID,
                ORDER_QUATATION_NO,
                ORDER_QUATATION_DATE,
                ORDER_PROJECT_NAME,
                ORDER_CUSTOMER_NAME,
                ORDER_CUSTOMER_ADDRESS,
                ORDER_CUSTOMER_PHONE,
                ORDER_CUSTOMER_EMAIL,
                ORDER_REMARKS,
                ORDER_DISCOUNT,
                ORDER_TOTAL_PRICE);

        db.execSQL(CREATE_ORDER);

        Log.i(LOG_TAG,"Create table order complete");
    }

    private String createTableProfileSale(SQLiteDatabase db) {
        String CREATE_TABLE_PROFILE_SALE = String.format("CREATE TABLE %s " +
                        "(%s INTEGER PRIMARY KEY  AUTOINCREMENT, %s TEXT, %s TEXT, %s TEXT, %s INTEGER)",
                PROFILE_SALE_TABLE,
                PROFILE_SALE_ID,
                PROFILE_SALE_NAME,
                PROFILE_SALE_PHONE,
                PROFILE_QUATATION_NO,
                PROFILE_QUATATION_RUNNING_NO);

        db.execSQL(CREATE_TABLE_PROFILE_SALE);

        Log.i(LOG_TAG,"Create table profile sale complete");
        return CREATE_TABLE_PROFILE_SALE;
    }


    public void setProfileSale(ProfileSaleModel profileSaleModel) {
        Log.i(LOG_TAG,"setProfileSale");
        sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PROFILE_SALE_NAME, profileSaleModel.getSaleName());
        values.put(PROFILE_SALE_PHONE, profileSaleModel.getSalePhone());
        values.put(PROFILE_QUATATION_NO, profileSaleModel.getQuatationNo());
        values.put(PROFILE_QUATATION_RUNNING_NO, profileSaleModel.getQuatationRunningNo());

        sqLiteDatabase.insert(PROFILE_SALE_TABLE, null, values);

        sqLiteDatabase.close();
    }

    public ProfileSaleModel getProfileSale() {
        Log.i(LOG_TAG,"getProfileSale");
        ProfileSaleModel profileSaleModel = new ProfileSaleModel();

        sqLiteDatabase = this.getWritableDatabase();

        Cursor cursor = sqLiteDatabase.query
                (PROFILE_SALE_TABLE, null, null, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        while(!cursor.isAfterLast()) {
            profileSaleModel.setID(cursor.getInt(0));
            profileSaleModel.setSaleName(cursor.getString(1));
            profileSaleModel.setSalePhone(cursor.getString(2));
            profileSaleModel.setQuatationNo(cursor.getString(3));
            profileSaleModel.setQuatationRunningNo(cursor.getInt(4));
            cursor.moveToNext();
        }
        sqLiteDatabase.close();

        return profileSaleModel;
    }

    public void updateProfileSale(ProfileSaleModel profileSaleModel) {
        Log.i(LOG_TAG,"updateProfileSale >>>>> ID : " + profileSaleModel.getID());
        sqLiteDatabase  = this.getWritableDatabase();

        ContentValues values = new ContentValues();
//        values.put(PROFILE_SALE_ID, profileSaleModeodel.getID());
        values.put(PROFILE_SALE_NAME, profileSaleModel.getSaleName());
        values.put(PROFILE_SALE_PHONE, profileSaleModel.getSalePhone());
        values.put(PROFILE_QUATATION_NO, profileSaleModel.getQuatationNo());
        values.put(PROFILE_QUATATION_RUNNING_NO, profileSaleModel.getQuatationRunningNo());

        int row = sqLiteDatabase.update(PROFILE_SALE_TABLE,
                values,
                PROFILE_SALE_ID + " = ? ",
                new String[] { String.valueOf(profileSaleModel.getID()) });

        sqLiteDatabase.close();
    }

    public void setOrder(OrderModel orderModel) {
        Log.i(LOG_TAG,"setOrder");
        sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ORDER_QUATATION_NO, orderModel.getQuatationNo());
        values.put(ORDER_QUATATION_DATE, orderModel.getQuatationDate());
        values.put(ORDER_PROJECT_NAME, orderModel.getProjectName());
        values.put(ORDER_CUSTOMER_NAME, orderModel.getCustomerName());
        values.put(ORDER_CUSTOMER_ADDRESS, orderModel.getCustomerAdress());
        values.put(ORDER_CUSTOMER_PHONE, orderModel.getCustomerPhone());
        values.put(ORDER_CUSTOMER_EMAIL, orderModel.getCustomerEmail());
        values.put(ORDER_REMARKS, orderModel.getRemarks());
        values.put(ORDER_DISCOUNT, orderModel.getDiscount());
        values.put(ORDER_TOTAL_PRICE, orderModel.getTotalPrice());


        sqLiteDatabase.insert(ORDER_TABLE, null, values);

        sqLiteDatabase.close();
        Log.i(LOG_TAG,"insert order complete");
    }

    public List<OrderModel> getOrderList() {
        Log.i(LOG_TAG,"getOrderList");
        List<OrderModel> orderModelList = new ArrayList<OrderModel>();
        OrderModel orderModel;

        sqLiteDatabase = this.getWritableDatabase();

        Cursor cursor = sqLiteDatabase.query
                (ORDER_TABLE, null, null, null, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        while(!cursor.isAfterLast()) {
            orderModel = new OrderModel();
            orderModel.setID(cursor.getInt(0));
            orderModel.setQuatationNo(cursor.getString(1));
            orderModel.setQuatationDate(cursor.getString(2));
            orderModel.setProjectName(cursor.getString(3));
            orderModel.setCustomerName(cursor.getString(4));
            orderModel.setCustomerAdress(cursor.getString(5));
            orderModel.setCustomerPhone(cursor.getString(6));
            orderModel.setCustomerEmail(cursor.getString(7));
            orderModel.setRemarks(cursor.getString(8));
            orderModel.setDiscount(cursor.getString(9));
            orderModel.setTotalPrice(cursor.getDouble(10));

            orderModelList.add(orderModel);
            cursor.moveToNext();
        }
        sqLiteDatabase.close();

        Log.i(LOG_TAG,"------------------------------");
        for(OrderModel model : orderModelList) {
            Log.i(LOG_TAG,"id : " + model.getID());
            Log.i(LOG_TAG,"floor : " + model.getQuatationNo());
        }
        Log.i(LOG_TAG,"------------------------------");
        Log.i(LOG_TAG,"size of list : "+orderModelList.size());


        return orderModelList;
    }

    public OrderModel getOrderByQuatationNo(String quatationNo) {
        Log.i(LOG_TAG,"getOrderByQuatationNo >>>> quatationNo : " + quatationNo);
        OrderModel orderModel = new OrderModel();

        sqLiteDatabase = this.getWritableDatabase();

        String selection = ORDER_QUATATION_NO+ " = ?"; // MISSING in your update!!
        String[] selectionArgs = new String[] { quatationNo };


        Cursor cursor = sqLiteDatabase.query
                (ORDER_TABLE, null, selection, selectionArgs, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        while(!cursor.isAfterLast()) {
            orderModel.setID(cursor.getInt(0));
            orderModel.setQuatationNo(cursor.getString(1));
            orderModel.setQuatationDate(cursor.getString(2));
            orderModel.setProjectName(cursor.getString(3));
            orderModel.setCustomerName(cursor.getString(4));
            orderModel.setCustomerAdress(cursor.getString(5));
            orderModel.setCustomerPhone(cursor.getString(6));
            orderModel.setCustomerEmail(cursor.getString(7));
            orderModel.setRemarks(cursor.getString(8));
            orderModel.setDiscount((cursor.getString(9)));
            orderModel.setTotalPrice(cursor.getDouble(10));
            cursor.moveToNext();
        }
        sqLiteDatabase.close();

        Log.i(LOG_TAG,"------------------------------");
        Log.i(LOG_TAG,"id : " + orderModel.getID());
        Log.i(LOG_TAG,"quatation no : " + orderModel.getQuatationNo());
        Log.i(LOG_TAG,"customer name : " + orderModel.getCustomerName());
        Log.i(LOG_TAG,"------------------------------");

        return orderModel;
    }

    public void setEachOrderList(EachOrderModel eachOrderModel) {
        Log.i(LOG_TAG,"setEachOrderList");
        sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(EACH_ORDER_QUATATION_NO, eachOrderModel.getQuatationNo());
        values.put(EACH_ORDER_FLOOR, eachOrderModel.getFloor());
        values.put(EACH_ORDER_POSITION, eachOrderModel.getPosition());
        values.put(EACH_ORDER_DW, eachOrderModel.getDw());
        values.put(EACH_ORDER_TYPE_OF_M, eachOrderModel.getTypeOfM());
        values.put(EACH_ORDER_SPECIAL_WORD, eachOrderModel.getSpecialWord());
        values.put(EACH_ORDER_SPECIAL_REQ, eachOrderModel.getSpecialReq().toString());
        values.put(EACH_ORDER_WIDTH, eachOrderModel.getWidth());
        values.put(EACH_ORDER_HEIGHT, eachOrderModel.getHeight());
        values.put(EACH_ORDER_TOTAL_PRICE,eachOrderModel.getTotolPrice());


        sqLiteDatabase.insert(EACH_ORDER_TABLE, null, values);

        sqLiteDatabase.close();
    }

    public List<EachOrderModel> getEachOrderList(String quatationNo) {
        Log.i(LOG_TAG,"getEachOrderList >>> quatationNo : " + quatationNo);
        List<EachOrderModel> eachOrderModelList = new ArrayList<EachOrderModel>();
        EachOrderModel eachOrderModel;

        sqLiteDatabase = this.getWritableDatabase();

        String selection = EACH_ORDER_QUATATION_NO+ " = ?"; // MISSING in your update!!
        String[] selectionArgs = new String[] { quatationNo };

        Cursor cursor = sqLiteDatabase.query
                (EACH_ORDER_TABLE, null, selection, selectionArgs, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        while(!cursor.isAfterLast()) {
            eachOrderModel = new EachOrderModel();
            eachOrderModel.setID(cursor.getInt(0));
            eachOrderModel.setQuatationNo(cursor.getString(1));
            eachOrderModel.setFloor(cursor.getString(2));
            eachOrderModel.setPosition(cursor.getString(3));
            eachOrderModel.setDw(cursor.getString(4));
            eachOrderModel.setTypeOfM(cursor.getString(5));
            eachOrderModel.setSpecialWord(cursor.getString(6));
            eachOrderModel.setSpecialReq(new ArrayList<String>(Arrays.asList(cursor.getString(7).split(","))));
            eachOrderModel.setWidth(cursor.getDouble(8));
            eachOrderModel.setHeight(cursor.getDouble(9));
            eachOrderModel.setTotolPrice(cursor.getDouble(10));
            eachOrderModelList.add(eachOrderModel);
            cursor.moveToNext();
        }
        sqLiteDatabase.close();

//        Log.i(LOG_TAG,"------------------------------");
//        for(EachOrderModel model : eachOrderModelList) {
//            Log.i(LOG_TAG,"id : " + model.getID());
//            Log.i(LOG_TAG,"floor : " + model.getFloor());
//        }
//        Log.i(LOG_TAG,"------------------------------");
//        Log.i(LOG_TAG,"size of list : "+eachOrderModelList.size());


        return eachOrderModelList;
    }


}
