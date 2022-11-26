package com.elmohandes.task2grand.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.elmohandes.task2grand.R
import com.elmohandes.task2grand.adapter.listeners.ArticleDetailsListener
import com.elmohandes.task2grand.databinding.ArticleDetailsItemBinding
import com.elmohandes.task2grand.model.DataX

class ArticlesDetailsAdapter(
    private val context: Context,
    private val articlesDetails : List<DataX>,
    private val listener: ArticleDetailsListener
)
    : Adapter<ArticlesDetailsAdapter.ArticlesDetailsVH>() {

    lateinit var titleAnimation: Animation

    inner class ArticlesDetailsVH(itemView: View) : ViewHolder(itemView){
        val binding : ArticleDetailsItemBinding = ArticleDetailsItemBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticlesDetailsVH {
        return ArticlesDetailsVH(LayoutInflater.from(context).inflate(
            R.layout.article_details_item,parent, false
        ))
    }

    override fun onBindViewHolder(holder: ArticlesDetailsVH, position: Int) {
        val data = articlesDetails[position]
        titleAnimation = AnimationUtils.loadAnimation(context,R.anim.title_anim)
        holder.binding.articleDetailsTitle.animation = titleAnimation
        holder.binding.articleDetailsTitle.text = data.title
        if (data.all_awardings.isEmpty()){
            holder.binding.articleDetailsImg.visibility = View.GONE
        }else{
            holder.binding.articleDetailsImg.visibility = View.VISIBLE
            Glide.with(context).load(data.all_awardings[0].icon_url)
                .placeholder(R.drawable.placeholder).into(holder.binding.articleDetailsImg)
        }

        holder.itemView.setOnClickListener {
            listener.onClick(articlesDetails[position])
        }

    }

    override fun getItemCount(): Int {
        return articlesDetails.size
    }

}