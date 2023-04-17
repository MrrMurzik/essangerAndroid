package com.example.messengerAndroid.app.foundation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.messengerAndroid.app.data.result.*
import com.example.messengerAndroid.app.ui.auth.signUp.Error


abstract class BaseFragment<B : ViewBinding>(
    private val inflater: (LayoutInflater, ViewGroup?, Boolean) -> B) : Fragment() {

    private var _binding: B? = null
    protected val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = this.inflater.invoke(inflater, container, false)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun <T> renderResult(
        root: ViewGroup,
        result: Result<T>,
        onPending: () -> Unit,
        onSystemErrorResult: (Error) -> Unit,
        onActionErrorResult: (Error) -> Unit,
        onSuccess: (T) -> Unit
    ) {
        root.children.forEach {
            it.visibility = GONE
        }
        when (result) {
            is SuccessResult -> onSuccess(result.data)
            is SystemErrorResult -> onSystemErrorResult(result.error)
            is ActionErrorResult -> onActionErrorResult(result.error)
            is PendingResult -> onPending()
        }

    }




}
