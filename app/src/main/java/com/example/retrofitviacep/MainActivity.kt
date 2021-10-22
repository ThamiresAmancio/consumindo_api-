package com.example.retrofitviacep

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {


    lateinit var buttonBuscar: Button
    lateinit var textViewEndereco : TextView
    lateinit var editTextCep : EditText

    lateinit var buttonBuscarRua: Button
    lateinit var editTextLogradouro : EditText
    lateinit var editTextCidade: EditText
    lateinit var editTextUf: EditText
    lateinit var textViewRua : TextView
    lateinit var  rvCeps : RecyclerView
    lateinit var  cepsAdapter: CepsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        buttonBuscar = findViewById(R.id.buttonBuscar)
        textViewEndereco = findViewById(R.id.textViewEndereco)
        editTextCep = findViewById(R.id.editTextCep)


        buttonBuscarRua = findViewById(R.id.buttonBuscarRua)
        editTextLogradouro = findViewById(R.id.editTextRua)
        editTextCidade = findViewById(R.id.editTextCidade)
        editTextUf = findViewById(R.id.editTextEstado)

        rvCeps = findViewById(R.id.rv_ceps)
        cepsAdapter = CepsAdapter(this)


//        rvCeps.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
//        rvCeps.adapter = cepsAdapter

        rvCeps.layoutManager = GridLayoutManager(this,2)
        rvCeps.adapter = cepsAdapter


        buttonBuscar.setOnClickListener {

            //recebe a instância do retrofit
            //obter uma instânica da conexão com o backend
            val remote = RetrofitFactory().retrofitService()

            //criar uma chamada para o endpoint/cep/json

            val call: Call<Cep> = remote.getCEP(editTextCep.text.toString())

            //executar a chamada para a api
            call.enqueue(object : Callback<Cep>{
                override fun onResponse(call: Call<Cep>, response: Response<Cep>) {
                    val ceps = response.body()


                }

                override fun onFailure(call: Call<Cep>, t: Throwable) {

                    Log.i("cep",t.message.toString())
                }

            })
        }

        buttonBuscarRua.setOnClickListener {

            //recebe a instância do retrofit
            //obter uma instânica da conexão com o backend
            val remote = RetrofitFactory().retrofitService()

            //criar uma chamada para o endpoint/cep/json

            val call : Call<List<Cep>> = remote.getCEPByLogradouro(editTextUf.text.toString(), editTextCidade.text.toString(),editTextLogradouro.text.toString())

            //executar a chamada para a api
            call.enqueue(object  : Callback<List<Cep>>{
                override fun onResponse(call: Call<List<Cep>>, response: Response<List<Cep>>) {
                    val cepsList = response.body()
                    cepsAdapter.updateListaCeps(cepsList!!)
//                    textViewRua.text = cep.toString()
                }
                override fun onFailure(call: Call<List<Cep>>, t: Throwable) {
                    Log.i("cep",t.message.toString())
                }
            })
        }
    }
}