package vrpro.vrpro.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import vrpro.vrpro.R;
import vrpro.vrpro.util.OnDataPass;

/**
 * Created by Plooer on 6/24/2017 AD.
 */

public class ListOrderAdapter extends BaseAdapter {
    private final String LOG_TAG = "ListOrderAdapter";

    private Context context;
    private String avatarName;

    public ListOrderAdapter (Context context, String avatarName) {
        this.context = context;
        this.avatarName = avatarName;
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public Object getItem(int position) {
        return this.avatarName;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        ViewHolder holder = null;
        if(convertView == null)
            convertView = layoutInflater.inflate(R.layout.order_row, null);
        holder = new ViewHolder();
        holder.txtQuatationDate = (TextView) convertView.findViewById(R.id.quatationDate);
        holder.txtQuatationNo = (TextView) convertView.findViewById(R.id.quatationNo);
        holder.txtProjectName = (TextView) convertView.findViewById(R.id.projectName);
        holder.txtCustomerName = (TextView) convertView.findViewById(R.id.customerName);
        holder.txtTotalPrice = (TextView) convertView.findViewById(R.id.totalPrice);

        holder.txtQuatationDate.setText("24/06/2017");
        holder.txtQuatationNo.setText("60#0-VR1043");
        holder.txtProjectName.setText("ม.คาซ่า ศิตี้ ดอนเมือง ศรีสมาน");
        holder.txtCustomerName.setText("จิราภัสร์ จิรเดชวิโรจน์");
        holder.txtTotalPrice.setText("10,000 บาท");

        return convertView;
    }

    private static class ViewHolder {
        TextView txtQuatationDate;
        TextView txtQuatationNo;
        TextView txtProjectName;
        TextView txtCustomerName;
        TextView txtTotalPrice;
    }


}
