package com.example.usercontact

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.contacsusers.ContactAdapter
import com.example.usercontact.database.UserModel


@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<UserModel>?){
    val adapter= recyclerView.adapter as? ContactAdapter
    adapter?.submitList(data)

}