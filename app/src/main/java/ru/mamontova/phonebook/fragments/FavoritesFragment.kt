package ru.mamontova.phonebook.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_favorites.*
import ru.mamontova.phonebook.*
import ru.mamontova.phonebook.db.DBmanager

class FavoritesFragment(private val dbManager: DBmanager) : Fragment() {

    private val contactsList = ArrayList<FavoriteContact>()
    private val adapter = AdapterForFavs(contactsList)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler_view_for_favorites.adapter = adapter
        recycler_view_for_favorites.layoutManager = LinearLayoutManager(context)
        recycler_view_for_favorites.setHasFixedSize(true)

        showContacts(dbManager.selectFaves())
    }

    private fun showContacts(list: ArrayList<Map<String, Any>>) {
        clear()
        for(item in list) {
            val contact = FavoriteContact(item["name"].toString(), item["phone"].toString())
            contactsList.add(contact)
        }
    }

    private fun clear() {
        val size = contactsList.size
        contactsList.clear()
        adapter.notifyItemRangeRemoved(0, size)
    }
}