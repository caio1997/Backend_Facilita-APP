package puc.facilita.facilitabackend.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional
import puc.facilita.facilitabackend.entity.FotoEntity
import puc.facilita.facilitabackend.entity.UsuarioEntity
import puc.facilita.facilitabackend.entity.enum.TipoDeFoto
import puc.facilita.facilitabackend.entity.enum.TiposDeProdutos
import java.util.Optional


interface FotoRepository: JpaRepository<FotoEntity, Int> {

    @Transactional
    fun findByIdDoVinculoAndTipo(idDoVinculo: Int, tipo: TipoDeFoto): List<Optional<FotoEntity>>

    @Transactional
    fun deleteByIdAndTipo(idDoVinculo: Int, tipo: TipoDeFoto)

}