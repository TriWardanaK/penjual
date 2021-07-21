package com.example.penjual.view

import android.annotation.SuppressLint
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.*
import androidx.core.content.res.ResourcesCompat
import com.example.penjual.R
import com.example.receiver.ConnectivityReceiver
import kotlinx.android.synthetic.main.activity_web_view.*
import www.sanju.motiontoast.MotionToast

class WebViewActivity : AppCompatActivity(), ConnectivityReceiver.ConnectivityReceiverListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        loading.max = 100
        settings()
        loadWebsite()

        registerReceiver(ConnectivityReceiver(), IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

        refreshLayout.setOnRefreshListener {
            loading.max = 100
            settings()
            loadWebsite()

            registerReceiver(ConnectivityReceiver(), IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun settings(){
        val webSettings = webView.settings
        webSettings.javaScriptEnabled = true
        webSettings.allowContentAccess = true
        webSettings.useWideViewPort = true
        webSettings.loadsImagesAutomatically = true
        webSettings.cacheMode = WebSettings.LOAD_NO_CACHE
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH)
        webSettings.setEnableSmoothTransition(true)
        webSettings.domStorageEnabled = true
    }

    private fun loadWebsite(){
        val website = resources.getString(R.string.link_upload)

        if(Build.VERSION.SDK_INT >= 21) {
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null)
        }else{
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null)
        }
        webView.webChromeClient = object: WebChromeClient(){
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                loading.visibility = View.VISIBLE
                loading.progress = newProgress
                if(newProgress == 100){
                    loading.visibility = View.GONE

                }
                super.onProgressChanged(view, newProgress)
            }
        }
        webView.webViewClient = object: WebViewClient(){
            override fun shouldOverrideUrlLoading(view: WebView?, URL: String?): Boolean {
                view?.loadUrl(URL)
                loading.visibility = View.VISIBLE
                return true
            }
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    view?.loadUrl(request?.url.toString())
                }
                loading.visibility = View.VISIBLE
                return true
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                loading.visibility = View.GONE
            }
        }

        webView.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
        webView.loadUrl(website)
    }

    override fun onBackPressed() {
        if(webView.canGoBack()){
            webView.goBack()
        }else{
            super.onBackPressed()
        }
    }

    //broadcast network receiver
    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        showNetworkMessage(isConnected)
    }

    override fun onResume() {
        super.onResume()
        ConnectivityReceiver.connectivityReceiverListener = this
    }

    private fun showNetworkMessage(isConnected: Boolean) {
        if (!isConnected) {
            refreshLayout.isRefreshing = false
            MotionToast.createToast(this,"Please Check Your Network",
                MotionToast.TOAST_WARNING,
                MotionToast.GRAVITY_BOTTOM,
                MotionToast.LONG_DURATION,
                ResourcesCompat.getFont(this,R.font.helvetica_regular))
        } else {
            refreshLayout.isRefreshing = false
            MotionToast.createToast(this,"Internet Connected",
                MotionToast.TOAST_SUCCESS,
                MotionToast.GRAVITY_BOTTOM,
                MotionToast.SHORT_DURATION,
                ResourcesCompat.getFont(this,R.font.helvetica_regular))
        }
    }
}
