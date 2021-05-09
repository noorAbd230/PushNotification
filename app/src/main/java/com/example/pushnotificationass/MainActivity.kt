package com.example.pushnotificationass

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONException
import org.json.JSONObject
import java.io.UnsupportedEncodingException


class MainActivity : AppCompatActivity() {
    lateinit var fName:String
    lateinit var sName:String
    lateinit var email:String
    lateinit var password:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
         fName = edFirstName.text.toString()
         email = edTxtEmail.text.toString()
         password = edTxtPassword.text.toString()
         sName = edSecondName.text.toString()
        saveBtn.setOnClickListener {

            addUsers()

        }
        txt_login.setOnClickListener {
            var i = Intent(this,MainActivity2::class.java)
            startActivity(i)
        }

    }

    private fun addUsers(){
        val url="https://mcc-users-api.herokuapp.com/add_new_user"
        val queue= Volley.newRequestQueue(this)
        val request = object : StringRequest(Request.Method.POST,url,
                Response.Listener { response ->
                    val data=  JSONObject(response)
                    Log.e("Add User","$data")
                },
                Response.ErrorListener { error ->
                    Toast.makeText(this,error.message, Toast.LENGTH_SHORT).show()
                }){
            override fun getParams(): MutableMap<String, String> {
                var params = HashMap<String, String>()
                params["firstName"] = edFirstName.text.toString()
                params["secondName"] = edSecondName.text.toString()
                params["email"] = edTxtEmail.text.toString()
                params["password"] = edTxtPassword.text.toString()
              // Log.e("Add User","$params")
                return params
            }

        }



        queue.add(request)

  }}