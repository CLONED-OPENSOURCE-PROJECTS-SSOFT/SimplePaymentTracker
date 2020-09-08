package simple.payment.tracker

import android.app.Application
import org.koin.core.context.startKoin
import org.koin.dsl.module
import simple.payment.tracker.compose.Backs

class Application : Application() {
  override fun onCreate() {
    super.onCreate()
    startKoin {
      modules(module {
        single { Logger() }
        single { TransactionsRepository(get()) }
        single { Backs() }
      })
    }
  }
}
