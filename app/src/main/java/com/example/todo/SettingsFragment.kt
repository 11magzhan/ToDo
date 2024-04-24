package com.example.todo

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.todo.databinding.FragmentSettingsBinding

class SettingsFragment: Fragment() {
    private lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.uncompletedColors.setOnCheckedChangeListener { _, checkedId ->
            val selectedColor = when(checkedId) {
                R.id.red_not_completed -> Color.RED
                R.id.green_not_completed -> Color.GREEN
                R.id.blue_not_completed -> Color.BLUE
                else -> Color.BLACK
            }
            SharedPrefs.saveUncheckedColor(selectedColor)
        }
        binding.completedColors.setOnCheckedChangeListener { _, checkedId ->
            val selectedColor = when (checkedId) {
                R.id.red_completed -> Color.RED
                R.id.green_completed -> Color.GREEN
                R.id.blue_completed -> Color.BLUE
                else -> Color.BLACK
            }
            SharedPrefs.saveCheckedColor(selectedColor)
        }
    }
}