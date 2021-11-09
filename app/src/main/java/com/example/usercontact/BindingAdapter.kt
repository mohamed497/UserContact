package com.example.usercontact

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.usercontact.contact.ContactAdapter
import com.example.usercontact.database.UserModel


@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<UserModel>?){
    val adapter= recyclerView.adapter as? ContactAdapter
    adapter?.submitList(data)
}

@BindingAdapter("customVisibility")
fun setVisibility(view : View, visible : Boolean) {
    view.visibility = if (visible) View.VISIBLE else View.INVISIBLE
}