package com.elmohandes.task2grand.ui

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.elmohandes.task2grand.R
import com.elmohandes.task2grand.databinding.FragmentArticleBinding


class ArticleFragment : Fragment() {

    lateinit var binding: FragmentArticleBinding
    lateinit var title : String
    lateinit var url : String
    lateinit var name : String
    lateinit var desc : String
    lateinit var imgLink : String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_article, container, false)

        binding = FragmentArticleBinding.bind(view)

        title = arguments?.get("article_title").toString()
        url = arguments?.get("article_url").toString()
        name = arguments?.get("article_name").toString()
        desc = arguments?.get("article_selftext").toString()
        imgLink = arguments?.get("article_img").toString()

        //Toast.makeText(context, imgLink, Toast.LENGTH_SHORT).show()

        if (imgLink == "null"){
            Log.d("img visibilty","gone")
            binding.articleImg.visibility = View.GONE
        }else{
            Glide.with(requireContext()).load(imgLink).into(binding.articleImg)
            binding.articleImg.visibility = View.VISIBLE
        }

        binding.articleDesc.text = desc

        makeClickableText()

        (activity as AppCompatActivity).supportActionBar!!.title = title

        return view
    }

    private fun makeClickableText() {
        val content = binding.articleLink.text
        val spannable = SpannableString(content)
        val foregroundColorSpan = ForegroundColorSpan(Color.BLUE)
        val clickableSpan = object :ClickableSpan(){
            override fun onClick(p0: View) {
                sendToSource()
            }
        }

        spannable.setSpan(clickableSpan,0,content.length,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannable.setSpan(foregroundColorSpan,0,content.length,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.articleLink.text = spannable
        binding.articleLink.movementMethod = LinkMovementMethod.getInstance()

    }

    private fun sendToSource() {
        val bundle = bundleOf(
            "source_link" to url
        )
        Navigation.findNavController(requireView()).navigate(R.id.action_article_to_source,bundle)
    }

}