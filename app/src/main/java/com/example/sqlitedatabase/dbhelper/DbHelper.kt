package com.example.sqlitedatabase.dbhelper

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.sqlitedatabase.const.*
import com.example.sqlitedatabase.models.UserList

class DbHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME,null, DATABASE_VERSION) {


    override fun onCreate(db: SQLiteDatabase?) {
        val query = ("CREATE TABLE $TABLE_NAME($USER_ID INTEGER PRIMARY KEY,$USER_NAME TEXT,$MOBILE_NUMBER TEXT,$EMAIL TEXT,$GENDER TEXT,$ADDRESS TEXT )")
        db?.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)

    }
    fun addUser(user:UserList): Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(USER_NAME,user.name)
        contentValues.put(MOBILE_NUMBER,user.mobileNumber)
        contentValues.put(EMAIL,user.email)
        contentValues.put(GENDER,user.gender)
        contentValues.put(ADDRESS,user.address)

        val userInsert = db.insert(TABLE_NAME,null,contentValues)
        db.close()
        return userInsert
    }

    @SuppressLint("Range")
    fun getAllUserData(): Cursor {
        val db=this.readableDatabase
        var cursor=db.rawQuery("SELECT *  FROM $TABLE_NAME",null)
        return cursor
    }
    // update userData
    fun updateUserData(model:UserList): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(USER_ID,model.id)
        contentValues.put(USER_NAME,model.name)
        contentValues.put(MOBILE_NUMBER,model.mobileNumber)
        contentValues.put(EMAIL,model.email)
        val usr = db.update(TABLE_NAME, contentValues, "id=" +model.id,null)
        db.close()
        return usr
    }


    @SuppressLint("Range")
    fun showUserData():ArrayList<UserList>{
        var usrList: ArrayList<UserList> = ArrayList<UserList>()
        val selectQuery = "SELECT *  FROM $TABLE_NAME"
        val db = this.readableDatabase
        var cursor : Cursor? = null
        try {
            cursor = db.rawQuery(selectQuery,null)
        }catch (e : SQLException){
            db.execSQL(selectQuery)
            return ArrayList()
        }
        if (cursor.moveToFirst()){
            do {
                usrList.add(
                    UserList(
                        cursor.getInt(cursor.getColumnIndex(USER_ID)),
                        cursor.getString(cursor.getColumnIndex(USER_NAME)),
                        cursor.getString(cursor.getColumnIndex(MOBILE_NUMBER)),
                        cursor.getString(cursor.getColumnIndex(EMAIL)),
                        cursor.getString(cursor.getColumnIndex(GENDER)),
                        cursor.getString(cursor.getColumnIndex(ADDRESS)),
                    )
                )

            }while (cursor.moveToNext())
        }
        return usrList
    }

    // delete for userData
    fun deleteUserData(id:Int):Int {
        var db = this.writableDatabase
        val delete = db.delete(TABLE_NAME,"id=$id",null)
        db.close()
        return delete
    }
}