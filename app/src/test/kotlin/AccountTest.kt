import androidx.compose.ui.graphics.Color
import com.example.compose.rally.data.Account
import org.junit.Assert.assertTrue
import org.junit.Test

class AccountTest {

    @Test
    fun testAccountColor() {
        val listColors = listOf<Color>(
            Color(0xFF004940),
            Color(0xFF005D57),
            Color(0xFF04B97F),
            Color(0xFF37EFBA)
        )

      // Uncomment this!
      //  val account = Account("Buy coffee", 1234, 1000)
      //  assertTrue(listColors.contains(account.color))
    }
}