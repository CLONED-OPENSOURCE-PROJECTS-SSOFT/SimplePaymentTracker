package simple.payment.tracker

import io.reactivex.Observable
import java.time.Instant

data class Transaction(
  val id: Long,
  val merchant: String,
  val sum: Int,
  val comment: String,
  val cancelled: Boolean = false,
  val time: Long = id,
  val category: String = "Еда",
  val trip: String? = null,
)

/**
 * Aggregates notifications and payments into one flat list ready for presentation.
 */
class TransactionsRepository(
  private val logger: Logger
) {
  private val transactions: Observable<List<Transaction>> by lazy {
    Observable.just(
      listOf(
        Transaction(
          id = Instant.now().toEpochMilli(),
          merchant = "First: Click here to see details",
          sum = 10,
          comment = "Leave the screen by clicking save or back",
          category = "Снаряга"
        ),
        Transaction(
          id = Instant.now().toEpochMilli(),
          merchant = "Second: put some text into the search in the top bar",
          sum = 10,
          comment = "It will filter the list so put one letter or so",
          category = "Снаряга"
        ),
        Transaction(
          id = Instant.now().toEpochMilli(),
          merchant = "Third: click on any item to crash the app",
          sum = 10,
          comment = "Boom!",
          category = "Снаряга"
        ),
        Transaction(
          id = Instant.now().toEpochMilli(),
          merchant = "167692837: MutableState is not evaluated for Text(style)",
          sum = 10,
          comment = "Try clicking on the checkbox several times, observe text in the TopBar",
          category = "Снаряга"
        ),
        Transaction(
          id = Instant.now().toEpochMilli(),
          merchant = "167692837: MutableState is not evaluated for Text(style)",
          sum = 10,
          comment = "Changing the text fixes the issue (see comments in DetainsScreen)",
          category = "Снаряга"
        ),
      )
    )
  }

  fun transactions(): Observable<List<Transaction>> {
    return transactions
  }

}
