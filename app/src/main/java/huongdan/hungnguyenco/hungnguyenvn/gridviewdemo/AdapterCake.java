package huongdan.hungnguyenco.hungnguyenvn.gridviewdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by Hung on 2/6/2017.
 */

public class AdapterCake extends BaseAdapter {

    Context context;
    ArrayList<Cake> cakeList;

    ImageView cakePhoto;
    TextView cakeName, cakeKind, cakePrice, cakeMoney;

    public AdapterCake(Context context, ArrayList<Cake> cakeList) {
        this.context = context;
        this.cakeList = cakeList;
    }

    @Override
    public int getCount() {
        return cakeList.size();
    }

    @Override
    public Object getItem(int i) {
        return cakeList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.menu_cake, viewGroup, false);
        v.setLayoutParams(new GridView.LayoutParams(-1, 450));
        cakePhoto = (ImageView)v.findViewById(R.id.cakePhoto);
        cakeName = (TextView)v.findViewById(R.id.cakeName);
        cakeKind = (TextView) v.findViewById(R.id.cakeKind);
        cakePrice = (TextView)v.findViewById(R.id.cakePrice);
        cakeMoney = (TextView)v.findViewById(R.id.cakeMoney);
        Cake cake = cakeList.get(i);
        cakeName.setText(cake.getName());
        cakeKind.setText(cake.getKind());
        cakePrice.setText(cake.getPrice());
        Glide.with(context).load(cake.getImage()).into(cakePhoto);
        return v;
    }

}
