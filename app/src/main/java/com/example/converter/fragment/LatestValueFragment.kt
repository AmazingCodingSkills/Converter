package com.example.converter.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.converter.Common.Common
import com.example.converter.Interface.RetrofitServices
import com.example.converter.R
import com.example.converter.adapters.ConvertAdapter
import com.example.converter.adapters.Information
import com.example.converter.adapters.TestResponse
import com.example.converter.databinding.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LatestValueFragment : Fragment() {


    private lateinit var binding: FragmentLatestValueBinding
    private lateinit var adapter: ConvertAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLatestValueBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRcView()
    }

    private fun initRcView() = with(binding) {
        recyclerLatest.setHasFixedSize(true)
        recyclerLatest.layoutManager = LinearLayoutManager(activity)
        adapter = ConvertAdapter()
        recyclerLatest.adapter = adapter
        getAllInformationList()
    }

    private fun getAllInformationList() {
        Common.api.getInformationList().enqueue(object : Callback<TestResponse> {
            override fun onFailure(call: Call<TestResponse>, t: Throwable) {
                Log.d("tag", "${t}")
            }

            override fun onResponse(
                call: Call<TestResponse>,
                response: Response<TestResponse>
            ) {
                Log.d("tag", "OK 2")
                Log.d("tag", "${response.body()?.response}")

            }
        })
    }

    companion object {
        fun newInstance() = LatestValueFragment()
    }
}