package puc.facilita.facilitabackend.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.transaction.annotation.Transactional
import puc.facilita.facilitabackend.entity.UsuarioEntity
import puc.facilita.facilitabackend.entity.enum.StatusCliente
import java.time.LocalDateTime
import java.util.*


interface UsuarioRepository: JpaRepository<UsuarioEntity, Int> {

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE UsuarioEntity u set u.status = :status, u.dataDeExclusao = :dataDeExclusao where u.id = :id")
    fun updateStatusCliente(id: Int, status: StatusCliente = StatusCliente.DELETADO, dataDeExclusao: LocalDateTime = LocalDateTime.now()): Int

    fun findByEmail(email: String): Optional<UsuarioEntity>

    fun findByLocalizacao(localizacao: String): List<Optional<UsuarioEntity>>

}