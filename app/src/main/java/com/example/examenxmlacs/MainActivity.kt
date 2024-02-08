package com.example.examenxmlacs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.examenxmlacs.Dao.DaoAssets
import com.example.examenxmlacs.Dao.DaoSax

class MainActivity : AppCompatActivity() {
    var recetaIng = mutableListOf<Ingrediente>()
//    var recetaAli = mutableListOf<Alimento>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d("XMLSAX", "probando SAX")
        var daoSAX=DaoSax(applicationContext)
        daoSAX.procesarArchivoAssetsXMLSAX()
        Log.d("XMLSAX", "SAX terminado")

        var daoAssets=DaoAssets(applicationContext)
        daoAssets.procesarArchivoAssetsXML()
        Log.d("SimpleXML", "probando procesado con Simple XML Framework")

        daoAssets.copiarArchivoDesdeAssets()

        //añadiendo nuevo ingrediente
        val ingrediente=Ingrediente((Alimento((Proteinas(20)), Grasas(23),Hidratos(25))),"20","pavo")
        daoAssets.addIngrediente(ingrediente)
        daoAssets.ingredientes.forEach(){
            Log.d("ingPavo", it.nombre.toString() + it.toString())
        }

        Log.d("SimpleXMLinterno", "proceso XML interno antes de empezar")
        daoAssets.ProcesarArchivoXMLInterno()
        Log.d("SimpleXMLinterno", "proceso XML interno finalizado")


    }
}