package com.example.examenxmlacs.Dao

import android.content.Context
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.examenxmlacs.Alimento
import com.example.examenxmlacs.Ingrediente
import com.example.examenxmlacs.Receta
import org.simpleframework.xml.core.Persister
import java.io.*

class DaoAssets(private val context: Context) {
    var ingredientes = mutableListOf<Ingrediente>()

    fun procesarArchivoAssetsXML() {
        val serializer = Persister()
        var inputStream: InputStream? = null
        var reader: InputStreamReader? = null

        try {
            inputStream = context.assets.open("recetas.xml")
            reader = InputStreamReader(inputStream)
            val ingredientesListType = serializer.read(Receta::class.java, reader, false)
            ingredientes.addAll(ingredientesListType.ingrediente)
            ingredientes.forEach(){
                Log.d("assetsXMLIng", "Ingrediente: ${it.alimento} Cantidad: ${it.cantidad}")
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

    fun addIngrediente(ingrediente: Ingrediente) {
        try {
            val serializer = Persister()
            ingredientes.add(ingrediente)
            val ingredientesList = Receta(ingredientes)
            val outputStream = context.openFileOutput("recetas.xml", AppCompatActivity.MODE_PRIVATE)
            serializer.write(ingredientesList, outputStream)
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
            val ingredientesList = serializer.read(Receta::class.java, inputStream)
            ingredientes.clear() //evitar duplicar datos, limpiamos lista de ingredientes, no el archivo interno
            ingredientes.addAll(ingredientesList.ingrediente)
            ingredientes.forEach(){
                Log.d("assetsXML-interno", "Ingrediente: ${it.alimento} Cantidad: ${it.cantidad}")
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