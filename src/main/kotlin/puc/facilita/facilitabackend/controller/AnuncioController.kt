package puc.facilita.facilitabackend.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import puc.facilita.facilitabackend.entity.AnuncioEntity
import puc.facilita.facilitabackend.entity.UsuarioEntity
import puc.facilita.facilitabackend.service.AnuncioService
import java.util.*

@RestController
@RequestMapping("/anuncio", produces = [MediaType.APPLICATION_JSON_VALUE])
class AnuncioController(private val anuncioService: AnuncioService) {

    @GetMapping("/findAll")
    fun findAll(): ResponseEntity<List<AnuncioEntity>> {
        val response = anuncioService.findAll()
        return ResponseEntity.ok().body(response)
    }

    @GetMapping("/findById/{id}")
    fun findById(@PathVariable("id") id: Int): ResponseEntity<Optional<AnuncioEntity>> {
        val response = anuncioService.findById(id)
        return ResponseEntity.ok().body(response)
    }

    @PostMapping("/save")
    fun save(@RequestBody anuncio: AnuncioEntity): ResponseEntity<AnuncioEntity> {
        val responseSave = anuncioService.save(anuncio)
        return ResponseEntity.ok().body(responseSave)
    }

    @PutMapping("/update/{id}")
    fun update(@PathVariable("id") id: Int, @RequestBody anuncio: AnuncioEntity): ResponseEntity<AnuncioEntity> {
        val responseUpdate = anuncioService.update(id, anuncio)
        return ResponseEntity.ok().body(responseUpdate)
    }

    @DeleteMapping("/delete/{id}")
    fun delete(@PathVariable("id") id: Int): ResponseEntity<Any> {
        anuncioService.delete(id)
        return ResponseEntity.ok().build()
    }

    @GetMapping("/contabilizar-anuncios/{id}")
    fun contabilizarAnuncios(@PathVariable("id") idUsuario: Int): ResponseEntity<Int> {
        val response = anuncioService.contabilizarAnuncios(idUsuario)
        return ResponseEntity.ok().body(response)
    }

    @GetMapping("/findByLocalizacao")
    fun findByLocalizacao(@RequestParam("localizacao") localizacao: String): ResponseEntity<List<AnuncioEntity>> {
        val response = anuncioService.findByLocalizacao(localizacao)
        return ResponseEntity.ok().body(response)
    }


}