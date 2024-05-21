package di

import org.koin.core.context.startKoin
import org.koin.dsl.module
import repository.ArticleRepository
import repository.ArticleRepositoryImpl
import viewModels.HomeViewModel
import viewModels.SearchViewModel

fun initKoin() = startKoin {
    modules(homeViewModelModule)
}

val homeViewModelModule = module {
    single<ArticleRepository> { ArticleRepositoryImpl() }
    factory { HomeViewModel(get()) }
    factory { SearchViewModel(get()) }
}
