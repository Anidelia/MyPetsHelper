package com.example.mypethelper

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ArticleFull : BottomSheetDialogFragment() {
    private val liveData: DataForElement by viewModels()
    var dialogView: View? = null
    private lateinit var dialog: Dialog
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_sheet_article_full, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Ваши дополнительные действия, если они необходимы при создании Bottom Sheet
        checkAndView()
    }

    // Переименовал метод для более точного отражения смысла
    private fun checkAndView() {
        if (liveData.flag_view.value == true) {
            viewInfoForArticle()
        }
    }

    private fun viewInfoForArticle() {
            dialogView = layoutInflater.inflate(R.layout.bottom_sheet_article_full, null)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(dialogView!!)
            dialog.getWindow()?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            );
            dialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow()?.setGravity(Gravity.BOTTOM);


            val image = dialogView?.findViewById<ViewPager2>(R.id.textImage)
            val title = dialogView?.findViewById<TextView>(R.id.textTitle)
            val content = dialogView?.findViewById<TextView>(R.id.textContent)

            title!!.text = liveData.data.value!!.title
            content!!.text = liveData.data.value!!.content


        }
    }

