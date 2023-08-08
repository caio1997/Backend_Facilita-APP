package puc.facilita.facilitabackend.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import puc.facilita.facilitabackend.entity.AnuncioEntity
import puc.facilita.facilitabackend.entity.FotoEntity
import puc.facilita.facilitabackend.entity.UsuarioEntity
import puc.facilita.facilitabackend.entity.enum.StatusCliente
import puc.facilita.facilitabackend.entity.enum.TipoDeFoto
import puc.facilita.facilitabackend.repository.UsuarioRepository
import java.util.*

@Service
class UsuarioService(
    private val usuarioRepository: UsuarioRepository,
    private val fotoService: FotoService,
) {

    @Autowired
    lateinit var anuncioService: AnuncioService

    private fun findFotosDoCliente(idDoAnuncio: Int): List<FotoEntity> {
        val findFotos = fotoService.findByIdDoVinculoCliente(idDoAnuncio)
        val listaDeFotos = mutableListOf<FotoEntity>()
        if(findFotos.isNotEmpty()) {
            findFotos.map { if(it.isPresent) listaDeFotos.add(it.get()) }
        }
        return listaDeFotos.toList()
    }

    fun findAll(): List<UsuarioEntity> {
        val findAllCliente = usuarioRepository.findAll()
        findAllCliente.forEach {
            val fotosDoCliente = findFotosDoCliente(it.id!!)
            it.fotos = fotosDoCliente
        }
        findAllCliente.map {
            val newAnuncios = mutableListOf<AnuncioEntity>()
            it.anuncios.forEach {
                newAnuncios.add(vinculaFotosDosAnunciosDoCliente(it))
            }
            it.anuncios = newAnuncios
        }
        return findAllCliente
    }

    private fun vinculaFotosDosAnunciosDoCliente(anuncioEntity: AnuncioEntity): AnuncioEntity {
        val fotosDoAnuncio = fotoService.findByIdDoVinculoAnuncio(anuncioEntity.id!!)
        val fotos = fotosDoAnuncio.map { it.get() }
        anuncioEntity.fotos = fotos
        return anuncioEntity
    }

    fun findById(id: Int): Optional<UsuarioEntity> {

        val findCliente = usuarioRepository.findById(id)
        if(findCliente.isPresent) {
            val cliente = findCliente.get()
            cliente.fotos = findFotosDoCliente(cliente.id!!)
            val newAnuncios = mutableListOf<AnuncioEntity>()
            cliente.anuncios.forEach {
                newAnuncios.add(vinculaFotosDosAnunciosDoCliente(it))
            }
            cliente.anuncios = newAnuncios
            return Optional.of(cliente)
        }
        return findCliente
    }

    fun save(usuario: UsuarioEntity): UsuarioEntity {
        return usuarioRepository.save(usuario)
    }

    fun login(email: String, senha: String): Boolean {
        val response = usuarioRepository.findByEmail(email)
        if(!response.isPresent) return false
        return (response.get().senha == senha && response.get().status == StatusCliente.ATIVO)
    }

    fun update(id: Int, usuario: UsuarioEntity): UsuarioEntity? {
        val findUsuario = this.findById(id)
        if(findUsuario.isPresent) {
            val usuarioAtualizado = findUsuario.get()
            usuarioAtualizado.nome = usuario.nome
            usuarioAtualizado.email = usuario.email
            usuarioAtualizado.senha = usuario.senha
            usuarioAtualizado.telefone = usuario.telefone
            usuarioAtualizado.localizacao = usuario.localizacao
            return usuarioRepository.save(usuarioAtualizado)
        } else {
            return null
        }
    }

    fun delete(id: Int) {
        val findUsuario = this.findById(id)
        if(findUsuario.isPresent) {
            if(findUsuario.get().status != StatusCliente.DELETADO) {
                val fotosDoUsuario = fotoService.findByIdDoVinculoCliente(findUsuario.get().id!!)
                fotosDoUsuario.map { fotoService.deleteByIdAndTipo(it.get().id!!, TipoDeFoto.CLIENTE) }
                val anuncios = anuncioService.findAllAnunciosByIdCliente(id)
                anuncios.map { anuncioService.delete(it.id!!) }
                usuarioRepository.updateStatusCliente(id)
            }
        }
    }

    fun findByEmail(email: String): Optional<UsuarioEntity>? {
        val response = usuarioRepository.findByEmail(email)
        if(response.isPresent)
            return response
        return null
    }

    fun findByLocalizacao(localizacao: String): List<Optional<UsuarioEntity>> {
        return usuarioRepository.findByLocalizacao(localizacao)
    }

}