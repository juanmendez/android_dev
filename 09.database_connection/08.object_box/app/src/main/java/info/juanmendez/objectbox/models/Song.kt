package info.juanmendez.objectbox.models

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class Song(//@PrimaryKey
        @Id var id: Long = 0, //required to be a var, ObjectBox assigns id
        val title: String,
        val video_url: String,
        val year: Int)