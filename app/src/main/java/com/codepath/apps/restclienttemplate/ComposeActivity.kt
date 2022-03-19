package com.codepath.apps.restclienttemplate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers

class ComposeActivity : AppCompatActivity() {

    lateinit var etCompose:EditText
    lateinit var btnTweet:Button
    lateinit var client:TwitterClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compose)

        etCompose=findViewById(R.id.etTweetCompose)
        btnTweet=findViewById(R.id.btnTweet)
            client=TwitterApp.getRestClient(this)
        btnTweet.setOnClickListener{

            val tweetContent=etCompose.text.toString()

            if(tweetContent.isEmpty()){

                Toast.makeText(this,"empty",Toast.LENGTH_SHORT).show()

            }else

            if(tweetContent.length>140){
                Toast.makeText(this,"too long",Toast.LENGTH_SHORT)
                    .show()

            }else {

                client.publishTweet(tweetContent,object: JsonHttpResponseHandler(){

                    override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON?) {
                        TODO("Not yet implemented")
                    }

                    override fun onFailure(
                        statusCode: Int,
                        headers: Headers?,
                        response: String?,
                        throwable: Throwable?
                    ) {
                        TODO("Not yet implemented")
                    }




                })
            }
        }

    }
}