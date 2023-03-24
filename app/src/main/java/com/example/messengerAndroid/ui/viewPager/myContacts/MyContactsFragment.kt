package com.example.messengerAndroid.ui.viewPager.myContacts

import android.Manifest.permission.READ_CONTACTS
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.messengerAndroid.R
import com.example.messengerAndroid.data.contactsRepository.contactModel.User
import com.example.messengerAndroid.databinding.DialogDeniedPermissionBinding
import com.example.messengerAndroid.databinding.FragmentMyContactsBinding
import com.example.messengerAndroid.extensions.factory
import com.example.messengerAndroid.extensions.navigate
import com.example.messengerAndroid.extensions.openAppSettings
import com.example.messengerAndroid.ui.base.BaseFragment
import com.example.messengerAndroid.ui.viewPager.myContacts.adapter.ContactsAdapter
import com.example.messengerAndroid.ui.viewPager.myContacts.adapter.UserActionListener
import com.example.messengerAndroid.ui.viewPager.myContacts.contactsViewModel.ContactsViewModel
import com.example.messengerAndroid.utils.Constants.TAG_ADD_CONTACT_DIALOG
import com.google.android.material.snackbar.Snackbar


class MyContactsFragment : BaseFragment<FragmentMyContactsBinding>(FragmentMyContactsBinding::inflate) {

    private val viewModel: ContactsViewModel by viewModels { factory() }

    private var actionListener: UserActionListener? = object : UserActionListener {
        override fun onUserDelete(user: User) {
            val listener = getDeletionAlertDialogListener(user)
            showDeletionAlertDialog(listener)
        }

        override fun onItemClicked(user: User) {
            navigate().showViewDetails(user.id)
        }

        override fun updateRecycler() {
            binding.recyclerContacts.adapter = adapterContacts
        }


    }


    private val adapterContacts: ContactsAdapter by lazy {
        ContactsAdapter(actionListener!!)
    }

    private val requestReadContactsPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        if (it) {
            setupRecyclerView()
        } else {
            showPermissionDeniedDialog()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (context?.checkSelfPermission(READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestReadContactsPermission()
        } else {
            setupRecyclerView()
        }
    }

    override fun onStart() {
        super.onStart()
        if (context?.checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED)
            setupRecyclerView()
    }



    override fun onDestroyView() {
        requestReadContactsPermissionLauncher.unregister()
        actionListener = null
        super.onDestroyView()
    }

    private fun setupRecyclerView() {
        initRecycler()
        addSwipeToDeleteFeature()
        setListeners()
        setObservers()
    }


    private fun initRecycler() {
        binding.recyclerContacts.run {
            adapter = adapterContacts
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun addSwipeToDeleteFeature() {
        val swipeToDeleteCallback = object : SwipeToDeleteCallback() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                val position = viewHolder.absoluteAdapterPosition
                val user = viewModel.contactsLiveData.value?.get(position)
                viewModel.deleteUser(user)
                showUndoSnackbar(user, position)

            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(binding.recyclerContacts)
    }

    private fun setListeners() {
        binding.textViewAddContacts.setOnClickListener {
            AddContactFragmentDialog().show(childFragmentManager, TAG_ADD_CONTACT_DIALOG)

        }

        binding.imageButtonArrowBack.setOnClickListener {
            findNavController().navigateUp()
        }

//        binding.floatingActionDeletionButton.setOnClickListener {
//            adapterContacts.checkedUsers.forEach {
//                viewModel.deleteUser(it)
//            }
//            adapterContacts.isMultiselectMode = false
//            binding.recyclerContacts.adapter = adapterContacts
//        }
    }


    private fun setObservers() {
        viewModel.contactsLiveData.observe(viewLifecycleOwner) {
            adapterContacts.submitList(it.toMutableList())
        }

        viewModel.multiSelectLiveData.observe(viewLifecycleOwner) { it ->
            if (it) {
                binding.floatingActionDeletionButton.visibility = VISIBLE
                viewModel.contactsLiveData.observe(viewLifecycleOwner) { list ->
                    adapterContacts.submitList(list.toMutableList())
                }
            } else {
                binding.floatingActionDeletionButton.visibility = GONE
                viewModel.contactsSelectModeLiveData.observe(viewLifecycleOwner) { list ->
                    adapterContacts.submitList(list.toMutableList())
                }
            }
        }

    }


    private fun getDeletionAlertDialogListener(user: User): DialogInterface.OnClickListener {
        return DialogInterface.OnClickListener { _, which ->
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> {
                    val userPosition = viewModel.getUserPosition(user)
                    viewModel.deleteUser(user)
                    showUndoSnackbar(user, userPosition)
                }
                DialogInterface.BUTTON_NEGATIVE -> {
                    showToast(getString(R.string.toast_cancelled_deletion))
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private fun showUndoSnackbar(user: User?, position: Int) {
        if (position == -1) {
            return
        }
        Snackbar.make(
            binding.root, R.string.deletion_snackbar_message, Snackbar.LENGTH_LONG
        ).setAction(R.string.undo_delete_action) { viewModel.addExistingUser(user, position) }
            .setActionTextColor(Color.CYAN).show()
    }

    private fun showDeletionAlertDialog(listener: DialogInterface.OnClickListener) {

        AlertDialog.Builder(requireContext()).setCancelable(true)
            .setTitle(R.string.default_deletion_dialog_title)
            .setMessage(R.string.default_deletion_dialog_message)
            .setTitle(R.string.default_deletion_dialog_title)
            .setMessage(R.string.default_deletion_dialog_message)
            .setPositiveButton(R.string.action_confirmed, listener)
            .setNegativeButton(R.string.action_cancelled, listener).create().show()
    }


    private fun requestReadContactsPermission() {
        requestReadContactsPermissionLauncher.launch(READ_CONTACTS)
    }

    private fun showPermissionDeniedDialog() {
        val dialogBinding = DialogDeniedPermissionBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(requireContext())
            .setCancelable(false)
            .setView(dialogBinding.root)
            .create()
        dialog.show()
        setPermissionDeniedDialogListeners(dialogBinding, dialog)
    }

    private fun setPermissionDeniedDialogListeners(
        dialogBinding: DialogDeniedPermissionBinding,
        dialog: AlertDialog
    ) {
        dialogBinding.buttonGrantPermission.setOnClickListener {
            if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
                requestReadContactsPermission()
            } else {
                openAppSettings()
            }
            dialog.dismiss()
        }
        dialogBinding.buttonCancel.setOnClickListener {
            dialog.dismiss()
            setupRecyclerView()
        }
    }
}
