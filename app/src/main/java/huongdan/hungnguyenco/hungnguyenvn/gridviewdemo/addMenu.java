package huongdan.hungnguyenco.hungnguyenvn.gridviewdemo;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class addMenu extends AppCompatActivity {

    EditText edtName, edtKind, edtDes, edtPrice;
    String checkKey;
    ImageView imgPhoto;
    Button btnTakePhoTo, btnBrown, btnAdd, btnRemovePhoto;
    int REQUEST_CODE_IMAGE = 1;
    int SELECT_PHOTO = 2;
    DatabaseReference mData;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReferenceFromUrl("gs://bhnu-e0ffb.appspot.com");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_menu);
        mData = FirebaseDatabase.getInstance().getReference();
        addControl();
        removePhoto();
        takePhoto();
        brownPhoto();
        saveMenu();
        editNewMenu();
    }

    private void removePhoto() {
        btnRemovePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgPhoto.setImageBitmap(null);
            }
        });
    }

    private void editNewMenu() {
        Bundle bd = getIntent().getExtras();
        if(bd != null){
            checkKey = bd.getString("key");
            String name = bd.getString("name");
            String des = bd.getString("des");
            String photo = bd.getString("photo");
            String price = bd.getString("price");
            String kind = bd.getString("kind");
            edtKind.setText(kind);
            edtPrice.setText(price);
            edtDes.setText(des);
            edtName.setText(name);
            Glide.with(this).load(photo).into(imgPhoto);

        }
    }

    private void brownPhoto() {
        btnBrown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PHOTO);
            }
        });
    }

    private void saveMenu() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtName.getText().toString().isEmpty() || edtDes.getText().toString().isEmpty() || edtPrice.getText().toString().isEmpty() || edtKind.getText().toString().isEmpty() || imgPhoto.getDrawable() == null){
                    AlertDialog.Builder dialog = new AlertDialog.Builder(addMenu.this);
                    dialog.setCancelable(false);
                    dialog.setTitle("ERROR");
                    dialog.setMessage("You have to fill all in the blank!!!" );
                    dialog.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });
                    final AlertDialog alert = dialog.create();
                    alert.show();
                }else {
                    if(checkKey == null){
                        java.util.Calendar calendar = java.util.Calendar.getInstance();
                        StorageReference mountainsRef = storageRef.child("image" + calendar.getTimeInMillis() + ".png");
                        imgPhoto.setDrawingCacheEnabled(true);
                        imgPhoto.buildDrawingCache();
                        Bitmap bitmap = imgPhoto.getDrawingCache();
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                        byte[] data = baos.toByteArray();

                        UploadTask uploadTask = mountainsRef.putBytes(data);
                        uploadTask.addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Handle unsuccessful uploads
                            }
                        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                                Toast.makeText(addMenu.this, "success", Toast.LENGTH_SHORT).show();
                                Cake cake = new Cake(edtName.getText().toString(), edtKind.getText().toString(), edtPrice.getText().toString(), edtDes.getText().toString(), String.valueOf(downloadUrl));
                                mData.child("CAKE").push().setValue(cake);
                                edtName.setText("");
                                edtPrice.setText("");
                                edtKind.setText("");
                                edtDes.setText("");
                                imgPhoto.setImageBitmap(null);
                            }
                        });
                    }else {
                        java.util.Calendar calendar = java.util.Calendar.getInstance();
                        StorageReference mountainsRef = storageRef.child("image" + calendar.getTimeInMillis() + ".png");
                        imgPhoto.setDrawingCacheEnabled(true);
                        imgPhoto.buildDrawingCache();
                        Bitmap bitmap = imgPhoto.getDrawingCache();
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                        byte[] data = baos.toByteArray();

                        UploadTask uploadTask = mountainsRef.putBytes(data);
                        uploadTask.addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Handle unsuccessful uploads
                            }
                        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                                Toast.makeText(addMenu.this, "success", Toast.LENGTH_SHORT).show();
                                Cake cake = new Cake(edtName.getText().toString(), edtKind.getText().toString(), edtPrice.getText().toString(), edtDes.getText().toString(), String.valueOf(downloadUrl));
                                mData.child("CAKE").child(checkKey).setValue(cake);
                                edtName.setText("");
                                edtPrice.setText("");
                                edtKind.setText("");
                                edtDes.setText("");
                                imgPhoto.setImageBitmap(null);

                            }
                        });

                    }
                }

                startActivity(new Intent(addMenu.this, MainActivity.class));
            }
        });
    }

    private void takePhoto() {
        btnTakePhoTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQUEST_CODE_IMAGE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE_IMAGE && resultCode == RESULT_OK && data != null){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imgPhoto.setImageBitmap(bitmap);
        }else if(requestCode == SELECT_PHOTO && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            imgPhoto.setImageURI(uri);
        }
    }

    private void addControl() {
        btnRemovePhoto = (Button) findViewById(R.id.btnRemovePhoto);
        edtName = (EditText)findViewById(R.id.edtName);
        edtKind = (EditText)findViewById(R.id.edtKind);
        edtDes = (EditText)findViewById(R.id.edtDes);
        edtPrice = (EditText)findViewById(R.id.edtPrice);
        imgPhoto = (ImageView) findViewById(R.id.imgPhoto);
        btnTakePhoTo = (Button)findViewById(R.id.btnTakePhoto);
        btnBrown = (Button)findViewById(R.id.btnBrown);
        btnAdd = (Button)findViewById(R.id.btnAdd);

    }
}
