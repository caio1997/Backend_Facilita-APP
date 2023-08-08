package puc.facilita.facilitabackend.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import puc.facilita.facilitabackend.entity.FotoEntity
import puc.facilita.facilitabackend.entity.UsuarioEntity
import puc.facilita.facilitabackend.entity.enum.TipoDeFoto
import puc.facilita.facilitabackend.service.FotoService
import java.util.*

@RestController
@RequestMapping("/foto", produces = [MediaType.APPLICATION_JSON_VALUE])
class FotoController(private val fotoService: FotoService) {

    @GetMapping("/findAll")
    fun findAll(): ResponseEntity<List<FotoEntity>> {
        val response = fotoService.findAll()
        return ResponseEntity.ok().body(response)
    }

    @GetMapping("/findById/{id}")
    fun findById(@PathVariable("id") id: Int): ResponseEntity<Optional<FotoEntity>> {
        val response = fotoService.findById(id)
        return ResponseEntity.ok().body(response)
    }

    @PostMapping("/save")
    fun save(@RequestParam("nome") nome: String, @RequestParam("tipo") tipo: TipoDeFoto, @RequestParam("idDoVinculo", required = false) idDoVinculo: Int? = null, @RequestParam imagem: MultipartFile): ResponseEntity<FotoEntity> {
        val responseSave = fotoService.save(nome, tipo, idDoVinculo, imagem)
        return ResponseEntity.ok().body(responseSave)
    }

    @PutMapping("/update/{id}")
    fun update(@PathVariable("id") id: Int, @RequestParam("nome") nome: String, @RequestParam("tipo") tipo: TipoDeFoto, @RequestParam("idDoVinculo", required = false) idDoVinculo: Int? = null, @RequestParam imagem: MultipartFile): ResponseEntity<FotoEntity> {
        val responseUpdate = fotoService.update(id, nome, tipo, idDoVinculo, imagem)
        return ResponseEntity.ok().body(responseUpdate)
    }

    @DeleteMapping("/delete/{id}")
    fun delete(@PathVariable("id") id: Int): ResponseEntity<Any> {
        fotoService.delete(id)
        return ResponseEntity.ok().build()
    }

    @GetMapping("/find-by-id-vinculo-anuncio/{id}")
    fun findByIdDoVinculoAnuncio(@PathVariable("id") id: Int): ResponseEntity<List<Optional<FotoEntity>>>{
        val response = fotoService.findByIdDoVinculoAnuncio(id)
        return ResponseEntity.ok().body(response)
    }

    @GetMapping("/find-by-id-vinculo-cliente/{id}")
    fun findByIdDoVinculoCliente(@PathVariable("id") id: Int): ResponseEntity<List<Optional<FotoEntity>>>{
        val response = fotoService.findByIdDoVinculoCliente(id)
        return ResponseEntity.ok().body(response)
    }

}