package puc.facilita.facilitabackend.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import puc.facilita.facilitabackend.entity.AnuncioEntity
import puc.facilita.facilitabackend.entity.FotoEntity
import puc.facilita.facilitabackend.entity.UsuarioEntity
import puc.facilita.facilitabackend.entity.enum.TipoDeFoto
import puc.facilita.facilitabackend.repository.AnuncioRepository
import java.util.*

@Service
class AnuncioService(
    private val anuncioRepository: AnuncioRepository,
    private val fotoService: FotoService,
) {

    @Autowired
    lateinit var usuarioService: UsuarioService

    fun findFotosDoAnuncio(idDoAnuncio: Int): List<FotoEntity> {
        val findFotos = fotoService.findByIdDoVinculoAnuncio(idDoAnuncio)
        val listaDeFotos = mutableListOf<FotoEntity>()
        if(findFotos.isNotEmpty()) {
            findFotos.map { if(it.isPresent) listaDeFotos.add(it.get()) }
        }
        return listaDeFotos.toList()
    }

    fun findAll(): List<AnuncioEntity> {
        val findAllAnuncios = anuncioRepository.findAll()
        findAllAnuncios.forEach {
            val fotosDoAnuncio = findFotosDoAnuncio(it.id!!)
            it.fotos = fotosDoAnuncio
        }
        return findAllAnuncios
    }

    fun findById(id: Int): Optional<AnuncioEntity> {
        val findAnuncio = anuncioRepository.findById(id)
        if(findAnuncio.isPresent) {
            val anuncio = findAnuncio.get()
            anuncio.fotos = findFotosDoAnuncio(anuncio.id!!)
            return Optional.of(anuncio)
        }
        return findAnuncio
    }

    fun save(anuncio: AnuncioEntity): AnuncioEntity {
        return anuncioRepository.save(anuncio)
    }

    fun update(id: Int, anuncio: AnuncioEntity): AnuncioEntity? {
        val findAnuncio = this.findById(id)
        if(findAnuncio.isPresent) {
            val anuncioAtualizado = findAnuncio.get()
            anuncioAtualizado.nome = anuncio.nome
            anuncioAtualizado.descricao = anuncio.descricao
            anuncioAtualizado.capacidade = anuncio.capacidade
            anuncioAtualizado.polegada = anuncio.polegada
            anuncioAtualizado.descricaoGeral = anuncio.descricaoGeral
            anuncioAtualizado.preco = anuncio.preco
            anuncioAtualizado.uso = anuncio.uso
            anuncioAtualizado.produto = anuncio.produto
            return anuncioRepository.save(anuncioAtualizado)
        } else {
            return null
        }
    }

    fun delete(id: Int) {
        val findAnuncio = this.findById(id)
        if(findAnuncio.isPresent) {
            val fotosDoAnuncio = fotoService.findByIdDoVinculoAnuncio(findAnuncio.get().id!!)
            fotosDoAnuncio.map { fotoService.deleteByIdAndTipo(it.get().id!!, TipoDeFoto.ANUNCIO) }
            anuncioRepository.deleteById(id)
        }
    }

    fun contabilizarAnuncios(idUsuario: Int): Int {
        val anuncios = anuncioRepository.findByUsuarioId(idUsuario)
        return if(anuncios.isNotEmpty()) anuncios.size else 0
    }

    fun findAllAnunciosByIdCliente(idUsuario: Int): List<AnuncioEntity>{
        val anuncios = anuncioRepository.findByUsuarioId(idUsuario)
        return if(anuncios.isNotEmpty()) {
            anuncios.map { it.get() }
        } else
            emptyList()
    }

    fun findByNameAndUsuario(name: String, usuarioEntity: UsuarioEntity): AnuncioEntity? {
        val response = anuncioRepository.findByNomeAndUsuario(name, usuarioEntity)
        return if(response.isPresent) response.get() else null
    }

    fun findByLocalizacao(localizacao: String): List<AnuncioEntity>? {
        val usuarios = usuarioService.findByLocalizacao(localizacao)
        if(usuarios.isEmpty()) return null
        val anuncios = mutableListOf<AnuncioEntity>()
        usuarios.forEach {
            val anunciosByUsuarioId = anuncioRepository.findByUsuarioId(it.get().id!!)
            if(anunciosByUsuarioId.isNotEmpty())
                anuncios += anunciosByUsuarioId.map { it.get() }

        }
        return anuncios
    }

}