package com.example.sqlitedatabase.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.sqlitedatabase.R
import com.example.sqlitedatabase.databinding.ActivityUpdatedataBinding
import com.example.sqlitedatabase.dbhelper.DbHelper
import com.example.sqlitedatabase.models.UserList
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.android.synthetic.main.activity_updatedata.*
import kotlinx.android.synthetic.main.activity_updatedata.view.*

class UpdatedataActivity : AppCompatActivity() {

    lateinit var binding: ActivityUpdatedataBinding
    lateinit var dbHelper: DbHelper
    private lateinit var userList: UserList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdatedataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DbHelper(this)
        bundleData()
    }

    private fun bundleData() {
        userList = intent.extras!!.getParcelable("update")!!
        binding.updateFullname.edtName?.setText(userList.name)
        binding.updateMobile.edtMobile?.setText(userList.mobileNumber)
        binding.updateEmail.edtEmail?.setText(userList.email)
        if (userList.gender == "Male") {
            binding.updateRadioMale.isChecked = true
        } else
            binding.updateRadioFemale.isChecked = true

        binding.updateAddress.edtAddress?.setText(userList.address)

        updateData()

    }

    private fun updateData() {
        binding.updatedata.setOnClickListener {
            var name = binding.edtName.text.toString()
            var mobile = binding.edtMobile.text.toString()
            var email = binding.edtEmail.text.toString()
            var gender = ""
            gender = if (binding.updateRadiogroup.updateRadioMale.isChecked) {
                "Male"
            } else {
                "Female"
            }
            var address = binding.edtAddress.text.toString()

            if (name.isEmpty()) {
                edtName.error = getString(R.string.please_enter_fullname)
            } else if (mobile.isEmpty()) {
                edtMobile.error = getString(R.string.please_enter_mobile)
            } else if (email.isEmpty()) {
                edtEmail.error = getString(R.string.please_enter_email)
            } else if (address.isEmpty()) {
                edtAddress.error = getString(R.string.please_enter_address)
            }

            val updateData =
                dbHelper.updateUserData(
                    UserList(userList.id, name, mobile, email, gender, address))
            if (updateData > -1) {
                Toast.makeText(this, R.string.update_successfully, Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, ViewdataActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, R.string.update_not_successfully, Toast.LENGTH_SHORT).show()

            }
        }

    }
}