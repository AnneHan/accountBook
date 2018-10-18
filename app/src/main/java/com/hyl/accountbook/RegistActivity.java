package com.hyl.accountbook;

import com.hyl.dao.DBOpenHelper;
import com.hyl.util.pubFun;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * @programName: RegistActivity.java
 * @programFunction: the regiter page
 * @createDate: 2018/09/19
 * @author: AnneHan
 * @version:
 * xx.   yyyy/mm/dd   ver    author    comments
 * 01.   2018/09/19   1.00   AnneHan   New Create
 */
public class RegistActivity extends AppCompatActivity {
    private EditText editPhone;
    private EditText editPwd;
    private Button btnRegist;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.regist);
        editPhone = (EditText) findViewById(R.id.editPhone);
        editPwd = (EditText) findViewById(R.id.editPwd);
        btnRegist = (Button) findViewById(R.id.btnRegist);
    }

    /**
     * register event
     * @param v
     */
    public void OnMyRegistClick(View v)
    {
        boolean isTrue = true;
        if(pubFun.isPhoneNumberValid(editPhone.getText().toString()) == false){
            isTrue = false;
            Toast.makeText(this, "手机号格式不正确！", Toast.LENGTH_SHORT).show();
            return;
        }
        if(pubFun.isEmpty(editPwd.getText().toString())){
            isTrue = false;
            Toast.makeText(this, "密码不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }

        if(isTrue = true){
            //call DBOpenHelper
            DBOpenHelper helper = new DBOpenHelper(this,"qianbao.db",null,1);
            SQLiteDatabase db = helper.getWritableDatabase();
            Cursor c = db.query("user_tb",null,"userID=?",new String[]{editPhone.getText().toString()},null,null,null);
            if(c!=null && c.getCount() >= 1){
                Toast.makeText(this, "该用户已存在", Toast.LENGTH_SHORT).show();
                c.close();
            }
            else{
                //insert data
                ContentValues values= new ContentValues();
                values.put("userID",editPhone.getText().toString());
                values.put("pwd",editPwd.getText().toString());
                long rowid = db.insert("user_tb",null,values);

                Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
                this.finish();
            }
            db.close();
        }else{
            return;
        }
    }
}
