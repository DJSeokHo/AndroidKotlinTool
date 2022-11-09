package com.swein.androidkotlintool.main.examples.newsystemphotopicker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.swein.androidkotlintool.R

class PhotoPickerSelectorFragment(
    private val onSingle: (sheet: BottomSheetDialogFragment) -> Unit,
    private val onMultiple: (sheet: BottomSheetDialogFragment) -> Unit
) : BottomSheetDialogFragment() {

    private lateinit var rootView: View

    private lateinit var buttonSingle: Button
    private lateinit var buttonMultiple: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        rootView = inflater.inflate(R.layout.fragment_photo_picker_selector, container)
        findView()
        setListener()
        return rootView
    }

    private fun findView() {
        buttonSingle = rootView.findViewById(R.id.buttonSingle)
        buttonMultiple = rootView.findViewById(R.id.buttonMultiple)
    }

    private fun setListener() {
        buttonSingle.setOnClickListener {
            onSingle(this)
        }

        buttonMultiple.setOnClickListener {
           onMultiple(this)
        }
    }
}
