package di

import database.MongoDB
import org.koin.core.context.startKoin
import org.koin.dsl.module
import repository.ArticleRepository
import repository.ArticleRepositoryImpl
import viewModels.HomeViewModel
import viewModels.ProfileViewModel
import viewModels.SearchViewModel

fun initKoin() = startKoin {
    modules(modules)
}

val modules = module {
    single<ArticleRepository> { ArticleRepositoryImpl() }
    single { MongoDB() }
    factory { HomeViewModel(get()) }
    factory { SearchViewModel(get()) }
    factory { ProfileViewModel(get()) }
}
