package com.example.todo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.todo.adapter.ToDoItemAdapter
import com.example.todo.data.ToDoItem
import com.example.todo.data.ToDoItemRepository
import com.example.todo.databinding.FragmentListDetailsBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch

class ListDetailsFragment: Fragment() {

    private val adapter = ToDoItemAdapter(
        onDeleteListener = :: onDelete,
        onEditListener = :: showEditDialog
    )

    private lateinit var binding: FragmentListDetailsBinding
    private lateinit var toDoItemRepository: ToDoItemRepository

    companion object {
        fun newInstance(listId: Long, title: String) =
            ListDetailsFragment().apply {
                arguments = Bundle().apply {
                    putLong("listId", listId)
                    putString("title", title)
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListDetailsBinding.inflate(inflater, container, false)
        toDoItemRepository = ToDoItemRepository(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = binding.list
        recyclerView.adapter = adapter
        refreshTodoItems()
        binding.apply {
            toolbar.title = arguments?.getString("title")
            toolbar.setNavigationOnClickListener {
                parentFragmentManager.popBackStack()
            }
            floatingActionBtn.setOnClickListener {
                showAddListDialogMaterial()
            }
        }
    }

    private fun showAddListDialogMaterial() {
        val editText = EditText(requireContext()).also {
            it.hint = "Enter text"
        }
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Add new item")
            .setView(editText)
            .setPositiveButton("Add") { dialog, _ ->
                onToDoItemAdded(editText.text.toString())
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    private fun onDelete(itemId: Long) {
        lifecycleScope.launch {
            toDoItemRepository.deleteToDoItem(itemId)
            refreshTodoItems()
            Toast.makeText(requireContext(), "Todo item deleted", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showEditDialog(itemId: Long) {
        val editText = EditText(binding.root.context).also {
            it.hint = "Enter new text"
        }
        MaterialAlertDialogBuilder(binding.root.context)
            .setTitle("Edit text")
            .setView(editText)
            .setPositiveButton("Edit") { dialog, _ ->
                onEdit(itemId, editText.text.toString())
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss()}
            .show()
    }

    private fun onEdit(id: Long, text: String) {
        lifecycleScope.launch {
            toDoItemRepository.editToDoItem(id = id, text = text)
            refreshTodoItems()
            Toast.makeText(requireContext(), "ToDo item edited", Toast.LENGTH_SHORT).show()
        }
    }

    private fun onToDoItemAdded(text: String) {
        lifecycleScope.launch {
            val newItem = ToDoItem(
                text = text,
                listId = arguments?.getLong("listId")!!
            )
            toDoItemRepository.insertToDoItem(newItem)
            refreshTodoItems()
            Toast.makeText(requireContext(), "Item added: $text", Toast.LENGTH_SHORT).show()
        }
    }

    private fun refreshTodoItems() {
        lifecycleScope.launch {
            val listId = arguments?.getLong("listId") ?: return@launch
            val items = toDoItemRepository.getAllToDoItems(listId)
            adapter.updateValues(items)
        }
    }
}