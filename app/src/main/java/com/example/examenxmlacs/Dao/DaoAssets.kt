package com.example.examenxmlacs.Dao

import android.content.Context
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.examenxmlacs.Ingrediente
import com.example.examenxmlacs.Receta
import com.example.examenxmlacs.Recetas
import org.simpleframework.xml.core.Persister
import java.io.*

class DaoAssets(private val context: Context) {
    var recetas = mutableListOf<Receta>()
    var receta:Receta= Receta()
    var ingredientes = mutableListOf<Ingrediente>()

    fun procesarArchivoAssetsXML() {
        val serializer = Persister()
        var inputStream: InputStream? = null
        var reader: InputStreamReader? = null

        try {
            inputStream = context.assets.open("recetas.xml")
            reader = InputStreamReader(inputStream)
            val recetaListType = serializer.read(Recetas::class.java, reader, false)
            recetas.addAll(recetaListType.receta)
            recetas.forEach(){
                Log.d("assetsXMLIng", "Receta: ${it.nombre} Ingrediente: ${it.ingredientes}")
            }
        } catch (e: Exception) {
            // Manejo de errores
            e.printStackTrace()
        } finally {
            // Cerrar inputStream y reader
            try {
                reader?.close()
                inputStream?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    fun copiarArchivoDesdeAssets() {
        val nombreArchivo = "recetas.xml"
        val archivoEnAssets = context.assets.open(nombreArchivo)
        val archivoInterno = context.openFileOutput(nombreArchivo, AppCompatActivity.MODE_PRIVATE)//tener en cuenta el context y AppCompatActivity

        archivoEnAssets.copyTo(archivoInterno)
        archivoEnAssets.close()
        archivoInterno.close()

    }

    fun addIngrediente(receta: Receta, ingrediente: Ingrediente) {
        try {
            val serializer = Persister()
            ingredientes.add(receta,  ingrediente)
            val recetaList = Recetas(recetas)
            val outputStream = context.openFileOutput("recetas.xml", AppCompatActivity.MODE_PRIVATE)
            serializer.write(recetaList, outputStream)
            outputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun ProcesarArchivoXMLInterno() {
        val nombreArchivo = "recetas.xml"
        val serializer = Persister()

        try {
            // Abrir el archivo para lectura
            val file = File(context.filesDir, nombreArchivo)
            val inputStream = FileInputStream(file)
            val recetaListType = serializer.read(Recetas::class.java, inputStream)
            recetas.clear() //evitar duplicar datos, limpiamos lista de ingredientes, no el archivo interno
            recetas.addAll(recetaListType.receta)
            recetas.forEach(){
                Log.d("assetsXML-interno", "Receta: ${it.nombre} Ingrediente: ${it.ingredientes}")

            }
            inputStream.close()


        } catch (e: Exception) {

            e.printStackTrace()
        }
    }

    fun vaciarArchivoInterno() { //vaciamos el archivo interno
        val nombreArchivo = "recetas.xml"

        // Abre el archivo interno para escritura (esto eliminará el contenido existente)
        val archivoInternoEscritura =
            context.openFileOutput(nombreArchivo, AppCompatActivity.MODE_PRIVATE)
        archivoInternoEscritura.write("".toByteArray())

        // Cierra el archivo
        archivoInternoEscritura.close()
    }
}