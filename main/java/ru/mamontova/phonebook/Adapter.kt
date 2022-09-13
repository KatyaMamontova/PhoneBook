package ru.mamontova.phonebook

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.contact.view.*
import androidx.recyclerview.widget.RecyclerView.Adapter as Adapter1

class Adapter(
    private val list: List<Contact>,
    private val listener: OnItemClickListener
) : Adapter1<Adapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val contact = LayoutInflater.from(parent.context).inflate(R.layout.contact, parent, false)
        return ViewHolder(contact)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentContact = list[position]

        holder.imageView.setImageResource(currentContact.imageResource)
        holder.contactName.text = currentContact.text1
        holder.contactPhone.text = currentContact.text2
        holder.switchFavsBtn.setImageResource(currentContact.isFaveImgResource)
        holder.deleteContactBtn.setImageResource(currentContact.deleteImgResource)

        holder.switchFavsBtn.setOnClickListener{
            listener.switchFavs(position)
        }
        holder.deleteContactBtn.setOnClickListener{
            listener.removeItem(position)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.image_view
        val contactName: TextView = itemView.text_view_1
        val contactPhone: TextView = itemView.text_view_2
        val deleteContactBtn: ImageView = itemView.deleteContact
        val switchFavsBtn: ImageView = itemView.switchFavsBtn
    }

    interface OnItemClickListener {
        fun removeItem(position: Int)
        fun switchFavs(position: Int)
    }

}