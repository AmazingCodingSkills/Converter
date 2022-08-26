package com.example.converter

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.converter.adaptersLatest.Response

class MainViewModel: ViewModel() {
    val liveDataCurrent = MutableLiveData<Response>()
    val liveDataList = MutableLiveData<Response>()
}