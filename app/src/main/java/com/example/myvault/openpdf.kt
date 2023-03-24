package com.example.myvault

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ListView
import android.widget.Toast
import androidx.core.net.toUri
import com.example.myvault.R.*
import java.net.URLEncoder

class openpdf : AppCompatActivity() {
    private lateinit var listview1:ListView
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_openpdf)
        var url = intent.getStringExtra("Url")
       // var uri2 = "https://docs.google.com/gview?embedded=true&url="+url
        //Toast.makeText(this@openpdf, uri2, Toast.LENGTH_LONG).show()


        val webView = findViewById<WebView>(R.id.web_view_upload)
        webView.loadUrl(("https://docs.google.com/gview?embedded=true&url="+ URLEncoder.encode(url)))

        webView.settings.javaScriptEnabled = true
        webView.webViewClient = WebViewClient()
    }
}