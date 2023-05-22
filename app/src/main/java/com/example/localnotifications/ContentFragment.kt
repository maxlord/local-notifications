package com.example.localnotifications

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.localnotifications.databinding.FragmentContentBinding
import com.example.localnotifications.notification.LocalNotificationManager
import com.example.localnotifications.notification.LocalNotificationManagerImpl

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ContentFragment : Fragment() {

    private var _binding: FragmentContentBinding? = null
    private val notificationManager: LocalNotificationManager
        get() = LocalNotificationManagerImpl(requireContext())

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    // Register the permissions callback, which handles the user's response to the
    // system permissions dialog. Save the return value, an instance of
    // ActivityResultLauncher. You can use either a val, as shown in this snippet,
    // or a lateinit var in your onAttach() or onCreate() method.
    val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(requireContext(), "POST_NOTIFICATIONS разрешены", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(
                    requireContext(),
                    "POST_NOTIFICATIONS запрещены. Уведомления не создадутся!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

//    private var alarmReceiver: AlarmReceiver = AlarmReceiver()
//    private var alarmIntentFilter = IntentFilter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentContentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkPermissions()
        handleUIEvents()
    }

    private fun checkPermissions() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED -> {
                Toast.makeText(requireContext(), "POST_NOTIFICATIONS разрешены", Toast.LENGTH_SHORT)
                    .show()
            }

            else -> {
                // You can directly ask for the permission.
                // The registered ActivityResultCallback gets the result of this request.
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        }
    }

    private fun handleUIEvents() {
        binding.btnSend.setOnClickListener {
            val isKeepClean = binding.rbKeepClean.isChecked
            if (isKeepClean) {
                notificationManager.sendKeepCleanNotification()
            } else {
                notificationManager.sendStormCleanerNotification()
            }
        }

        binding.btnSchedule.setOnClickListener {
            val isKeepClean = binding.rbKeepClean.isChecked
            if (isKeepClean) {
                notificationManager.scheduleKeepCleanNotification(NOTIFICATION_DELAY_MS)
            } else {
                notificationManager.scheduleStormCleanerNotification(NOTIFICATION_DELAY_MS)
            }
            Toast.makeText(
                requireContext(),
                "Запуск через ${NOTIFICATION_DELAY_MS / (60 * 1000)} мин",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val NOTIFICATION_DELAY_MS = 2 * 60 * 1000L
    }
}