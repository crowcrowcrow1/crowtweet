package com.codepath.apps.restclienttemplate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.apps.restclienttemplate.models.Tweet
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import org.json.JSONException

class TimelineActivity : AppCompatActivity() {

    lateinit var client:TwitterClient
    lateinit var rvTweets:RecyclerView
    lateinit var adapter:TweetsAdapter
    val tweets=ArrayList<Tweet>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timeline)

        client=TwitterApp.getRestClient(this)

        rvTweets=findViewById(R.id.rvTweets)
        adapter= TweetsAdapter(tweets)

        rvTweets.layoutManager=LinearLayoutManager(this)
        rvTweets.adapter=adapter

        populateHomeTimeline()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==R.id.compose) {
            val intent=Intent(this,ComposeActivity::class.java)
            startActivity(intent)
        }

        return super.onOptionsItemSelected(item)
    }


    fun populateHomeTimeline(){

        client.getHomeTimeline(object:JsonHttpResponseHandler(){

            override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                Log.i(TAG,"sucess")


                val jsonArray=json.jsonArray

                try {
                    val listofNewTweet = Tweet.fromJsonArray(jsonArray)
                    tweets.addAll(listofNewTweet)
                    adapter.notifyDataSetChanged()
                }catch(e:JSONException){
                    Log.e(TAG,"json excpetion$e")
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?
            ) {
               Log.i(TAG,"failed$statusCode")
            }


        })

    }
    companion object{

        val TAG="TimelineActivity"
    }

}