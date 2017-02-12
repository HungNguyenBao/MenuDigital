package layout;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import huongdan.hungnguyenco.hungnguyenvn.gridviewdemo.AdapterCake;
import huongdan.hungnguyenco.hungnguyenvn.gridviewdemo.Cake;
import huongdan.hungnguyenco.hungnguyenvn.gridviewdemo.Detail;
import huongdan.hungnguyenco.hungnguyenvn.gridviewdemo.R;
import huongdan.hungnguyenco.hungnguyenvn.gridviewdemo.addMenu;

/**
 * A simple {@link Fragment} subclass.
 */
public class Menu extends Fragment {

    ArrayList<Cake> arrayList;
    ArrayList<Cake> arrayListA;
    ArrayList<Cake> arrayListB;
    GridView gridView;
    DatabaseReference mData;
    AdapterCake adapter;


    public Menu() {
        // Required empty public constructor
    }

    static {
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        //Bundle bundle = getArguments();
        int pageSwipe = getArguments() != null ? getArguments().getInt("count") : 1;
        //int pageSwipe = bundle.getInt("count");
        gridView = (GridView)view.findViewById(R.id.menuCake);
        arrayList = new ArrayList<>();
        arrayListA = new ArrayList<>();
        arrayListB = new ArrayList<>();
        readData();

        switch (pageSwipe){
            case 0: {
                adapter = new AdapterCake(getActivity(), arrayList);
                gridView.setAdapter(adapter);
                break;
            }

            case 1: {

                adapter = new AdapterCake(getActivity(), arrayListA);
                gridView.setAdapter(adapter);

                break;
            }

            case 2:{
                adapter = new AdapterCake(getActivity(), arrayListB);
                gridView.setAdapter(adapter);
                break;
            }

        }

        registerForContextMenu(gridView);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cake cakes = arrayList.get(i);
                Intent intent = new Intent(getActivity(), Detail.class);
                intent.putExtra("name", cakes.getName());
                intent.putExtra("kind", cakes.getKind());
                intent.putExtra("photo", cakes.getImage());
                intent.putExtra("des", cakes.getDes());
                intent.putExtra("price", cakes.getPrice());
                startActivity(intent);
            }
        });
        return view;
    }



    private void readData() {
        mData = FirebaseDatabase.getInstance().getReference();
        mData.child("CAKE").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Cake cake = dataSnapshot.getValue(Cake.class);
                cake.setKey(dataSnapshot.getKey());
                Log.e("KEY", cake.getKey());
                setData(cake);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                String key = dataSnapshot.getKey();
                Cake newCake = dataSnapshot.getValue(Cake.class);
                for(Cake ck : arrayList){
                    if(ck.getKey().equals(key)){
                        ck.setNewMenu(newCake);
                        adapter.notifyDataSetChanged();
                        break;
                    }
                }
                for(Cake ck : arrayListA){
                    if(ck.getKey().equals(key)){
                        ck.setNewMenu(newCake);
                        if(Double.parseDouble(ck.getPrice()) < 50.0){
                            arrayListA.remove(ck);
                            arrayListB.add(ck);
                        }
                        adapter.notifyDataSetChanged();
                        break;
                    }
                }
                for(Cake ck : arrayListB){
                    if(ck.getKey().equals(key)){
                        ck.setNewMenu(newCake);
                        if(Double.parseDouble(ck.getPrice()) >= 50.0){
                            arrayListB.remove(ck);
                            arrayListA.add(ck);
                        }
                        adapter.notifyDataSetChanged();
                        break;
                    }
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String key = dataSnapshot.getKey();
                for (Cake ck : arrayList){
                    if(key.equals(ck.getKey())){
                        arrayList.remove(ck);
                        adapter.notifyDataSetChanged();
                        break;
                    }
                }
                for (Cake ck : arrayListA){
                    if (key.equals(ck.getKey())){
                        arrayListA.remove(ck);
                        adapter.notifyDataSetChanged();
                        break;
                    }
                }
                for (Cake ck : arrayListB){
                    if (key.equals(ck.getKey())){
                        arrayListB.remove(ck);
                        adapter.notifyDataSetChanged();
                        break;
                    }
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
    }

    private void setData(Cake cake)
    {
        arrayList.add(cake);
        if (Double.parseDouble(cake.getPrice()) > 50.0) {
            arrayListA.add(cake);
        }
        if (Double.parseDouble(cake.getPrice()) <= 50.0) {
            arrayListB.add(cake);
        }
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()){
            case R.id.itemDel:
                mData.child("CAKE").child(arrayList.get(info.position).getKey()).removeValue();
//                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
//                dialog.setCancelable(false);
//                dialog.setTitle("WARNING");
//                dialog.setMessage("Are you sure to delete?" );
//                dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int id) {
//                        //Action for "Delete".
//                        mData.child("CAKE").child(arrayList.get(info.position).getKey()).removeValue();
//                    }
//                });
//                dialog.setNegativeButton("Cancel ", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    dialog.dismiss();
//                    //Action for "Cancel".
//                }
//            });
//
//
//                final AlertDialog alert = dialog.create();
//                alert.show();
                break;

            case R.id.itemEdit:
                Cake cakes = arrayList.get(info.position);
                Intent intent = new Intent(getActivity(), addMenu.class);
                intent.putExtra("name", cakes.getName());
                intent.putExtra("kind", cakes.getKind());
                intent.putExtra("photo", cakes.getImage());
                intent.putExtra("des", cakes.getDes());
                intent.putExtra("price", cakes.getPrice());
                intent.putExtra("key", cakes.getKey());
                startActivity(intent);
                break;
        }
        return super.onContextItemSelected(item);
    }
}
