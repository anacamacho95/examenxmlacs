package com.example.examenxmlacs

import android.util.Log
import org.xml.sax.Attributes
import org.xml.sax.SAXException
import org.xml.sax.helpers.DefaultHandler

class RecetaHandlerXML :DefaultHandler() {
    private val cadena= StringBuilder()
    private var receta : Receta? = null
    private var ingrediente : Ingrediente? = null
    private var alimento : Alimento? = null
    private var proteinas : Proteinas? = null
    private var grasas : Grasas? = null
    private var hidratos : Hidratos? = null
    var recetas: MutableList<Receta> = mutableListOf()
    var ingredientes: MutableList<Ingrediente> = mutableListOf()
    var alimentos: MutableList<Alimento> = mutableListOf()

    @Throws(SAXException::class)
    override fun startDocument() {
        cadena.clear()
        recetas = mutableListOf()
        ingredientes = mutableListOf()
        alimentos = mutableListOf()
        Log.d("SAX", "abriendo el documento")
    }

    @Throws(SAXException::class)
    override fun startElement(uri: String, nombreLocal: String, nombre: String, attributes: Attributes) {
        cadena.setLength(0)
        if (nombre == "receta") {
            receta = Receta()
            ingrediente?.nombre = attributes.getValue("nombre")
        }
        Log.d("SAX", "abriendo etiqueta receta")

        if (nombre == "ingrediente") {
            ingrediente = Ingrediente()
            ingrediente?.nombre = attributes.getValue("nombre")
        }
        Log.d("SAX", "abriendo etiqueta ingrediente")

        if (nombre=="alimento"){
            alimento = Alimento()
        }
        Log.d("SAX", "abriendo etiqueta alimento")

        if (nombre=="proteinas"){
            proteinas = Proteinas()
        }
        Log.d("SAX", "abriendo etiqueta proteinas")
        if (nombre=="grasas"){
            grasas = Grasas()
        }
        Log.d("SAX", "abriendo etiqueta proteinas")
    }

    @Throws(SAXException::class)
    override fun characters(ch: CharArray, start: Int, length: Int) {
        cadena.append(ch, start, length)
        Log.d("SAX", "guardando en una cadena $cadena")
    }

    @Throws(SAXException::class)
    override fun endElement(uri: String, nombreLocal: String, nombre: String) {
        when (nombre) {
            "proteinas" -> alimento?.proteinas = cadena.toString().toInt()
            "grasas" -> alimento?.grasas = cadena.toString().toInt()
            "hidratos" -> alimento?.hidratos = cadena.toString().toInt()
            "alimento" -> alimentos.add(alimento!!)
            "cantidad" -> ingrediente?.cantidad = cadena.toString()
            "ingrediente" -> ingrediente?.let { ingredientes.add(it) }
        }

        Log.d("SAX", "cerrando elemento $nombre $nombreLocal")
    }

    @Throws(SAXException::class)
    override fun endDocument() {
        println("endDocument")
    }
}