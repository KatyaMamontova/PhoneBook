package ru.mamontova.phonebook

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.favorite_contact.view.*
import androidx.recyclerview.widget.RecyclerView.Adapter as AdapterForFaves1

class AdapterForFavs(
    private val list: List<FavoriteContact>
) :  AdapterForFaves1<AdapterForFavs.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val favContact = LayoutInflater.from(parent.context).inflate(R.layout.favorite_contact, parent, false)
        return ViewHolder(favContact)
    }

    override fun getItemCount() = list.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val contactName: TextView = itemView.name_of_fav
        val contactPhone: TextView = itemView.phone_of_fav
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentContact = list[position]

        holder.contactName.text = currentContact.name
        holder.contactPhone.text = currentContact.phone
    }
}