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
    var recetas: MutableList<Receta> = mutableListOf()
    var ingredientes: MutableList<Ingrediente> = mutableListOf()
    var alimentos: MutableList<Alimento> = mutableListOf()

    @Throws(SAXException::class)
    override fun startDocument() {
        cadena.clear()
        recetas.clear()
        ingredientes.clear()
        alimentos.clear()
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

    }

    @Throws(SAXException::class)
    override fun characters(ch: CharArray, start: Int, length: Int) {
        cadena.append(ch, start, length)
        Log.d("SAX", "guardando en una cadena $cadena")
    }

    @Throws(SAXException::class)
    override fun endElement(uri: String, nombreLocal: String, nombre: String) {
        when (nombre) {
            "proteinas" -> {
                if (cadena.isNotEmpty()) {
                    alimento?.proteinas = Proteinas(cadena.toString().toInt())
                }
            }
            "grasas" -> {
                if (cadena.isNotEmpty()) {
                    alimento?.grasas = Grasas(cadena.toString().toInt())
                }
            }
            "hidratos" -> {
                if (cadena.isNotEmpty()) {
                    alimento?.hidratos = Hidratos(cadena.toString().toInt())
                }
            }
            "cantidad" -> ingrediente?.cantidad = cadena.toString()
            "alimento" -> {
                ingrediente?.alimento = alimento
                alimento = null
            }
            "ingrediente" -> {
                ingrediente?.let { ingredientes.add(it) }
                alimento = null // Restablecer alimento a null después de agregarlo al ingrediente
            }
        }

        Log.d("SAX", "cerrando elemento $nombre $nombreLocal")
    }

    @Throws(SAXException::class)
    override fun endDocument() {
        println("endDocument")
    }
}