package puc.facilita.facilitabackend.entity

import org.hibernate.annotations.Type
import puc.facilita.facilitabackend.entity.enum.TipoDeFoto
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "fotos")
data class FotoEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    val id: Int? = null,

    @Lob
    @Column(name = "imagem")
    var imagem: ByteArray? = null,

    @Column(name = "data", columnDefinition = "timestamp")
    val data: LocalDateTime? = LocalDateTime.now(),

    @Column(name = "nome")
    var nome: String,

    @Column(name = "tipo")
    @Enumerated(EnumType.STRING)
    var tipo: TipoDeFoto,

    @Column(name = "id_do_vinculo")
    var idDoVinculo: Int? = null,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FotoEntity

        if (!imagem.contentEquals(other.imagem)) return false

        return true
    }

    override fun hashCode(): Int {
        return imagem.contentHashCode()
    }
}