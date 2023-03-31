package com.example.messengerAndroid.ui.viewPager.myContacts

import android.Manifest.permission.READ_CONTACTS
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.messengerAndroid.R
import com.example.messengerAndroid.data.contactsRepository.contactModel.User
import com.example.messengerAndroid.data.contactsRepository.contactModel.UserWithState
import com.example.messengerAndroid.databinding.DialogDeniedPermissionBinding
import com.example.messengerAndroid.databinding.FragmentMyContactsBinding
import com.example.messengerAndroid.extensions.factory
import com.example.messengerAndroid.extensions.navigate
import com.example.messengerAndroid.extensions.openAppSettings
import com.example.messengerAndroid.foundation.base.BaseFragment
import com.example.messengerAndroid.ui.viewPager.myContacts.adapter.BackgroundColorSelector
import com.example.messengerAndroid.ui.viewPager.myContacts.adapter.ContactsAdapter
import com.example.messengerAndroid.ui.viewPager.myContacts.adapter.UserActionListener
import com.example.messengerAndroid.ui.viewPager.myContacts.contactsViewModel.ContactsViewModel
import com.example.messengerAndroid.Constants.TAG_ADD_CONTACT_DIALOG
import com.google.android.material.snackbar.Snackbar


class MyContactsFragment : BaseFragment<FragmentMyContactsBinding>(FragmentMyContactsBinding::inflate) {

    private val viewModel: ContactsViewModel by viewModels { factory() }

    private var actionListener: UserActionListener? = object : UserActionListener {
        override fun onUserDelete(userWithState: UserWithState) {
            val listener = getDeletionAlertDialogListener(userWithState)
            showDeletionAlertDialog(listener)
        }

        override fun onItemClicked(userWithState: UserWithState) {
            navigate().showViewDetails(userWithState.user.id)
        }

        override fun onChangeMode() {
            viewModel.changeMode()
            binding.floatingActionDeletionButton.visibility = if (viewModel.getCurrentMode()) {
                VISIBLE
            } else {
                GONE
            }
            if (viewModel.getCurrentMode()) {
                binding.textViewAddContacts.visibility = GONE
                binding.textViewSelectAll.visibility = VISIBLE
            } else {
                binding.textViewAddContacts.visibility = VISIBLE
                binding.textViewSelectAll.visibility = GONE
            }
            addSwipeToDeleteFeature()
        }

        override fun onItemClickedChooseMode(userWithState: UserWithState) {
            viewModel.chooseUserChecked(userWithState)
        }


    }

    private var backgroundColorSelector: BackgroundColorSelector? = object : BackgroundColorSelector {

        override fun setBackgroundSelectionDrawable(): Drawable? =
            AppCompatResources.getDrawable(requireContext(), R.drawable.grey_medium_rounded_corner)

        override fun setBackgroundDefaultDrawable(): Drawable? =
            AppCompatResources.getDrawable(requireContext(), R.drawable.medium_rounded_corner)
    }


    private val adapterContacts: ContactsAdapter by lazy {
        ContactsAdapter(actionListener!!, backgroundColorSelector!!)
    }

    private val requestReadContactsPermissionLauncher: ActivityResultLauncher<String> by lazy {
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) {
            if (it) {
                setupRecyclerView()
            } else {
                showPermissionDeniedDialog()
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (context?.checkSelfPermission(READ_CONTACTS) != PackageManager.PERMISSION_GRANTED &&
            !shouldShowRequestPermissionRationale(READ_CONTACTS)
        ) {
            requestReadContactsPermission()
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    override fun onStart() {
        super.onStart()
        if (context?.checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED)
            setupRecyclerView()
    }



    override fun onDestroyView() {
        requestReadContactsPermissionLauncher.unregister()
        actionListener = null
        backgroundColorSelector = null
        if (viewModel.getCurrentMode()) {
            viewModel.changeMode()
            binding.floatingActionDeletionButton.visibility = GONE
        }
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
                val userWithState = viewModel.contactsLiveData.value?.get(position)
                viewModel.deleteUser(userWithState)
                showUndoSnackbar(userWithState?.user, position)

            }

            override fun isItemViewSwipeEnabled(): Boolean {
                return !viewModel.getCurrentMode()
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

        binding.floatingActionDeletionButton.setOnClickListener {
            viewModel.deleteCheckedUsers()
            it.visibility = GONE
            binding.textViewAddContacts.visibility = VISIBLE
            binding.textViewSelectAll.visibility = GONE
        }

        binding.textViewSelectAll.setOnClickListener {
            viewModel.selectAllUsers()
        }
    }


    private fun setObservers() {
        viewModel.contactsLiveData.observe(viewLifecycleOwner) {
            adapterContacts.submitList(it)
        }
    }


    private fun getDeletionAlertDialogListener(userWithState: UserWithState): DialogInterface.OnClickListener {
        return DialogInterface.OnClickListener { _, which ->
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> {
                    val userPosition = viewModel.getUserPosition(userWithState)
                    viewModel.deleteUser(userWithState)
                    showUndoSnackbar(userWithState.user, userPosition)
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
