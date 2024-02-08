package com.example.examenxmlacs

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name="recetas")
data class Recetas (
    @field:ElementList(inline=true, entry="recetas")
    var receta: List<Receta> = mutableListOf()
)
@Root(name="receta")
data class Receta (
    @field:ElementList(inline=true, entry="ingrediente", required = false)
    var ingrediente: List<Ingrediente> = mutableListOf(),
    @field:Attribute(name="nombre", required = false)
    var nombre: String? = ""
)

@Root(name="ingrediente")
data class Ingrediente(
    @field:Element(name="alimento")
    var alimento: Alimento? = null,
    @field:Element(name="cantidad")
    var cantidad: String="",

    @field:Attribute(name="nombre")
    var nombre: String? = ""
){
    override fun toString(): String {
        return "\nAlimento: ${alimento.toString()} \nCantidad: $cantidad"
    }
}

@Root(name="alimento")
data class Alimento(
    @field:Element(name="proteinas")
    var proteinas: Proteinas? = null,
    @field:Element(name="grasas")
    var grasas: Grasas? = null,
    @field:Element(name="hidratos")
    var hidratos: Hidratos? = null,

){
    override fun toString(): String {
        return "\nProteinas: ${proteinas?.cantidad100g}\nGrasas: ${grasas?.cantidad100g}\nHidratos: ${hidratos?.cantidad100g}"
    }
}

@Root(name="proteinas")
data class Proteinas(
    @field:Attribute(name="cantidad100g")
    var cantidad100g: Int? = 0
){
    override fun toString(): String {
        return "Cantidad100g: $cantidad100g"
    }
}
@Root(name="grasas")
data class Grasas(
    @field:Attribute(name="cantidad100g")
    var cantidad100g: Int? = 0
){
    override fun toString(): String {
        return "Cantidad100g: $cantidad100g"
    }
}
@Root(name="hidrato")
data class Hidratos(
    @field:Attribute(name="cantidad100g")
    var cantidad100g: Int? = 0
){
    override fun toString(): String {
        return "Cantidad100g: $cantidad100g"
    }
}