package com.elmohandes.task2grand.ui

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.elmohandes.task2grand.R
import com.elmohandes.task2grand.adapter.ArticlesDetailsAdapter
import com.elmohandes.task2grand.adapter.listeners.ArticleDetailsListener
import com.elmohandes.task2grand.api.RetrofitInstance
import com.elmohandes.task2grand.databinding.FragmentMainBinding
import com.elmohandes.task2grand.model.Children
import com.elmohandes.task2grand.model.DataX
import com.elmohandes.task2grand.model.RedditModel
import com.elmohandes.task2grand.mvvm.ArticleViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainFragment : Fragment(), ArticleDetailsListener {

    lateinit var binding: FragmentMainBinding
    lateinit var retrofitInstance: RetrofitInstance
    lateinit var adapter: ArticlesDetailsAdapter
    lateinit var childrenList: ArrayList<Children>
    lateinit var viewModel: ArticleViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        binding = FragmentMainBinding.bind(view)

        retrofitInstance = RetrofitInstance()
        childrenList = ArrayList()

        viewModel = ViewModelProvider(this)[ArticleViewModel::class.java]

        if (!checkInternetConnection()){
            Toast.makeText(context,"No Internet Connection",Toast.LENGTH_SHORT).show()
        }

        showdArticles()
        return view
    }

    private fun checkInternetConnection(): Boolean {
        val connection = requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as
                ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            val activeNetwork = connection.activeNetwork ?: return false
            val capabilities = connection.getNetworkCapabilities(activeNetwork)
            return when{
                capabilities!!.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> return true

                else -> {
                    Toast.makeText(context,"No Internet Connection",Toast.LENGTH_SHORT).show()
                    return false
                }
            }
        }else{
            connection.activeNetworkInfo.run {
                return when (this!!.type){
                    ConnectivityManager.TYPE_WIFI -> return true
                    ConnectivityManager.TYPE_MOBILE -> return true
                    ConnectivityManager.TYPE_ETHERNET -> return true

                    else -> {
                        Toast.makeText(context,"No Internet Connection",Toast.LENGTH_SHORT).show()
                        return false
                    }

                }
            }
        }

    }

    private fun showdArticles() {
        viewModel.getArticles.observe(viewLifecycleOwner) {

            if (it.isEmpty()){
                handlingData()
            }

            adapter = ArticlesDetailsAdapter(
                requireContext(),
                it,
                this@MainFragment
            )
            binding.mainRv.layoutManager = LinearLayoutManager(requireContext())
            binding.mainRv.adapter = adapter
        }
    }

    private fun handlingData() {
        RetrofitInstance.api.getAllArticles()!!
            .enqueue(object: Callback<RedditModel?> {

                override fun onResponse(call: Call<RedditModel?>,
                                        response: Response<RedditModel?>
                ) {
                    val data = response.body()
                    if (response.isSuccessful) {
                        Log.d("status","success")
                        childrenList = ArrayList(data!!.data!!.children)
                        for (i in 0 until childrenList.size){
                            Log.d("data",childrenList[i].data.title!!)
                            viewModel.insertArticle(childrenList[i].data)
                        }

                    }


                }

                override fun onFailure(call: Call<RedditModel?>, t: Throwable) {
                    Log.d("error", t.message.toString())
                }
            })
    }


    override fun onClick(dataX: DataX) {
        var bundle = bundleOf()
        if (dataX.all_awardings.isEmpty()){
            bundle = bundleOf(
                "article_url" to dataX.url,
                "article_title" to dataX.title,
                "article_name" to dataX.name,
                "article_selftext" to dataX.selftext,
            )
        }else{
            bundle = bundleOf(
                "article_url" to dataX.url,
                "article_title" to dataX.title,
                "article_name" to dataX.name,
                "article_selftext" to dataX.selftext,
                "article_img" to dataX.all_awardings[0].icon_url
            )
        }

        Navigation.findNavController(requireView()).navigate(R.id.action_main_to_article,bundle)

        //Toast.makeText(context, dataX.title, Toast.LENGTH_SHORT).show()
    }
}