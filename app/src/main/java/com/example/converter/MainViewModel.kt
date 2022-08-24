package com.example.converter

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.converter.adapters.Information
import com.example.converter.adapters.Response

class MainViewModel: ViewModel() {
    val liveDataCurrent = MutableLiveData<Response>()
    val liveDataList = MutableLiveData<Response>()
}