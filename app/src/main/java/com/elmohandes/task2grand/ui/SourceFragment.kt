package com.elmohandes.task2grand.ui

import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.elmohandes.task2grand.R
import com.elmohandes.task2grand.databinding.FragmentSourceBinding

class SourceFragment : Fragment() {

    private lateinit var url:String
    private lateinit var binding: FragmentSourceBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_source, container, false)

        binding = FragmentSourceBinding.bind(view)
        url = arguments?.get("source_link").toString()

        binding.sourceWeb.settings.javaScriptEnabled = true
        binding.sourceWeb.webViewClient = object : WebViewClient(){
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                (activity as AppCompatActivity).supportActionBar!!.title = "loading..."
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                (activity as AppCompatActivity).supportActionBar!!.title = view!!.title
            }
        }


        (activity as AppCompatActivity).supportActionBar!!.title = url
        binding.sourceWeb.loadUrl(url)

        return view
    }
}