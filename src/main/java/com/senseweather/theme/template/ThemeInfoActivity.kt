package com.senseweather.theme.template

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.senseweather.theme.template.R

/**
 * Launcher Activity for the standalone theme APK.
 *
 * This is the entry point when users open the theme from the Play Store.
 * It displays theme information and provides a way to apply the theme
 * to the main Sense Weather app if it's installed.
 *
 * The theme is discovered automatically by Sense Weather via the meta-data tag
 * in AndroidManifest.xml. Users typically don't need to open this activity —
 * they browse and apply themes from within Sense Weather's settings.
 */
class ThemeInfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_theme_info)

        // Set up the UI
        findViewById<TextView>(R.id.theme_name).text = getString(R.string.theme_name)
        findViewById<TextView>(R.id.theme_author).text = getString(R.string.theme_author)
        findViewById<TextView>(R.id.theme_description).text = getString(R.string.theme_description)

        // Apply button
        findViewById<Button>(R.id.apply_button).setOnClickListener {
            applyTheme()
        }

        // Check if Sense Weather is installed
        if (!isSenseWeatherInstalled()) {
            findViewById<TextView>(R.id.install_hint).visibility = View.VISIBLE
            findViewById<Button>(R.id.install_button).visibility = View.VISIBLE
            findViewById<Button>(R.id.install_button).setOnClickListener {
                openSenseWeatherInPlayStore()
            }
        }
    }

    /**
     * Apply this theme to Sense Weather by sending a broadcast.
     * Sense Weather's ThemeManager picks it up via external theme discovery.
     */
    private fun applyTheme() {
        if (!isSenseWeatherInstalled()) {
            Toast.makeText(this, R.string.sense_weather_not_installed, Toast.LENGTH_LONG).show()
            return
        }

        try {
            // Sense Weather discovers themes automatically via PackageManager.
            // We tell it to apply our theme ID.
            val intent = Intent("com.eugene373.sense.weather.ACTION_APPLY_THEME").apply {
                putExtra("theme_id", getThemeId())
                setPackage("com.eugene373.sense.weather")
            }
            sendBroadcast(intent)
            Toast.makeText(this, R.string.theme_applied_success, Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            // Theme will still be available in Sense Weather's browser
            Toast.makeText(this, R.string.tap_to_apply, Toast.LENGTH_LONG).show()
        }
    }

    private fun isSenseWeatherInstalled(): Boolean {
        return try {
            packageManager.getPackageInfo("com.eugene373.sense.weather", 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }

    private fun openSenseWeatherInPlayStore() {
        try {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.eugene373.sense.weather")))
        } catch (e: Exception) {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.eugene373.sense.weather")))
        }
    }

    private fun getThemeId(): String {
        return try {
            val appInfo = packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA)
            appInfo.metaData?.getString("com.eugene373.sense.weather.THEME") ?: "com.senseweather.theme.template"
        } catch (e: Exception) {
            "com.senseweather.theme.template"
        }
    }
}
