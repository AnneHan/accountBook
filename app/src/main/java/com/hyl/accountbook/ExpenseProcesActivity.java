package com.hyl.accountbook;

import com.hyl.dao.DBOpenHelper;
import com.hyl.util.pubFun;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @programName: ExpenseProcesActivity.java
 * @programFunction: Add an income and expense record
 * @createDate: 2018/09/19
 * @author: AnneHan
 * @version:
 * xx.   yyyy/mm/dd   ver    author    comments
 * 01.   2018/09/19   1.00   AnneHan   New Create
 */
public class ExpenseProcesActivity extends AppCompatActivity {

    private int type = 0;//0:income   1:payout
    final static int EDIT_MODE = 2;

    private String[] str = null;
    private String[] accountId = null;
    private Calendar calendar = Calendar.getInstance();
    private DatePickerDialog datePicker = null;
    private AlertDialog dialog = null;
    private ArrayAdapter<String> adapter;
    private List<String> list = null;


    private TextView title_tv = null;
    private RadioGroup trans_type_tab_rg = null;
    private RadioButton rb1=null;
    private RadioButton rb2=null;

    private FrameLayout corporation_fl = null;
    private FrameLayout empty_fl = null;
    private Button cost_btn = null;
    private String  value="0";
    private Spinner first_level_category_spn = null;
    private Spinner sub_category_spn = null;
    private int type_sub_id = 0;
    private Spinner account_spn = null;
    private Spinner corporation_spn = null;
    private Button trade_time_btn = null;
    private Spinner project_spn = null;
    private Button memo_btn = null;
    private Button save_btn = null;
    private Button cancel_btn = null;

    private EditText edit = null;
    private int isInitOnly = 0;

    private Context context;

    //类别
    private static String[] bigCategoryList = { "" };
    private static String[] defaultSubCategory_info = { "" };
    //子类别
    private static String[][] subCategory_info = new String[][] {{ "" }, { "" }};
    //账户
    private static String[] accountList = { "" };
    //商家
    private static String[] shopList = { "" };
    //备注
    private static String[] noteList = { "" };

    private TextView txtBigCategory_view;
    private Spinner BigCategory_spinner;
    private ArrayAdapter<String> BigCategory_adapter;

    private TextView txtSubCategory_view;
    private Spinner subCategory_spinner;
    private ArrayAdapter<String> subCategory_adapter;

    private TextView txtAccount_view;
    private Spinner account_spinner;
    private ArrayAdapter<String> account_adapter;

    private TextView txtShop_view;
    private Spinner shop_spinner;
    private ArrayAdapter<String> shop_adapter;

    private TextView txtNote_view;
    private Spinner note_spinner;
    private ArrayAdapter<String> note_adapter;

    private String txtBigCategory = "";
    private String txtSubCategory = "";
    private String txtAccount = "";
    private String txtShop = "";
    private String txtNote = "";

