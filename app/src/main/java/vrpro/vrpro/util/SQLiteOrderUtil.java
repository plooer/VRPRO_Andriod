package vrpro.vrpro.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import vrpro.vrpro.Model.EachOrderModel;
import vrpro.vrpro.Model.OrderModel;
import vrpro.vrpro.Model.ProfileSaleModel;

/**
 * Created by Plooer on 6/27/2017 AD.
 */

public class SQLiteOrderUtil extends SQLiteOpenHelper {

    private final String LOG_TAG = "SQLiteUtil";
    private String DATABASE_NAME = "vrpro.db";
    private Integer DATABASE_VERSION = 1;
    private SQLiteDatabase sqLiteDatabase;

    private String ORDER_TABLE = "order";
    private String ORDER_ID = "ID";
    private String ORDER_QUATATION_DATE = "quatation_date";
    private String ORDER_QUATATION_NO = "quatation_no";
    private String ORDER_PROJECT_NAME = "project_name";
    private String ORDER_CUSTOMER_NAME = "customer_name";
    private String ORDER_CUSTOMER_ADDRESS = "customer_address";
    private String ORDER_CUSTOMER_PHONE = "customer_phone";
    private String ORDER_CUSTOMER_EMAIL = "customer_email";
    private String ORDER_TOTAL_PRICE = "total_price";
    private String ORDER_REMARKS = "remarks";

    public SQLiteOrderUtil(Context context) {
        super(context, "vrpro.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createProfileSaleTable(db);
    }

    private void createProfileSaleTable(SQLiteDatabase db) {
        String CREATE_TABLE_PROFILE_SALE = String.format("CREATE TABLE %s " +
                        "(%s INTEGER PRIMARY KEY  AUTOINCREMENT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT)",
                ORDER_TABLE,
                ORDER_ID,
                ORDER_QUATATION_NO,
                ORDER_QUATATION_DATE,
                ORDER_PROJECT_NAME,
                ORDER_CUSTOMER_NAME,
                ORDER_CUSTOMER_ADDRESS,
                ORDER_CUSTOMER_PHONE,
                ORDER_CUSTOMER_EMAIL,
                ORDER_TOTAL_PRICE,
                ORDER_REMARKS);

        db.execSQL(CREATE_TABLE_PROFILE_SALE);

        Log.i(LOG_TAG,"Create table profile sale complete");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_FRIEND_TABLE = "DROP TABLE IF EXISTS profile_sale";

        db.execSQL(DROP_FRIEND_TABLE);

        onCreate(db);
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
        values.put(ORDER_TOTAL_PRICE, orderModel.getTotalPricel());
        values.put(ORDER_REMARKS, orderModel.getRemarks());

        sqLiteDatabase.insert(ORDER_TABLE, null, values);

        sqLiteDatabase.close();
    }

    public List<OrderModel> getOrderList() {
        Log.i(LOG_TAG,"ggetOrderList");
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
            orderModel.setTotalPricel(cursor.getDouble(8));
            orderModel.setRemarks(cursor.getString(9));
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


//    public void updateProfileSale(ProfileSaleModel profileSaleModel) {
//        Log.i(LOG_TAG,"updateProfileSale >>>>> ID : " + profileSaleModel.getID());
//        sqLiteDatabase  = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
////        values.put(PROFILE_SALE_ID, profileSaleModeodel.getID());
//        values.put(PROFILE_SALE_NAME, profileSaleModel.getSaleName());
//        values.put(PROFILE_SALE_PHONE, profileSaleModel.getSalePhone());
//
//        int row = sqLiteDatabase.update(PROFILE_SALE_TABLE,
//                values,
//                PROFILE_SALE_ID + " = ? ",
//                new String[] { String.valueOf(profileSaleModel.getID()) });
//
//        sqLiteDatabase.close();
//    }
}
