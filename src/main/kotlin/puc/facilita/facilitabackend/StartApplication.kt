package puc.facilita.facilitabackend

import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component
import puc.facilita.facilitabackend.entity.AnuncioEntity
import puc.facilita.facilitabackend.entity.UsuarioEntity
import puc.facilita.facilitabackend.entity.enum.StatusCliente
import puc.facilita.facilitabackend.entity.enum.TiposDeProdutos
import puc.facilita.facilitabackend.entity.enum.TiposDeUso
import puc.facilita.facilitabackend.repository.AnuncioRepository
import puc.facilita.facilitabackend.repository.UsuarioRepository
import java.math.BigDecimal
import java.time.LocalDateTime

@Component
class StartApplication(
    private val usuarioRepository: UsuarioRepository,
    private val anuncioRepository: AnuncioRepository,
) : ApplicationRunner {

    override fun run(args: ApplicationArguments?) {
        val usuarios = mutableListOf<UsuarioEntity>()
        val usuarioO1 = UsuarioEntity(null, "usuario_teste_01@hotmail.com", "12345", "Usuário Teste 01", "(31)99155-1010", "Belo Horizonte - MG", LocalDateTime.now(), StatusCliente.ATIVO, null)
        val usuarioO2 = UsuarioEntity(null, "usuario_teste_02@hotmail.com", "12345", "Usuário Teste 02", "(31)99155-1011", "Belo Horizonte - MG", LocalDateTime.now(), StatusCliente.ATIVO, null)
        val usuarioO3 = UsuarioEntity(null, "usuario_teste_03@hotmail.com", "12345", "Usuário Teste 03", "(31)99155-1011", "Belo Horizonte - MG", LocalDateTime.now(), StatusCliente.ATIVO, null)
        val usuario01Exists = usuarioRepository.findByEmail("usuario_teste_01@hotmail.com")
        if(!usuario01Exists.isPresent) usuarios.add(usuarioO1)
        val usuario02Exists = usuarioRepository.findByEmail("usuario_teste_02@hotmail.com")
        if(!usuario02Exists.isPresent) usuarios.add(usuarioO2)
        val usuario03Exists = usuarioRepository.findByEmail("usuario_teste_03@hotmail.com")
        if(!usuario03Exists.isPresent) usuarios.add(usuarioO3)
        usuarioRepository.saveAll(usuarios)

        val findUsuario01 = usuarioRepository.findByEmail("usuario_teste_01@hotmail.com").get()
        val findUsuario02 = usuarioRepository.findByEmail("usuario_teste_02@hotmail.com").get()
        val findUsuario03 = usuarioRepository.findByEmail("usuario_teste_03@hotmail.com").get()

        val anuncios = mutableListOf<AnuncioEntity>()
        val anuncio01 = AnuncioEntity(null, "Anuncio 01", "Anuncio Descrição 01", "8GB RAM - 240SSD", null, null, BigDecimal.valueOf(2500), TiposDeUso.NOVO, TiposDeProdutos.NOTEBOOK, LocalDateTime.now(), null, findUsuario01)
        val anuncio02 = AnuncioEntity(null, "Anuncio 02", "Anuncio Descrição 02", null, 55.00, null, BigDecimal.valueOf(3200), TiposDeUso.NOVO, TiposDeProdutos.MONITOR_TV, LocalDateTime.now(), null, findUsuario01)
        val anuncio03 = AnuncioEntity(null, "Anuncio 03", "Anuncio Descrição 03", null, 29.00, null, BigDecimal.valueOf(600), TiposDeUso.USADO, TiposDeProdutos.MONITOR_TV, LocalDateTime.now(), null, findUsuario02)
        val anuncio04 = AnuncioEntity(null, "Anuncio 04", "Anuncio Descrição 04", "16GB RAM - 1TB", null, null, BigDecimal.valueOf(5600), TiposDeUso.NOVO, TiposDeProdutos.NOTEBOOK, LocalDateTime.now(), null, findUsuario03)
        val anuncio05 = AnuncioEntity(null, "Anuncio 05", "Anuncio Descrição 05", "4GB RAM - 128GB", null, null, BigDecimal.valueOf(3500), TiposDeUso.USADO, TiposDeProdutos.CELULAR, LocalDateTime.now(), null, findUsuario03)
        val anuncio06 = AnuncioEntity(null, "Anuncio 06", "Anuncio Descrição 05", null, null, "Vendo produto geral", BigDecimal.valueOf(1240), TiposDeUso.USADO, TiposDeProdutos.GERAL, LocalDateTime.now(), null, findUsuario03)

        val existsAnuncio01 = anuncioRepository.findByNomeAndUsuario("Anuncio 01", findUsuario01)
        if(!existsAnuncio01.isPresent) anuncios.add(anuncio01)
        val existsAnuncio02 = anuncioRepository.findByNomeAndUsuario("Anuncio 02", findUsuario01)
        if(!existsAnuncio02.isPresent) anuncios.add(anuncio02)
        val existsAnuncio03 = anuncioRepository.findByNomeAndUsuario("Anuncio 03", findUsuario02)
        if(!existsAnuncio03.isPresent) anuncios.add(anuncio03)
        val existsAnuncio04 = anuncioRepository.findByNomeAndUsuario("Anuncio 04", findUsuario03)
        if(!existsAnuncio04.isPresent) anuncios.add(anuncio04)
        val existsAnuncio05 = anuncioRepository.findByNomeAndUsuario("Anuncio 05", findUsuario03)
        if(!existsAnuncio05.isPresent) anuncios.add(anuncio05)
        val existsAnuncio06 = anuncioRepository.findByNomeAndUsuario("Anuncio 06", findUsuario03)
        if(!existsAnuncio06.isPresent) anuncios.add(anuncio06)
        anuncioRepository.saveAll(anuncios)
    }

}