package com.example.retrofitviacep

import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.*

interface RetrofitService {
    //Método que será responsável por chamar a API
    // https://viacep.com.br/ws/06600025/json/
    @GET("{CEP}/json/")
    fun getCEP (@Path("CEP") cep: String) : Call<Cep>

    // retorno da chamada da  api retorna uma lista de cep
    @GET("{uf}/{cidade}/{logradouro}/json/")
    fun getCEPByLogradouro(@Path("uf") uf: String,
                           @Path("cidade")cidade:String,
                           @Path("logradouro") logradouro:String) : Call<List<Cep>>


    @POST("/ceps")
        fun  gravarCep(@Body cep: Cep) : Call<Cep>

    @DELETE("/ceps/{id}")
    fun excluir(@Path("id") id: Int){

    }



    @POST("clientes")
    fun gravarCliente(@Body cliente : Cliente) : Call<Cliente>
}

