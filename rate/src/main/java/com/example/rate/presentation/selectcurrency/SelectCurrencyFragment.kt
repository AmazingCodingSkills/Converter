package com.example.rate.presentation.selectcurrency

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.converter.core.ConverterApplication
import com.example.rate.databinding.FragmentSelectCurrencyBinding
import com.example.rate.di.DaggerSelectCurrencyComponent
import com.example.rate.di.SelectCurrencyComponent
import java.text.DecimalFormat

internal class SelectCurrencyFragment() : Fragment() {

    private lateinit var binding: FragmentSelectCurrencyBinding
    private val entryFormat = DecimalFormat("#####.####")
    private val component: SelectCurrencyComponent by lazy {
        DaggerSelectCurrencyComponent.factory()
            .create(((activity?.applicationContext as? ConverterApplication)?.appComponent!!))
    }
    private var argOne: String? = null
    private var argTwo: String? = null
    private var argThree: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSelectCurrencyBinding.inflate(inflater, container, false)
        argOne = arguments?.getString("reference")
        argTwo = arguments?.getString("base")
        argThree = arguments?.getString("value")
        binding.selectCurrency.text = argOne
        binding.baseCurrency.text = argTwo
        binding.valueSelectCurrency.text = entryFormat.format(argThree?.toDouble())
        return binding.root
    }

    private val viewModel: SelectCurrencyViewModel by viewModels {
        component.factorySelectCurrencyViewModel().create(argOne!!, argTwo!!)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnBackFromSelect.setOnClickListener {
            val fr = requireActivity().supportFragmentManager
            if (fr.backStackEntryCount > 0)
                fr.popBackStack()
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.state.collect { state ->
                when (state) {
                    is SelectCurrencyViewState.Content -> {}
                }
            }
        }
    }
}
