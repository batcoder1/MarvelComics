package com.marvelapp.autentia.comics

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.widget.Toast
import com.marvelapp.autentia.comics.adapter.MyAdapter
import com.marvelapp.autentia.comics.data.remote.APIService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.apache.commons.codec.binary.Hex
import org.apache.commons.codec.digest.DigestUtils
import java.util.*
import com.amazonaws.mobile.config.AWSConfiguration
import com.amazonaws.mobile.auth.core.IdentityManager
import com.amazonaws.mobileconnectors.pinpoint.PinpointManager
import com.amazonaws.mobileconnectors.pinpoint.PinpointConfiguration




class MainActivity : AppCompatActivity() {

    lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: MyAdapter
    var toolbar: Toolbar? = null
    var _offset = 0
    var limit = 20
    val ApiServe by lazy {
        APIService.create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeApplication()
        setContentView(R.layout.activity_main)
        setUpRecyclerView()

    }

    private fun initializeApplication() {

        val awsConfig = AWSConfiguration(applicationContext)

        // If IdentityManager is not created, create it
        if (IdentityManager.getDefaultIdentityManager() == null) {
            val awsConfiguration = AWSConfiguration(applicationContext)
            val identityManager = IdentityManager(applicationContext, awsConfiguration)
            IdentityManager.setDefaultIdentityManager(identityManager)
        }

        // Register identity providers here.
        // With none registered IdentityManager gets unauthenticated AWS credentials


        val credentialsProvider = IdentityManager.getDefaultIdentityManager().credentialsProvider

        val pinpointConfig = PinpointConfiguration(applicationContext,
                credentialsProvider,
                awsConfig)

        val pinpointManager = PinpointManager(pinpointConfig)

        pinpointManager.sessionClient.startSession();


        // Choose a meaningful point in your apps lifecycle to mark the end of your session
        // pinpointManager.getSessionClient().stopSession();
        pinpointManager.analyticsClient.submitEvents()

    }
    private fun getComics(offset: Int, limit: Int): MarvelDataResponse {

        var comics = ComicDataWrapper(0, "", "", "", "", MarvelDataResponse(0, 0, 0, 0, ArrayList()))

        val params = connectionParameter()
        ApiServe.getComics(offset, limit, params.ts, params.apikey, params.hash)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result ->
                            comics = result
                            mAdapter.swap(result.data)
                            mAdapter.notifyDataSetChanged()

                        },
                        { error ->
                            Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
                            println(error.message)
                        }
                )
        _offset = comics.data.results.size
        return comics.data
    }

    fun setUpRecyclerView() {
        mRecyclerView = findViewById(R.id.RecView)
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = LinearLayoutManager(this)

        val MarvelComicsResponse: MarvelDataResponse = getComics(_offset, limit)
        val ArrayMarvelComic: ArrayList<Comic> = MarvelComicsResponse.results
        mAdapter = MyAdapter(ArrayMarvelComic)

        mRecyclerView.adapter = mAdapter

    }

    fun connectionParameter(): Params {
        val privateapi = "003c81343b52f0cf0722b0ea73ba1ee0f428ff19"
        val publickey = "5b23f1dbea4c2160ed035a817fd4fc38"
        val formatted = Calendar.getInstance().get(Calendar.MILLISECOND).toString()

        val key = formatted + privateapi + publickey
        val hash = String(Hex.encodeHex(DigestUtils.md5(key)))
        val params = Params(formatted, publickey, hash)
        return params
    }
//    override fun onPause() {
//        super.onPause()
//        disposable?.dispose()
//    }

}

