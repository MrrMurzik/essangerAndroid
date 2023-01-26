package com.example.level2.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.level2.databinding.MyContactsActivityBinding
import com.example.level2.model.User
import com.example.level2.viewmodel.ContactsViewModel

// dependency injection (hilt, coin, dagger)

class ActivityMyContacts : AppCompatActivity(), UserActionListener {

    private lateinit var binding: MyContactsActivityBinding

    private val adapterContacts: ContactsAdapter by lazy {
        ContactsAdapter(actionListener = this@ActivityMyContacts)
    }


    private val viewModel: ContactsViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MyContactsActivityBinding.inflate(layoutInflater)

        setContentView(binding.root)

        initRecycler()

        setObservers()

    }

    private fun initRecycler() {
        binding.recyclerContacts.run {
            adapter = adapterContacts
            layoutManager = LinearLayoutManager(this@ActivityMyContacts)
        }
    }

    private fun setObservers() {
       viewModel.contactsLiveData.observe(this){
            adapterContacts.submitList(it.toMutableList())
       }
    }

    override fun onUserDelete(user: User) {
        viewModel.deleteUser(user)
    }

    override fun onUndoDeletion(user: User) {
        TODO("Not yet implemented")
    }

}