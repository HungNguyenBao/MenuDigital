package huongdan.hungnguyenco.hungnguyenvn.gridviewdemo;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class Detail extends AppCompatActivity {

    TextView cakeNameD;
    TextView cakeKindD;
    ImageView cakePhotoD;
    TextView cakeDesD;
    TextView cakePriceD;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        addControl();
        android.support.v7.app.ActionBar ab = getSupportActionBar();
        Drawable backButton = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_keyboard_arrow_left_black_24dp, null);
        backButton.mutate().setColorFilter(Color.parseColor("#666666"), PorterDuff.Mode.SRC_ATOP);
        ab.setHomeAsUpIndicator(backButton);
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setElevation(0);
        ab.setTitle("");
        Bundle bd = getIntent().getExtras();
        if(bd != null){
            String name = bd.getString("name");
            String des = bd.getString("des");
            String photo = bd.getString("photo");
            String price = bd.getString("price");
            String kind = bd.getString("kind");
            cakeKindD.setText(kind);
            cakePriceD.setText("$" + price);
            cakeDesD.setText(des);
            cakeNameD.setText(name);
            Glide.with(this).load(photo).into(cakePhotoD);

        }

    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()){
//            case R.id.home:
//                startActivity(new Intent(this, MainActivity.class));
//                break;
//        }
//        return true;
//    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void addControl() {
        cakeNameD = (TextView)findViewById(R.id.cakeNameD);
        cakeKindD = (TextView)findViewById(R.id.cakeKindD);
        cakeDesD = (TextView)findViewById(R.id.cakeDesD);
        cakePriceD = (TextView)findViewById(R.id.cakePriceD);
        cakePhotoD = (ImageView) findViewById(R.id.cakePhotoD);
    }

}
