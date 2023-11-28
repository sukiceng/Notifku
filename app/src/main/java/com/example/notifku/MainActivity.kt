package com.example.notifku

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import com.example.notifku.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val channelId = "TEST_NOTIF"
    private val notifId = 90


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val notifManager = getSystemService(Context.NOTIFICATION_SERVICE) as
                NotificationManager

        binding.btnNotif.setOnClickListener {
            val flag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            }
            else {
                0
            }
            val intent = Intent(this, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(
                this,
                0,
                intent,
                flag
            )
            val builder = NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.baseline_notifications_24)
                .setContentTitle("Notifku")
                .setContentText("Hello World!")
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
            val notifManager = getSystemService(Context.NOTIFICATION_SERVICE) as
                    NotificationManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val notifChannel = NotificationChannel(
                    channelId, // Id channel
                    "Notifku", // Nama channel notifikasi
                    NotificationManager.IMPORTANCE_DEFAULT
                )
                with(notifManager) {
                    createNotificationChannel(notifChannel)
                    notify(notifId, builder.build())
                }
            }
            else {
                notifManager.notify(notifId, builder.build())
            }
        }
        binding.btnUpdate.setOnClickListener {
            val notifImage = BitmapFactory.decodeResource(resources,
                R.drawable.maxx_verstapen)
            val builder = NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.baseline_notifications_24)
                .setContentTitle("Notifku")
                .setContentText("Ini update notifikasi")
                .setStyle(
                    NotificationCompat.BigPictureStyle()
                        .bigPicture(notifImage)
                )
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            notifManager.notify(notifId, builder.build())
        }
    }
}



