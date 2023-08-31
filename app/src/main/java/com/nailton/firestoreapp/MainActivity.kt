package com.nailton.firestoreapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.nailton.firestoreapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        // iniciando constante para instanciar o firestore
        val db = Firebase.firestore

        // criando collecao
        val usersCollection =  db.collection("Users")

        // inserindo dados no documento, usando o hashMapOf que nos permite criar dados com chave e valor
        val user1 = hashMapOf(
            "name" to "Jhon",
            "lastName" to "Doe",
            "race" to "Vampire",
            "born" to 1664
        )

        val user2 = hashMapOf(
            "name" to "Van",
            "lastName" to "Helsing",
            "race" to "Lycan",
            "born" to 1664
        )

        // adcionando dados a colecao
        usersCollection.document("user1").set(user1)
        usersCollection.document("user2").set(user2)

        // acessando um documento especifico
        /*usersCollection.document("user1").get()
            .addOnSuccessListener { it ->
                if (it != null) {
                    binding.dataView.text = it.data.toString()
                }
            }
            .addOnFailureListener {
                Toast.makeText(
                    this@MainActivity,
                    it.toString(),
                    Toast.LENGTH_LONG
                ).show()
            }*/

        // deletando documento especifico
        usersCollection.document("user1")
            .delete()
            .addOnSuccessListener {
                Toast.makeText(
                    this@MainActivity,
                    "Documento Deletado",
                    Toast.LENGTH_LONG
                ).show()
            }
            .addOnFailureListener {
                Toast.makeText(
                    this@MainActivity,
                    "Erro ao deletar documento",
                    Toast.LENGTH_LONG
                ).show()
            }

        // atualizando um campo do documento
        usersCollection.document("user2")
            .update("race", "Monster Hunter")
            .addOnSuccessListener {
                Toast.makeText(
                    this@MainActivity,
                    "Campo atualizado",
                    Toast.LENGTH_LONG
                ).show()
            }
            .addOnFailureListener {
                Toast.makeText(
                    this@MainActivity,
                    "Erro ao atualizar documento",
                    Toast.LENGTH_LONG
                ).show()
            }

        // atualizando mais de um campo do documento por vez
        usersCollection.document("user2")
            .update("race", "Lycan", "name", "Lucian", "lastName", "Monster")
            .addOnSuccessListener {
                Toast.makeText(
                    this@MainActivity,
                    "Campos atualizado",
                    Toast.LENGTH_LONG
                ).show()
            }
            .addOnFailureListener {
                Toast.makeText(
                    this@MainActivity,
                    "Erro",
                    Toast.LENGTH_LONG
                ).show()
            }

        // acessando todos os documentos de uma colecao
        var allDocuments: String = ""
        usersCollection.get()
            .addOnSuccessListener {
                for (document in it) {
                    Log.i("TAGY", document.data.toString())
                    // utilizamos uma variavel que guardara os dados para deopois mostramos
                    // na tela para noa ocorrer sobreposicao dos objetos
                    allDocuments += document.data.toString()
                    allDocuments += ","
                }
                binding.dataView.text = allDocuments
            }
    }
}