    private TextView txtDate;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expense_proces);

        //接收传递过来的参数
        final Intent intent = getIntent();
        type = intent.getIntExtra("strType", 0);

        context = this;

        initSpinner();

        loadingFormation();

        trade_time_btn.setText(pubFun.format(calendar.getTime()));

        cost_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent i = new Intent(ExpenseProcesActivity.this, KeyPad.class);
                i.putExtra("value", value);
                startActivityForResult(i, 0);
            }
        });
        trade_time_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                openDate();
            }
        });
    }

    private void loadingFormation(){
        cost_btn=(Button)findViewById(R.id.cost_btn);
        trade_time_btn=(Button)findViewById(R.id.trade_time_btn);
    }

    private void openDate() {
        datePicker = new DatePickerDialog(this, mDateSetListenerSatrt,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePicker.show();
    }

    @Override
    /**
     * return money
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        if (resultCode == Activity.RESULT_OK && requestCode == 0) {
            Bundle extras = data.getExtras();
            value = extras.getString("value");
            cost_btn.setText(DecimalFormat.getCurrencyInstance().format(Double.parseDouble(value)));
        }
    }

    /**
     * return date
     */
    private DatePickerDialog.OnDateSetListener mDateSetListenerSatrt = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.YEAR, year);
            trade_time_btn.setText(pubFun.format(calendar.getTime()));
        }
    };

    /**
     * 初始化spinner
     */
    private void initSpinner(){
        if(type == 0){
            //类别
            bigCategoryList = new String[] { "职业收入", "其他收入" };
            defaultSubCategory_info = new String[] { "兼职收入", "投资收入" ,"奖金收入","加班收入","利息收入","工资收入" };
            //子类别
            subCategory_info = new String[][] {
                    { "兼职收入", "投资收入" ,"奖金收入","加班收入","利息收入","工资收入" },
                    { "经营所得", "意外来钱" ,"中奖收入","礼金收入" }};
            //账户
            accountList = new String[] { "现金账户", "金融账户","虚拟账户","负债账户","债权账户" };
            //商家
            shopList = new String[] { "银行", "公交","饭堂","商场","超市","其他" };
            //备注
            noteList = new String[] { "腐败", "旅游","装修","公司报销","出差","其他" };
        }else{
            bigCategoryList = new String[] { "衣服饰品", "食品酒水","居家物业","行车交通","交流通讯","休闲娱乐","学习进修"
                    ,"人情往来","医疗保健","金融保险","其他杂项" };
            defaultSubCategory_info = new String[] { "衣服裤子", "鞋帽包包" ,"化妆饰品" };
            //子类别
            subCategory_info = new String[][] {
                    { "衣服裤子", "鞋帽包包" ,"化妆饰品" },
                    { "早午晚餐", "水果零食" ,"烟酒茶" },
                    { "房租", "物业管理" ,"维修保养", "水电煤气" ,"日常用品" },
                    { "公共交通", "打车租车" ,"私家车费用" },
                    { "手机费", "上网费" ,"座机费" ,"邮寄费" },
                    { "休闲玩乐", "旅游度假" ,"宠物宝贝", "腐败聚会" ,"运动健身" },
                    { "书报杂志", "数码装备" ,"培训进修" },
                    { "送礼请客", "慈善捐助" ,"还人钱物", "孝敬家长" },
                    { "治疗费", "美容费" ,"保健费" ,"药品费" },
                    { "赔偿罚款", "利息支出" ,"消费税收", "按揭还款" ,"投资亏损", "银行手续" },
                    { "烂账损失", "意外丢失" ,"其他支出" }};
            //账户
            accountList = new String[] { "银行卡", "公交卡","饭卡","支付宝","财付通","现金","其他" };
            //商家
            shopList = new String[] { "银行", "公交","饭堂","商场","超市","其他" };
            //备注
            noteList = new String[] { "腐败", "旅游","装修","公司报销","出差","其他" };
        }

        /**
         * 1、定义类别下拉菜单
         */
        txtBigCategory_view = (TextView) findViewById(R.id.txtBigCategory);
        BigCategory_spinner = (Spinner) findViewById(R.id.BigCategory_spinner);
        //将可选内容与ArrayAdapter连接起来
        BigCategory_adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, bigCategoryList);
        //设置下拉列表的风格
        BigCategory_adapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //将adapter添加到spinner中
        BigCategory_spinner.setAdapter(BigCategory_adapter);
        // 添加事件Spinner事件监听
        BigCategory_spinner
                .setOnItemSelectedListener(new BigCategory_spinnerSelectedListener());
        // 设置默认值
        BigCategory_spinner.setVisibility(View.VISIBLE);

        /**
         * 2、定义子类别下拉菜单
         */
        txtSubCategory_view = (TextView) findViewById(R.id.txtSubCategory);
        subCategory_spinner = (Spinner) findViewById(R.id.subCategory_spinner);
        subCategory_adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, defaultSubCategory_info);
        subCategory_adapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subCategory_spinner.setAdapter(subCategory_adapter);
        subCategory_spinner
                .setOnItemSelectedListener(new subCategory_spinnerSelectedListener());
        subCategory_spinner.setVisibility(View.VISIBLE);

        /**
         * 3、定义账户下拉菜单
         */
        txtAccount_view = (TextView)findViewById(R.id.txtAccount);
        account_spinner = (Spinner) findViewById(R.id.account_spinner);
        account_adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, accountList);
        account_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        account_spinner.setAdapter(account_adapter);
        account_spinner
                .setOnItemSelectedListener(new account_spinnerSelectedListener());
        account_spinner.setVisibility(View.VISIBLE);

        /**
         * 4、定义商家下拉菜单
         */
        txtShop_view = (TextView)findViewById(R.id.txtShop);
        shop_spinner = (Spinner) findViewById(R.id.shop_spinner);
        shop_adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, shopList);
        shop_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        shop_spinner.setAdapter(shop_adapter);
        shop_spinner
                .setOnItemSelectedListener(new shop_spinnerSelectedListener());
        shop_spinner.setVisibility(View.VISIBLE);

        /**
         * 5、定义备注下拉菜单
         */
        txtNote_view = (TextView)findViewById(R.id.txtNote);
        note_spinner = (Spinner) findViewById(R.id.note_spinner);
        note_adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, noteList);
        note_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        note_spinner.setAdapter(note_adapter);
        note_spinner
                .setOnItemSelectedListener(new note_spinnerSelectedListener());
        note_spinner.setVisibility(View.VISIBLE);
    }

    /**
     * 选择 类别 事件 监听器
     */
    class BigCategory_spinnerSelectedListener implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                                   long arg3) {
            txtBigCategory = bigCategoryList[arg2];
            int pos = BigCategory_spinner.getSelectedItemPosition();
            subCategory_adapter = new ArrayAdapter<String>(context,
                    android.R.layout.simple_spinner_item, subCategory_info[pos]);
            subCategory_spinner.setAdapter(subCategory_adapter);
        }
        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }

    /**
     * 选择 子类别 事件 监听器
     */
    class subCategory_spinnerSelectedListener implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                                   long arg3) {
            txtSubCategory = (String) subCategory_spinner
                    .getItemAtPosition(arg2);
        }
        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }

    /**
     * 选择 账户事件 监听器
     */
    class account_spinnerSelectedListener implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                                   long arg3) {
            txtAccount = accountList[arg2];
        }
        public void onNothingSelected(AdapterView<?> arg0) {
        }

    }

    /**
     * 选择 商家事件 监听器
     */
    class shop_spinnerSelectedListener implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                                   long arg3) {
            txtShop = shopList[arg2];
        }
        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }

    /**
     * 选择 商家事件 监听器
     */
    class note_spinnerSelectedListener implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                                   long arg3) {
            txtNote = noteList[arg2];
        }
        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }

    public void OnMySaveClick(View v) {
        saveInfo();
    }
    public void OnMyCancelClick(View v) {
        exit();
    }

    /**
     * cancel event
     */
    private void exit() {
        if(type != EDIT_MODE){
            Intent intent = new Intent(this,SpendingActivity.class);
            startActivity(intent);
            finish();
        }else{
            this.setResult(RESULT_OK, getIntent());
            this.finish();
        }
    }

    /**
     * save event
     */
    private void saveInfo() {
        //Save之前先判断用户是否登录
        SharedPreferences sharedPreferences= getSharedPreferences("setting",Activity.MODE_PRIVATE);
        String userID =sharedPreferences.getString("userID", "");

        Log.i("info", "此次登录的用户是" + userID);

        if(userID.isEmpty()){
            new AlertDialog.Builder(this)
                    .setTitle("提示")
                    .setMessage("您还未登录，请点击确定按钮进行登录！")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            setResult(RESULT_OK);
                            Intent intent=new Intent(ExpenseProcesActivity.this,LoginActivity.class);
                            ExpenseProcesActivity.this.startActivity(intent);
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            return;
                        }
                    })
                    .show();
        }else{
            if(value.equals("") || value == null || Double.parseDouble(value) <= 0){
                Toast.makeText(getApplicationContext(), getString(R.string.input_message),
                        Toast.LENGTH_SHORT).show();
                return;
            }

            //调用DBOpenHelper
            DBOpenHelper helper = new DBOpenHelper(this,"qianbao.db",null,1);
            SQLiteDatabase db = helper.getWritableDatabase();
            //插入数据
            ContentValues values= new ContentValues();
            values.put("userID",userID);
            values.put("Type",type);
            values.put("incomeWay",txtAccount);
            values.put("incomeBy",txtShop);
            values.put("category",txtBigCategory);
            values.put("item",txtSubCategory);
            values.put("cost", value);
            values.put("note", txtNote);
            values.put("makeDate",pubFun.format(calendar.getTime()));
            long rowid = db.insert("basicCode_tb",null,values);

            //Test
            Cursor c = db.query("basicCode_tb",null,"userID=?",new String[]{userID},null,null,null);
            if(c!=null && c.getCount() >= 1){
                String[] cols = c.getColumnNames();
                while(c.moveToNext()){
                    for(String ColumnName:cols){
                        Log.i("info",ColumnName+":"+c.getString(c.getColumnIndex(ColumnName)));
                    }
                }
                c.close();
            }
            db.close();

            Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
        }
    }
}
