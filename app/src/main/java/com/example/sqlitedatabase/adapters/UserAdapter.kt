package com.example.sqlitedatabase.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sqlitedatabase.R
import com.example.sqlitedatabase.models.UserList
import kotlinx.android.synthetic.main.item_view_userdata.view.*

class UserAdapter(
    val context: Context, private val userList: ArrayList<UserList>,
    var callback: (UserList, Int,View) -> Unit
):
    RecyclerView.Adapter<UserAdapter.ViewHolder>() {


    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var fullname : TextView
        var mobile : TextView
        var upBtn : ImageView
        var delBtn : ImageView
        init {
            fullname = itemView.findViewById(R.id.textFullname)
            mobile = itemView.findViewById(R.id.textMobile)
            upBtn = itemView.findViewById(R.id.updateBtn)
            delBtn = itemView.findViewById(R.id.deleteBtn)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.ViewHolder {
        val view: View =

            LayoutInflater.from(parent.context).inflate(R.layout.item_view_userdata, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.fullname.text = userList[position].name
        holder.mobile.text = userList[position].mobileNumber

        holder.itemView.updateBtn.setOnClickListener {
            callback.invoke(userList[position],position,holder.upBtn)
        }

        holder.itemView.deleteBtn.setOnClickListener {
            callback.invoke(userList[position],position,holder.delBtn)
        }

    }

    override fun getItemCount(): Int = userList.size
}