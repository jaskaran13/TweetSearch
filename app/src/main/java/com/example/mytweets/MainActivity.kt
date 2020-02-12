package com.example.mytweets

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mytweets.TweeterOverview.MyListAdapter
import com.example.mytweets.TweeterOverview.TweetViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var viewmodel: TweetViewModel
    var Progress___Bar: ProgressBar? = null
    var Search_text:EditText?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tweet_list = findViewById(R.id.tweeter_list) as RecyclerView
        viewmodel = ViewModelProviders.of(this).get(TweetViewModel::class.java)
        Progress___Bar = findViewById(R.id.progressBar)
        Search_text=findViewById(R.id.Search_text)
        Progress___Bar?.visibility = View.GONE

       tweet_list.layoutManager= LinearLayoutManager(this)
        val divider = DividerItemDecoration(tweeter_list.getContext(), DividerItemDecoration.VERTICAL)
        tweet_list.addItemDecoration(divider)




        Search_text?.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                viewmodel.OnClick(Search_text?.text.toString())
                Progress___Bar?.visibility = View.VISIBLE
                val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm?.hideSoftInputFromWindow(Search_text?.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
            }
            true
        }
        viewmodel.resLive.observe(this, Observer {
            Progress___Bar?.visibility = View.GONE
            tweet_list.setAdapter(MyListAdapter(it,this))
        })
    }






}

