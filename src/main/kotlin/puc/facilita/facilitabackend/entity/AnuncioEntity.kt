package puc.facilita.facilitabackend.entity

import org.hibernate.annotations.LazyCollection
import org.hibernate.annotations.LazyCollectionOption
import puc.facilita.facilitabackend.entity.enum.TiposDeProdutos
import puc.facilita.facilitabackend.entity.enum.TiposDeUso
import java.math.BigDecimal
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "anuncios")
data class AnuncioEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int? = null,

    @Column(name = "nome")
    var nome: String,

    @Column(name = "descricao")
    var descricao: String? = "",

    @Column(name = "capacidade")
    var capacidade: String? = null,

    @Column(name = "polegada")
    var polegada: Double? = null,

    @Column(name = "descricaoGeral")
    var descricaoGeral: String? = null,

    @Column(name = "preco")
    var preco: BigDecimal,

    @Column(name = "uso")
    @Enumerated(EnumType.STRING)
    var uso: TiposDeUso,

    @Column(name = "produto")
    @Enumerated(EnumType.STRING)
    var produto: TiposDeProdutos,

    @Column(name = "data", columnDefinition = "timestamp")
    val data: LocalDateTime? = LocalDateTime.now(),

    @Transient
    @Column(name = "fotos")
    var fotos: List<FotoEntity>? = emptyList(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private val usuario: UsuarioEntity? = null,

)