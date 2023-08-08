package puc.facilita.facilitabackend.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional
import puc.facilita.facilitabackend.entity.AnuncioEntity
import puc.facilita.facilitabackend.entity.UsuarioEntity
import java.util.Optional


interface AnuncioRepository: JpaRepository<AnuncioEntity, Int> {

    fun findByUsuarioId(idUsuario: Int): List<Optional<AnuncioEntity>>

    fun findByNomeAndUsuario(nome: String, usuarioEntity: UsuarioEntity): Optional<AnuncioEntity>

}