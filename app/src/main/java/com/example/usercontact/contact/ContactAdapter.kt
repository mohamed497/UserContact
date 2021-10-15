package com.example.android.contacsusers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.usercontact.database.UserModel
import com.example.usercontact.databinding.ContactListItemBinding

//class ContactAdapter: ListAdapter<UserModel, ItemViewModel>(UserDiffCallBack()) {

class ContactAdapter: ListAdapter<UserModel, ItemViewModel>(UserDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewModel {
        return ItemViewModel.from(parent)
    }

    override fun onBindViewHolder(holder: ItemViewModel, position: Int) {
        
        holder.bind(getItem(position)!!)
//        holder.bind(usersList[position])
    }
}

class UserDiffCallBack: DiffUtil.ItemCallback<UserModel>() {
    override fun areItemsTheSame(oldItem: UserModel, newItem: UserModel): Boolean {
        return oldItem.userNumber == newItem.userNumber
    }

    override fun areContentsTheSame(oldItem: UserModel, newItem: UserModel): Boolean {
        return oldItem == newItem
    }

}

class ItemViewModel private constructor(val binding: ContactListItemBinding): RecyclerView.ViewHolder(binding.root){

    fun bind(item: UserModel){
//        binding.userName.text = item.userName
        binding.user = item
        binding.executePendingBindings()
    }
    companion object{
        fun from(parent: ViewGroup): ItemViewModel{
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ContactListItemBinding.inflate(layoutInflater, parent, false)
            return ItemViewModel(binding)
        }
    }

}
