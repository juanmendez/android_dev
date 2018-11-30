package info.juanmendez.objectbox.repo

import dagger.Component
import info.juanmendez.objectbox.AppModule
import javax.inject.Singleton

@Singleton
@Component(
        modules = [AppModule::class, RepositoryModule::class]
)
interface RepositoryComponent {
    fun provideSongRepository(): SongRepository
    fun provideBandRepository(): BandRepository
}