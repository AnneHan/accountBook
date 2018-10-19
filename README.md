# accountBook
这是我独立做的第一个APP，是一个记账本APP。  
This is the first APP, I've ever done on my own. It's a accountbook APP.

# 关于我
***
Author  | AnneHan
--      | --
E-mail  | lilyhyl@163.com
Blog    | https://www.cnblogs.com/AnneHan/
***

# 效果图
（备注：在把图片制作成gif时，图片的质量受损，所以最终呈现出来的gif图片，背景变得有些模糊）  
<img src="https://github.com/AnneHan/accountBook/blob/master/effectpic/%E6%9C%AA%E5%91%BD%E5%90%8D.gif" height="500" alt="图片"/>

# 开发环境
IDE：Android Studio  
Database：SQLite  

# 功能介绍
该APP共包含6个功能，分别是：  
1、登录&注册功能：  
　进入该界面，默认是让用户进行登录操作；  
　若用户之前没有注册过，则会提示让用户进行注册；  
　若用户忘记登录密码，也有重置密码的功能。  
 
2、收入&支出功能：  
　该功能分为两个页签：明细、类别报表  
　这两个页签内容，都根据月份来统计呈现（开发中，目前是呈现所有收支明细）  
　并根据月份，显示当月的收入与支出（开发中，目前是呈现固定值）  
 
　明细页签：  
　　在该页签最下方会显示【记一笔】按钮，点击该按钮，会提示让用户选择记录的类型，是收入 or 支出  
　　然后会进入到记录收支明细的界面  
　　根据用户的选择类型，来呈现不同的内容  
　　当用户录入好收支明细，点击保存，会返回到明细页签，此时会刷新明细页签的内容（刷新功能开发中）  
  
　类别报表页签：  
　　该页签会根据用户的收支明细的类别来进行统计，呈现方式是圆形饼图（具体的统计逻辑开发中）  
  
3、统计功能：  
　该功能分为两个页签：明细、类别报表（该功能和收入&支出功能类似）  
　这两个页签内容，都根据月份来统计呈现（开发中，目前是呈现所有收支明细）  
　并根据月份，显示当月的结余，以及相比上月支出（开发中，目前是呈现固定值）  
 
4、特殊设置功能：该功能正在设计中。  

5、心愿墙功能：该功能正在设计中。  

6、关于我们功能：该功能主要是对APP进行简要介绍。

# 数据库设计：
目前APP共涉及到三个表，分别是：用户信息表、配置表、收支明细表  
```Java  
public void onCreate(SQLiteDatabase db){
	//user table
	db.execSQL("create table if not exists user_tb(_id integer primary key autoincrement," +
			"userID text not null," +
			"pwd text not null)");

	//Configuration table
	db.execSQL("create table if not exists refCode_tb(_id integer primary key autoincrement," +
			"CodeType text not null," +
			"CodeID text not null," +
			"CodeName text null)");

	//costDetail_tb
	db.execSQL("create table if not exists basicCode_tb(_id integer primary key autoincrement," +
			"userID text not null," +
			"Type integer not null," +
			"incomeWay text not null," +
			"incomeBy text not null," +
			"category text not null," +
			"item text not null," +
			"cost money not null," +
			"note text not null," +
			"makeDate text not null)");
}  
```

# 教程
以下博文是根据APP中涉及到的功能进行整理的，如下：
* [Android Studio 快速实现上传项目到Github（详细步骤）](https://www.cnblogs.com/AnneHan/p/9707232.html)
* [Android Studio 在项目中引用第三方jar包](https://www.cnblogs.com/AnneHan/p/9708051.html)
* [Android Studio 通过一个登录功能介绍SQLite数据库的使用](https://www.cnblogs.com/AnneHan/p/9724688.html)
* [Android Studio 使用ViewPager + Fragment实现滑动菜单Tab效果 --简易版](https://www.cnblogs.com/AnneHan/p/9702365.html)
* [Android Studio列表用法之一：ListView图文列表显示（实例）](https://www.cnblogs.com/AnneHan/p/9726391.html)
* [Android Studio 使用AChartEngine制作饼图](https://www.cnblogs.com/AnneHan/p/9773958.html)
* [Android Studio 使用Intent实现页面的跳转（带参数）](https://www.cnblogs.com/AnneHan/p/9705431.html)
* [Android Studio 点击两次返回键，退出APP](https://www.cnblogs.com/AnneHan/p/9704836.html)

# 参考：
* 首页的旋转菜单参考该博文：[参考](https://blog.csdn.net/lmj623565791/article/details/43131133)

# 版本：
目前的版本为V1.0  
APP还有很多功能需要继续完善，希望有兴趣的小伙伴可以一起参与进来，和我一起来开发完善。

# 一点想说的话
不论遇到什么困难，都不应该成为我们放弃的理由
