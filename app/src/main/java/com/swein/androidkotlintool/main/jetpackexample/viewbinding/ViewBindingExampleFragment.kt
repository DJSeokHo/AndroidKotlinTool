package com.swein.androidkotlintool.main.jetpackexample.viewbinding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.swein.androidkotlintool.databinding.FragmentViewBindingExampleBinding
import com.swein.androidkotlintool.framework.util.log.ILog

class ViewBindingExampleFragment : Fragment() {

    companion object {

        private const val TAG = "ViewBindingExampleFragment"

        @JvmStatic
//        fun newInstance() =
//            ViewBindingExampleFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
//            }

        fun newInstance() = ViewBindingExampleFragment()
    }

    private var _binding: FragmentViewBindingExampleBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
//        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentViewBindingExampleBinding.inflate(inflater, container, false)

        binding.textView.text = "123"

        createViewHolder()
        return binding.root
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_view_binding_example, container, false)
    }

    private fun createViewHolder() {

        context?.let {
            val viewBindingExampleViewHolder = ViewBindingExampleViewHolder(it)
            viewBindingExampleViewHolder.delegate = object:
                ViewBindingExampleViewHolder.ViewBindingExampleViewHolderDelegate {
                override fun onClick() {
                    ILog.debug(TAG, "view holder click")
                }
            }

            binding.frameLayout.addView(viewBindingExampleViewHolder.getView())
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        ILog.debug(TAG, "onDestroyView ${_binding == null}")
    }
}