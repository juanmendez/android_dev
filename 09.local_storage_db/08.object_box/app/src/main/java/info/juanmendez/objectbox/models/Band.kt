package info.juanmendez.objectbox.models

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class Band(
        @Id var id: Long = 0,
        var name: String? = null
)