package com.example.mypethelper

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mypethelper.DataClasses.Article

open class DataForElement : ViewModel() {
    val data : MutableLiveData<Article> by lazy {
        MutableLiveData<Article>()
    }
    val flag_view : MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
}