package com.example.converter

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.converter.adapters.Information

class MainViewModel: ViewModel() {
    val liveDataCurrent = MutableLiveData<Information>()
    val liveDataList = MutableLiveData<Information>()
}