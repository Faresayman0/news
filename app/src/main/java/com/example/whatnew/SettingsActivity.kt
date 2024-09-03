package com.example.whatnew

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import com.example.whatnew.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // إعداد ViewBinding
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("app_settings", MODE_PRIVATE)

        // استرجاع البلد المحفوظ مسبقًا
        val selectedCountry = sharedPreferences.getString("selected_country", "")

        // تحديث حالة RadioButtons بناءً على البلد المحفوظ
        when (selectedCountry) {
            "us" -> binding.radioUs.isChecked = true
            "gb" -> binding.radioGb.isChecked = true
            "ca" -> binding.radioCa.isChecked = true
            else -> binding.radioAll.isChecked = true // الخيار الافتراضي
        }

        // حفظ البلد عند النقر على زر الحفظ
        binding.saveButton.setOnClickListener {
            val chosenCountry = when {
                binding.radioUs.isChecked -> "us"
                binding.radioGb.isChecked -> "gb"
                binding.radioCa.isChecked -> "ca"
                else -> "" // إذا كان الخيار "الكل"
            }
            saveSelectedCountry(chosenCountry)

            // الانتقال إلى صفحة الأخبار وإعادة تحميل الأخبار
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish() // اغلق صفحة الإعدادات
        }
    }

    private fun saveSelectedCountry(country: String) {
        val editor = sharedPreferences.edit()
        editor.putString("selected_country", country)
        editor.apply() // حفظ البلد في SharedPreferences
    }
}
