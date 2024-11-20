package com.app.atletismo.data.entities.usuarios

import com.app.atletismo.logic.data.dto.UsuarioDTO

data class Usuario(
    val id: Int,
    val nombres: String,
    val apellidos: String,
    val email: String,
    val rol: Rol
)

fun Usuario.getUsuario(): UsuarioDTO {
    return UsuarioDTO(
        id,
        nombres,
        apellidos,
        email,
        rol.id,
        rol.descripcion
    )
}
