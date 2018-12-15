package info.juanmendez.objectbox

import android.app.Application
import info.juanmendez.objectbox.repo.DaggerRepositoryComponent
import info.juanmendez.objectbox.repo.RepositoryComponent

class ObjectBoxApplication : Application() {
    val repositoryComponent : RepositoryComponent by lazy {
        DaggerRepositoryComponent.builder().appModule(AppModule(this)).build()
    }
}