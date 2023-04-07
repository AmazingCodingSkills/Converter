package com.example.rate.presentation.favourite.onlyfavourite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.converter.core.ConverterApplication
import com.example.rate.databinding.FragmentTabLayoutFavoritesBinding
import com.example.rate.di.DaggerOnlyFavouritesComponent
import com.example.rate.di.OnlyFavouritesComponent
import com.example.rate.presentation.favourite.CurrenciesAdapter


internal class OnlyFavoritesFragment : Fragment() {

    private lateinit var binding: FragmentTabLayoutFavoritesBinding
    private lateinit var adapterSelectedFavorite: CurrenciesAdapter


    private val component: OnlyFavouritesComponent by lazy {
        DaggerOnlyFavouritesComponent.factory()
            .create(((activity?.applicationContext as? ConverterApplication)?.appComponent!!))
    }

    private val viewModel: OnlyFavoritesViewModel by viewModels {
        component.onlyFavouritesViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTabLayoutFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.selectCurrencyRV.apply {
            layoutManager = LinearLayoutManager(activity)
            adapterSelectedFavorite = CurrenciesAdapter { item ->
                viewModel.handleAction(OnlyFavouritesViewAction.RemoveItem, item)
            }
            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                viewModel.state.collect { state ->
                    when (state) {
                        is OnlyFavouritesViewState.Content -> {
                            adapterSelectedFavorite.submitList(state.items)
                            binding.selectCurrencyRV.visibility = View.VISIBLE
                            binding.hintEmptyScreen.visibility = View.GONE
                        }
                        is OnlyFavouritesViewState.Empty -> {
                            binding.selectCurrencyRV.visibility = View.GONE
                            binding.hintEmptyScreen.visibility = View.VISIBLE
                        }
                        else -> {
                        }
                    }
                }
            }
            binding.selectCurrencyRV.adapter = adapterSelectedFavorite
            this.itemAnimator = null
        }

    }

    override fun onResume() {
        super.onResume()
        viewModel.reloadScreen()
    }
    companion object {
        fun newInstance() = OnlyFavoritesFragment()
    }
}
