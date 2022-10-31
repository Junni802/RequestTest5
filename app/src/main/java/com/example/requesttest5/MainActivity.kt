package com.example.requesttest5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.requesttest5.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {
	val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(binding.root)

		binding.btnSearch.setOnClickListener {
			CoroutineScope(Dispatchers.IO).launch {
				try{
					val url = URL("http://192.168.0.29:8083/test/all_member_info.jsp?")
					val urlConnection = url.openConnection() as HttpURLConnection
					urlConnection.requestMethod = "GET"

					if(urlConnection.responseCode == HttpURLConnection.HTTP_OK){
						val streamReader = InputStreamReader(urlConnection.inputStream)
						val buffered = BufferedReader(streamReader)
						var result = ""
						while (true){
							result = buffered.readLine()
							if(result != "")	break
						}	// member_info.jsp의 실행결과를 받아와 result에 저장
						buffered.close()
						urlConnection.disconnect()
						urlConnection.disconnect()
						launch {
							var result1 = result.substring(1)
							var list = result1.split(",")
							var content = ""
							for(i in 0 until list.size){
								if(i == 0){
									content = "${list[i]}"
								}else{
									content += "\n${list[i]}"
								}
							}
							binding.txtList.text = content
						}
					}
				}catch (e: Exception){
					Log.d("request1", "오류 발생")
					e.printStackTrace()
				}
			}
		}
	}
}