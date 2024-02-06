package com.example.examenxmlacs.Dao

import android.content.Context
import android.util.Log
import com.example.examenxmlacs.RecetaHandlerXML
import javax.xml.parsers.SAXParserFactory

class DaoSax (private val context: Context) {
    fun procesarArchivoAssetsXMLSAX() {
        try {
            val factory = SAXParserFactory.newInstance()
            val parser = factory.newSAXParser()
            val handler = RecetaHandlerXML()
            val inputStream = context.assets.open("recetas.xml")
            parser.parse(inputStream, handler)
            handler.ingredientes.forEach(){
                Log.d("XMLSAX", "Ingrediente: ${it.nombre}")
            }



        } catch (e: Exception) {
            // Maneja las excepciones imprimiendo la traza en la consola
            e.printStackTrace()
        }
    }
}