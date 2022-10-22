package it.vfsfitvnm.vimusic.models

import androidx.compose.runtime.Immutable
import androidx.room.Junction
import androidx.room.Relation

@Immutable
open class DetailedSong(
    val id: String,
    val title: String,
    val artistsText: String? = null,
    val durationText: String?,
    val thumbnailUrl: String?,
    val totalPlayTimeMs: Long = 0,
    @Relation(
        entity = SongAlbumMap::class,
        entityColumn = "songId",
        parentColumn = "id",
        projection = ["albumId"]
    )
    val albumId: String?,
    @Relation(
        entity = Artist::class,
        entityColumn = "id",
        parentColumn = "id",
        associateBy = Junction(
            value = SongArtistMap::class,
            parentColumn = "songId",
            entityColumn = "artistId"
        ),
        projection = ["id", "name"]
    )
    val artists: List<Info>?
) {
    val formattedTotalPlayTime: String
        get() {
            val seconds = totalPlayTimeMs / 1000

            val hours = seconds / 3600

            return when {
                hours == 0L -> "${seconds / 60}m"
                hours < 24L -> "${hours}h"
                else -> "${hours / 24}d"
            }
        }
}
