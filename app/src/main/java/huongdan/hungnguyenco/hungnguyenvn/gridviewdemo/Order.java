package huongdan.hungnguyenco.hungnguyenvn.gridviewdemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

public class Order extends AppCompatActivity {

    TextView cakeNameO;
    ImageView cakePhotoO;
    TextView cakeDes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        android.support.v7.app.ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        addControl();
        Bundle bd = getIntent().getExtras();
        if(bd != null){
            String name = bd.getString("name");
            String des = bd.getString("des");
            byte[] photo = bd.getByteArray("photo");
            ab.setTitle(bd.getString("name"));
            Bitmap bmPhotoO = BitmapFactory.decodeByteArray(photo, 0, photo.length);
            cakeDes.setText(des);
            cakePhotoO.setImageBitmap(bmPhotoO);
            cakeNameO.setText(name);
        }
    }

    private void addControl() {
        cakeDes = (TextView)findViewById(R.id.cakeDes);
        cakeNameO = (TextView)findViewById(R.id.cakeNameO);
        cakePhotoO = (ImageView)findViewById(R.id.cakePhotoO);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.item_order_bar, menu);
        Drawable yourdrawable = menu.getItem(0).getIcon(); // change 0 with 1,2 ...
        yourdrawable.mutate();
        yourdrawable.setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN);
        return true;
    }
}
