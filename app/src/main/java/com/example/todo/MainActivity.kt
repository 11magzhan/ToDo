package com.example.todo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.todo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val recyclerViewFragment = ListFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showToDoListFragment()
    }

    private fun showToDoListFragment() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, recyclerViewFragment)
            .commit()
    }
}