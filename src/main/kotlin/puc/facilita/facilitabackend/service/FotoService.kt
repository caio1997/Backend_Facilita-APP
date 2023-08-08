package puc.facilita.facilitabackend.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import puc.facilita.facilitabackend.entity.FotoEntity
import puc.facilita.facilitabackend.entity.enum.TipoDeFoto
import puc.facilita.facilitabackend.repository.FotoRepository
import java.util.*

@Service
class FotoService(private val fotoRepository: FotoRepository) {

    fun findAll(): List<FotoEntity> {
        return fotoRepository.findAll()
    }

    fun findById(id: Int): Optional<FotoEntity> {
        return fotoRepository.findById(id)
    }

    fun save(nome: String, tipo: TipoDeFoto,idDoVinculo: Int?, image: MultipartFile): FotoEntity {
        val fotoSave = FotoEntity(nome = nome, tipo = tipo, idDoVinculo = idDoVinculo, imagem = image.bytes)
        return fotoRepository.save(fotoSave)
    }

    fun update(id: Int, nome: String, tipo: TipoDeFoto, idDoVinculo: Int?, imagem: MultipartFile): FotoEntity? {
        val findFoto = this.findById(id)
        if(findFoto.isPresent) {
            val fotoAtualizada = findFoto.get()
            fotoAtualizada.nome = nome
            fotoAtualizada.tipo = tipo
            fotoAtualizada.imagem = imagem.bytes
            fotoAtualizada.idDoVinculo = idDoVinculo
            return fotoRepository.save(fotoAtualizada)
        } else {
            return null
        }
    }

    fun delete(id: Int) {
        val findFoto = this.findById(id)
        if(findFoto.isPresent) fotoRepository.deleteById(id)
    }


    fun findByIdDoVinculoAnuncio(idDoVinculo: Int): List<Optional<FotoEntity>> {
        return fotoRepository.findByIdDoVinculoAndTipo(idDoVinculo, TipoDeFoto.ANUNCIO)
    }

    fun findByIdDoVinculoCliente(idDoVinculo: Int): List<Optional<FotoEntity>> {
        return fotoRepository.findByIdDoVinculoAndTipo(idDoVinculo, TipoDeFoto.CLIENTE)
    }

    fun deleteByIdAndTipo(idDoVinculo: Int, tipo: TipoDeFoto) {
        fotoRepository.deleteByIdAndTipo(idDoVinculo, tipo)
    }

}