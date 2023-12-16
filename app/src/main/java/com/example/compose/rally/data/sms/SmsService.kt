import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import com.example.compose.rally.R
import com.example.compose.rally.RallyActivity

@Suppress("DEPRECATION")
class SmsService : Service() {

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val smsBody = intent?.extras?.getString("sms_body")
        smsBody?.let {
            showNotification(it)
        }
        return START_STICKY
    }

    private fun showNotification(text: String) {
        val contentIntent = PendingIntent.getActivity(
            this, 0,
            Intent(this, RallyActivity::class.java), PendingIntent.FLAG_MUTABLE
        )
        val context = applicationContext
        val builder = Notification.Builder(context)
            .setContentTitle("Rugball")
            .setContentText(text)
            .setContentIntent(contentIntent)
            .setSmallIcon(R.drawable.enderhead)
            .setAutoCancel(true)
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification = builder.build()
        notificationManager.notify(R.drawable.enderhead, notification)
    }
}
