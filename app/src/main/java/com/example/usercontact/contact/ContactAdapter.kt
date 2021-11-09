package com.example.usercontact.contact

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.usercontact.database.UserModel
import com.example.usercontact.databinding.ContactListItemBinding


class ContactAdapter(private var onClickListener: OnClickListener) :
    ListAdapter<UserModel, ContactAdapter.ItemViewModel>(
        UserDiffCallBack()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewModel {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ContactListItemBinding.inflate(layoutInflater, parent, false)
        return ItemViewModel(binding)
    }

    override fun onBindViewHolder(holder: ItemViewModel, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ItemViewModel(val binding: ContactListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: UserModel) {
            binding.user = item
            binding.executePendingBindings()
        }

        init {
            itemView.setOnClickListener {
                onClickListener.onClick(binding.user!!.userNumber)
            }
        }
    }


}

class UserDiffCallBack : DiffUtil.ItemCallback<UserModel>() {

    override fun areItemsTheSame(oldItem: UserModel, newItem: UserModel): Boolean {
        return oldItem.userNumber == newItem.userNumber
    }

    override fun areContentsTheSame(oldItem: UserModel, newItem: UserModel): Boolean {
        return oldItem == newItem
    }

}


class OnClickListener(val clickLisenter: (userNumber: String) -> Unit) {

    fun onClick(userNumber: String) = clickLisenter(userNumber)

}
