package com.example.pushnotificationass

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_main2.*
import org.json.JSONObject

class MainActivity2 : AppCompatActivity() {

   private lateinit var token:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)



        txt_signUp.setOnClickListener {
            var i = Intent(this,MainActivity::class.java)
            startActivity(i)
        }
        goBtn.setOnClickListener {
                  getToken()
                checkLogin()

        }
    }



    fun checkLogin(){
        val url="https://mcc-users-api.herokuapp.com/login"
        val queue= Volley.newRequestQueue(this)
        val request = object : StringRequest(Request.Method.POST,url,
                Response.Listener { response ->
                    val data=  JSONObject(response)
                    Log.e("Login","$data")
                    try {
                        var i = Intent(this,HomeActivity::class.java)
                        startActivity(i)
                    }catch (e:Exception){
                        Log.e("error","$e")
                    }


                },
                Response.ErrorListener { error ->
                    Toast.makeText(this,error.message, Toast.LENGTH_SHORT).show()
                }){
            override fun getParams(): MutableMap<String, String> {
                var params = HashMap<String, String>()
                params["email"] = edLoginEmail.text.toString()
                params["password"] = edLoginPassword.text.toString()
                addRegistrationToken()
              //  Log.e("Login","$params")
                return params
            }
        }



        queue.add(request)

    }
    fun addRegistrationToken(){
        val url="https://mcc-users-api.herokuapp.com/add_reg_token"
        val queue= Volley.newRequestQueue(this)
        val request = object : StringRequest(Request.Method.PUT,url,
                Response.Listener { response ->
                    val data=  JSONObject(response)
                    Log.e("Token","$data")
                },
                Response.ErrorListener { error ->
                    Toast.makeText(this,error.message, Toast.LENGTH_SHORT).show()
                }){
            override fun getParams(): MutableMap<String, String> {
                var params = HashMap<String, String>()
                params["email"] = edLoginEmail.text.toString()
                params["password"] = edLoginPassword.text.toString()
                params["reg_token"] = token




                return params
            }

//
        }



        queue.add(request)

    }

    fun getToken(){
        FirebaseMessaging.getInstance().token
                .addOnCompleteListener {task ->
                    if (!task.isSuccessful) {
                        Log.w("TAG", "Fetching FCM registration token failed", task.exception)
                    }
                     token = task.result.toString()
                }
    }
}

