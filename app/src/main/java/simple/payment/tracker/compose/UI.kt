package simple.payment.tracker.compose

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Box
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import org.koin.core.context.KoinContextHandler
import simple.payment.tracker.Transaction
import simple.payment.tracker.TransactionsRepository
import simple.payment.tracker.theme.PaymentsTheme

sealed class Screen {
  object ListAll : Screen()
  data class Details(val transaction: Transaction) : Screen()
}

@Composable
fun PaymentsApp(backs: Backs, trasactions: TransactionsRepository) {
  PaymentsTheme {
    AppContent()
  }
}

@Composable
private fun AppContent() {
  val currentScreen: MutableState<Screen> = remember { mutableStateOf(Screen.ListAll) }
  KoinContextHandler.get().get<Backs>()
    .backPressed
    .commitSubscribe {
      currentScreen.value = Screen.ListAll
    }

  val search = remember { mutableStateOf(TextFieldValue("")) }
  Scaffold(
    topBar = {
      // actually makes no difference
      if (currentScreen.value == Screen.ListAll) {
        NavigationTopBar(search, currentScreen)
      }
    },
    bodyContent = {
      Crossfade(currentScreen) { screen ->
        Surface(color = MaterialTheme.colors.background) {
          when (val scr = screen.value) {
            is Screen.ListAll -> ListScreen(true, currentScreen, search)
            is Screen.Details -> DetailsScreen(scr.transaction, currentScreen)
          }
        }
      }
    }
  )
}

@Composable
fun Modifier.debugBorder(): Modifier {
  return this
}

@Composable
private fun NavigationTopBar(
  search: MutableState<TextFieldValue>,
  currentScreen: MutableState<Screen>
) {
  TopAppBar(
    content = {
      if (currentScreen.value is Screen.ListAll) {
        TextField(
          value = search.value,
          onValueChange = { search.value = it },
          label = { Text("Search") },
          textStyle = MaterialTheme.typography.body1,
          backgroundColor = Color.Transparent,
          activeColor = MaterialTheme.colors.onSurface,
          modifier = Modifier.padding(2.dp)
        )
      } else {
        Box(Modifier.debugBorder())
      }
    }
  )
}