package com.currency.converter.features.favorite

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.currency.converter.ConverterApplication
import com.example.converter.databinding.FragmentTabLayoutFavoritesAllBinding


class CurrenciesFragment : Fragment() {
    private lateinit var binding: FragmentTabLayoutFavoritesAllBinding
    private lateinit var adapterX: CurrenciesAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTabLayoutFavoritesAllBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerFavoriteAll.apply {
            layoutManager = LinearLayoutManager(activity)
            adapterX = CurrenciesAdapter { item ->
                Toast.makeText(activity, item.currencyName, Toast.LENGTH_SHORT)
                    .show()
            }


            /*adapterX = ConvertAdapterX {
                if (it.countries.let { true }){
                    Toast.makeText(activity, "${item.countries.first()}", Toast.LENGTH_SHORT)
                        .show()}
                else(it.countries.let { false })*/
                /*Toast.makeText(activity, "${item.countries.first()}", Toast.LENGTH_SHORT)
                    .show()*/
            binding.recyclerFavoriteAll.adapter = adapterX

        }
        val allCurrencys =
            ConverterApplication.ModelPreferencesManager.get<List<CurrencyItem>>("KEY_ONE")
                .orEmpty()
        Log.d("qwerty", "${allCurrencys::class.java}")
        adapterX.submitList(allCurrencys)
    } // совместить два списка + из шеред преференс

private operator fun Boolean.invoke(value: Any) {

}

companion object {
        fun newInstance() = CurrenciesFragment()
    }
}
/*{ item ->
                Toast.makeText(activity, "${item.countries.first()}", Toast.LENGTH_SHORT)
                    .show()
            } это в adterX = ConvertAdapterX()*/