package com.example.yuanli.androidlab;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by yuanli on 2016-11-10.
 */

public class ChatDatabaseHelper extends SQLiteOpenHelper {


    // protected static final String ACTIVITY_NAME = "ChatDatabaseHelper";

    static final protected String DATABASE_NAME = "Chats.db";
    private static final int VERSION_NUM= 3;
    static final public String TABLE_NAME = "Chats";
    static  final public String KEY_ID =" id";
    static final public String KEY_MESSAGE="message";
    ChatDatabaseHelper chatDatabaseHelper;


    public ChatDatabaseHelper(Context ctx) {

        super(ctx, DATABASE_NAME, null, VERSION_NUM);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "
                + TABLE_NAME+
                "( " + KEY_ID
                + " integer primary key autoincrement, " + KEY_MESSAGE
                + " text not null);");
        Log.i("ChatDatabaseHelper", "Calling onCreate");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
        Log.i("ChatDatabaseHelper" , "Calling onUpgrade,oldVersion="+ oldVer+"newVersion=" +newVer);
        //  Log.i(ACTIVITY_NAME, "Calling onUpgrade, oldVersion=" + oldVer + " newVersion=" + newVer);



    }

//  public void close(){
//      chatDatabaseHelper.close();
//
//  }

}