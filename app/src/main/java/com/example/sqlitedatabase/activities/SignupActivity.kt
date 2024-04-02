package com.example.sqlitedatabase.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.sqlitedatabase.R
import com.example.sqlitedatabase.adapters.UserAdapter
import com.example.sqlitedatabase.databinding.ActivitySignupBinding
import com.example.sqlitedatabase.dbhelper.DbHelper
import com.example.sqlitedatabase.models.UserList


class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private lateinit var dbHelper: DbHelper
    private var adapter: UserAdapter? = null
    private var dataList = ArrayList<UserList>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DbHelper(this)

        binding.signupInsertdata.setOnClickListener {
            insertUserData()
        }

        binding.viewUserdata.setOnClickListener {
            val bundle = Bundle()
            bundle.putParcelableArrayList("star", dataList)
            var intent = Intent(this, ViewdataActivity::class.java)
            intent.putExtras(bundle)
            startActivity(intent)
        }
    }

    private fun insertUserData() {
        binding.apply {

            val fullname = signupFullname.editText?.text.toString().trim()
            val mobile = signupMobile.editText?.text.toString().trim()
            val email = signupEmail.editText?.text.toString().trim()
            val address = signupAddress.editText?.text.toString().trim()
            val selectOption: Int = signupRadiogroup.checkedRadioButtonId
            var selectGender = ""
            if (radioMale.isChecked) {
                selectGender = "Male"
            } else {
                selectGender = "Female"
            }

            signupFullname.error = null
            signupMobile.error = null
            signupEmail.error = null
            signupAddress.error = null


            if (fullname.isEmpty()) {
                signupFullname.error = getString(R.string.please_enter_fullname)
            } else if (mobile.isEmpty()) {
                signupMobile.error = getString(R.string.please_enter_mobile)
            } else if (email.isEmpty()) {
                signupEmail.error = getString(R.string.please_enter_email)
            } else if (address.isEmpty()) {
                signupAddress.error = getString(R.string.please_enter_address)
            } else {

                var addData =
                    dbHelper.addUser(UserList(null, fullname, mobile, email, selectGender, address))
                if (addData > 0) {

                    Toast.makeText(this@SignupActivity, R.string.insert_data, Toast.LENGTH_SHORT)
                        .show()

                    Log.e("dataCount-->", "" + dbHelper.getAllUserData().count)

                }

                //Toast.makeText(this@SignupActivity, R.string.data_inserted, Toast.LENGTH_SHORT).show()
//                val i = Intent(this@SignupActivity, ViewdataActivity::class.java)
//                startActivity(i)
            }
        }

    }


}
