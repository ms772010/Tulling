package com.example.administrator.tullingrobot;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;

import android.provider.MediaStore;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;


import com.turing.androidsdk.InitListener;
import com.turing.androidsdk.SDKInit;
import com.turing.androidsdk.SDKInitBuilder;
import com.turing.androidsdk.TuringApiManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import turing.os.http.core.ErrorMessage;
import turing.os.http.core.HttpConnectionListener;
import turing.os.http.core.RequestResult;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private List<ListData> lists;
    private ListView lv;
    private EditText sendText;
    private Button sendButton;
    private String context_str;
    private TextAdapter adapter;
    private ImageView ivHead;//头像显示
    private static String path="/sdcard/myHead/";//sd路径
    private Bitmap head;
    private SharedPreferences sharedPreferences;
    private Editor editor;
    private long time;

    private final String TAG = MainActivity.class.getSimpleName();
    private final String TURING_APIKEY = "eb25a2730cad4c85bfa1031bd7e617ea" ;
    private final String TURING_SECRET ="07469a7b2b93eddc";
    private final String UNIQUEID = "1313131314";
    private TuringApiManager mTuringApiManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    /*菜单选择*/
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_compose:
                Intent intent1 = new Intent(Intent.ACTION_PICK, null);
                intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent1, 1);

                return true;

            case R.id.action_delete:
                Intent intent =new Intent(MainActivity.this,history.class);
                startActivity(intent);
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }





    private void init(){

        SDKInitBuilder builder=new SDKInitBuilder(this)
                .setSecret(TURING_SECRET).setTuringKey(TURING_APIKEY).setUniqueId(UNIQUEID);
        SDKInit.init(builder,new InitListener(){
            @Override
            public void onFail(String error) {
                Log.d(TAG, error);
            }
            @Override
            public void onComplete() {
                // 获取userid成功后，才可以请求Turing服务器，需要请求必须在此回调成功，才可正确请求
                mTuringApiManager = new TuringApiManager(MainActivity.this);
                mTuringApiManager.setHttpListener(myHttpConnectionListener);

            }
        });



        sharedPreferences = getSharedPreferences("history", Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
        lv= (ListView) findViewById(R.id.lv);
        sendText= (EditText) findViewById(R.id.sendText);
        sendButton= (Button) findViewById(R.id.sendBtn);
        lists=new ArrayList<ListData>();
        sendButton.setOnClickListener(this);
        adapter=new TextAdapter(lists,this);
        lv.setAdapter(adapter);
        LayoutInflater flater = LayoutInflater.from(this);
        View view = flater.inflate(R.layout.left_item, null);
        ivHead = (ImageView) view.findViewById(R.id.iv);
        Bitmap bt = BitmapFactory.decodeFile(path + "head.jpg");//从Sd中找头像，转换成Bitmap
        if(bt!=null){
            @SuppressWarnings("deprecation")
            Drawable drawable = new BitmapDrawable(bt);//转换成drawable
            ivHead.setImageDrawable(drawable);
        }





    }


    HttpConnectionListener myHttpConnectionListener = new HttpConnectionListener() {

        @Override
        public void onSuccess(RequestResult result) {
            if (result != null) {
                try {
                    Log.d(TAG, result.getContent().toString());
                    JSONObject result_obj = new JSONObject(result.getContent()
                            .toString());
                    if (result_obj.has("text")) {
                        Log.d(TAG, result_obj.get("text").toString());
                        ListData listData=new ListData(result_obj.get("text").toString(),ListData.RECEIVER);
                        lists.add(listData);
                        time=System.currentTimeMillis();

                        editor.putString(String.valueOf(time),"机器人："+listData.getContent());
                        editor.commit();
                        adapter.notifyDataSetChanged();

                    }
                } catch (JSONException e) {
                    Log.d(TAG, "JSONException:" + e.getMessage());
                }
            }

        }

        @Override
        public void onError(ErrorMessage errorMessage) {
            Log.d(TAG, errorMessage.getMessage());
        }
    };






    @Override
    public void onClick(View v) {

        context_str=sendText.getText().toString();
        ListData listData=new ListData(context_str,ListData.SEND);
        lists.add(listData);
        time=System.currentTimeMillis();

        editor.putString(String.valueOf(time),"我："+listData.getContent());
        editor.commit();
        adapter.notifyDataSetChanged();
        mTuringApiManager.requestTuringAPI(context_str);
        sendText.setText("");

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    cropPhoto(data.getData());//裁剪图片
                }

                break;
            case 2:
                if (resultCode == RESULT_OK) {
                    File temp = new File(Environment.getExternalStorageDirectory()
                            + "/head.jpg");
                    cropPhoto(Uri.fromFile(temp));//裁剪图片
                }

                break;
            case 3:
                if (data != null) {
                    Bundle extras = data.getExtras();
                    head = extras.getParcelable("data");
                    if(head!=null){
                        /**
                         * 上传服务器代码
                         */
                        setPicToView(head);//保存在SD卡中
                        ivHead.setImageBitmap(head);//用ImageView显示出来
                    }
                }
                break;
            default:
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    };
    public void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 3);
    }
    private void setPicToView(Bitmap mBitmap) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            return;
        }
        FileOutputStream b = null;
        File file = new File(path);
        file.mkdirs();// 创建文件夹
        String fileName =path + "head.jpg";//图片名字
        try {
            b = new FileOutputStream(fileName);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                //关闭流
                b.flush();
                b.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}

