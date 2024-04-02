package com.example.sqlitedatabase.activities

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sqlitedatabase.R
import com.example.sqlitedatabase.adapters.UserAdapter
import com.example.sqlitedatabase.databinding.ActivityViewdataBinding
import com.example.sqlitedatabase.dbhelper.DbHelper
import com.example.sqlitedatabase.models.UserList
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ViewdataActivity : AppCompatActivity() {

    lateinit var binding: ActivityViewdataBinding
    lateinit var dataHelper: DbHelper
    private var dataList = ArrayList<UserList>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewdataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dataHelper = DbHelper(this)

        dataList = dataHelper.showUserData()

        val adapter = UserAdapter(this, dataList) { user, position, view ->
            when (view.id) {
                R.id.updateBtn -> {
                    navigateUpdate(user)
                }
                R.id.deleteBtn -> {
                    val builder = MaterialAlertDialogBuilder(this)
                    builder.setTitle(R.string.delete)
                    builder.setMessage(R.string.confirmation_data_deleted)
                    builder.setCancelable(false)
                    builder.setPositiveButton(R.string.ok_text, DialogInterface.OnClickListener { _, _ ->
                        deletedata(user,position)
                    })
                    builder.show()
                }
            }
        }
        binding.recyclerViewdata.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewdata.adapter = adapter
    }

    // Data delete method
    fun deletedata(user: UserList, position: Int) {
        var delete= dataHelper.deleteUserData(user.id!!)
        if (delete>-1){
            Toast.makeText(this, R.string.delete_successfully, Toast.LENGTH_SHORT).show()
            dataList.removeAt(position)
            startActivity(Intent(this,SignupActivity::class.java))
        }else{
            Toast.makeText(this, R.string.not_delete_data, Toast.LENGTH_SHORT).show()
        }
    }

    fun navigateUpdate(user: UserList) {
        var bundle = Bundle()
        bundle.putParcelable("update", user)
        Log.e("dataCount-->", "" + user)
        val intent = Intent(this, UpdatedataActivity::class.java)
        intent.putExtras(bundle)
        startActivity(intent)
        finish()
    }
}