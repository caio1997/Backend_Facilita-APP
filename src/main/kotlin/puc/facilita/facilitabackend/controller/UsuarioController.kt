package puc.facilita.facilitabackend.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import puc.facilita.facilitabackend.entity.UsuarioEntity
import puc.facilita.facilitabackend.entity.dto.UsuarioDTO
import puc.facilita.facilitabackend.service.UsuarioService
import java.util.*

@RestController
@RequestMapping("/usuario", produces = [MediaType.APPLICATION_JSON_VALUE])
class UsuarioController(private val usuarioService: UsuarioService) {

    @GetMapping("/findAll")
    fun findAll(): ResponseEntity<List<UsuarioEntity>> {
        val response = usuarioService.findAll()
        return ResponseEntity.ok().body(response)
    }

    @GetMapping("/findById/{id}")
    fun findById(@PathVariable("id") id: Int): ResponseEntity<Optional<UsuarioEntity>> {
        val response = usuarioService.findById(id)
        return ResponseEntity.ok().body(response)
    }

    @PostMapping("/save")
    fun save(@RequestBody usuario: UsuarioEntity): ResponseEntity<UsuarioEntity> {
        val responseSave = usuarioService.save(usuario)
        return ResponseEntity.ok().body(responseSave)
    }

    @PutMapping("/update/{id}")
    fun update(@PathVariable("id") id: Int, @RequestBody usuario: UsuarioEntity): ResponseEntity<UsuarioEntity> {
        val responseUpdate = usuarioService.update(id, usuario)
        return ResponseEntity.ok().body(responseUpdate)
    }

    @DeleteMapping("/delete/{id}")
    fun delete(@PathVariable("id") id: Int): ResponseEntity<Any> {
        usuarioService.delete(id)
        return ResponseEntity.ok().build()
    }

    @GetMapping("/findByEmail/{email}")
    fun findByEmail(@PathVariable("email") email: String): ResponseEntity<Optional<UsuarioEntity>> {
        val response = usuarioService.findByEmail(email)
        return ResponseEntity.ok().body(response)
    }

    @PostMapping("/login")
    fun login(@RequestHeader email: String, @RequestHeader senha: String): ResponseEntity<UsuarioDTO> {
        val responseSave = usuarioService.login(email, senha)
        return ResponseEntity.ok().body(responseSave)
    }

}