package puc.facilita.facilitabackend.entity

import org.hibernate.annotations.LazyCollection
import org.hibernate.annotations.LazyCollectionOption
import puc.facilita.facilitabackend.entity.enum.StatusCliente
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "usuarios")
data class UsuarioEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    val id: Int? = null,

    @Column(name = "email")
    var email: String? = null,

    @Column(name = "senha")
    var senha: String? = null,

    @Column(name = "nome")
    var nome: String? = null,

    @Column(name = "telefone")
    var telefone: String? = null,

    @Column(name = "localizacao")
    var localizacao: String? = null,

    @Column(name = "data_de_cadastro", columnDefinition = "timestamp")
    val dataDeCadastro: LocalDateTime? = LocalDateTime.now(),

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    var status: StatusCliente? = StatusCliente.ATIVO,

    @Column(name = "data_de_exclusao", columnDefinition = "timestamp")
    var dataDeExclusao: LocalDateTime? = null,

    @Transient
    @Column(name = "fotos")
    var fotos: List<FotoEntity>? = emptyList(),

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "usuario")
    var anuncios: List<AnuncioEntity> = emptyList(),
)