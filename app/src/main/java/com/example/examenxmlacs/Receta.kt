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
    @field:ElementList(inline=true, entry="ingrediente")
    var ingrediente: List<Ingrediente> = mutableListOf()
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
        return "Alimento: ${alimento.toString()} Cantidad: $cantidad"
    }
}

@Root(name="alimento")
data class Alimento(
    @field:Element(name="proteinas")
    var proteinas: Int?= null,
    @field:Element(name="grasas")
    var grasas: Int? = null,
    @field:Element(name="hidratos")
    var hidratos: Int? = null,

    @field:Attribute(name="cantidad100g")
    var cantidad100g: Int? = 0
){
    override fun toString(): String {
        return "Proteinas:$proteinas Grasas:$grasas Hidrato:$hidratos"
    }
}