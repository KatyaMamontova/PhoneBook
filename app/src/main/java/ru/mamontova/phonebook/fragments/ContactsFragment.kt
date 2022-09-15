package ru.mamontova.phonebook.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_contacts.*
import kotlinx.android.synthetic.main.fragment_contacts.view.*
import ru.mamontova.phonebook.Adapter
import ru.mamontova.phonebook.Contact
import ru.mamontova.phonebook.R
import ru.mamontova.phonebook.db.DBmanager

class ContactsFragment(val dbManager: DBmanager) : Fragment(), Adapter.OnItemClickListener {

    val contactsList = ArrayList<Contact>()
    val adapter = Adapter(contactsList, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_contacts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.addContactBtn.setOnClickListener { saveContact() }

        recycler_view.adapter = adapter
        recycler_view.layoutManager = LinearLayoutManager(context)
        recycler_view.setHasFixedSize(true)

        showContacts(dbManager.readDbData())
    }

    private fun saveContact() {
        dbManager.insertToDB(editTextName.text.toString(), editTextPhone.text.toString(), 0)
        showContacts(dbManager.readDbData())
        adapter.notifyDataSetChanged()
    }

    private fun showContacts(list: ArrayList<Map<String, Any>>) {
        clear()
        for(item in list) {
            val avatar = R.drawable.ic_android
            val bin = R.drawable.ic_baseline_delete_24
            val favorite = R.drawable.favorite_item
            val notFavorite = R.drawable.favorite_border

            val isFavorite = item["isFavorite"]
            val isFave = if (isFavorite==true) favorite else notFavorite
            val contact = Contact(avatar, item["name"].toString(), item["phone"].toString(), isFave, bin, isFavorite)
            contactsList.add(contact)
        }
    }

    override fun removeItem(position: Int) {
        dbManager.removeFromDB(contactsList[position].text1)
        contactsList.removeAt(position)
        adapter.notifyItemRemoved(position)
    }

    override fun switchFavs(position: Int) {
        dbManager.switchFavs(contactsList[position].text1)
        contactsList[position].isFavorite = contactsList[position].isFavorite != true
        adapter.notifyItemChanged(position)
    }

    fun clear() {
        val size = contactsList.size
        contactsList.clear()
        adapter.notifyItemRangeRemoved(0, size)
    }

}
