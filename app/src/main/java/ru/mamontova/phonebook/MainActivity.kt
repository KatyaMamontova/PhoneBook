package ru.mamontova.phonebook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*
import ru.mamontova.phonebook.db.DBmanager
import ru.mamontova.phonebook.fragments.ContactsFragment
import ru.mamontova.phonebook.fragments.FavoritesFragment

class MainActivity : AppCompatActivity() {

    private val dbManager = DBmanager(this)
    private val contactsFragment = ContactsFragment(dbManager)
    private val favoritesFragment = FavoritesFragment(dbManager)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        replaceFragment(contactsFragment)

        bottomNavigationView.setOnNavigationItemReselectedListener {
            when(it.itemId) {
                R.id.contacts -> replaceFragment(contactsFragment)
                R.id.favorites -> replaceFragment(favoritesFragment)
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        dbManager.closeDB()
    }
}