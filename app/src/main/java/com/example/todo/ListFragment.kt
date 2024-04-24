package com.example.todo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.todo.adapter.ToDoListAdapter
import com.example.todo.data.ListItem
import com.example.todo.data.ListItemRepository
import com.example.todo.data.ToDoItemRepository
import com.example.todo.databinding.FragmentListItemsListBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListFragment: Fragment() {

    private val adapter = ToDoListAdapter(
        onDeleteListener = :: onDelete,
        onEditListener = :: showEditDialog,
        onItemClick = :: navigateToDetails
    )

    private lateinit var binding: FragmentListItemsListBinding
    private lateinit var listItemRepository: ListItemRepository
    private lateinit var toDoItemRepository: ToDoItemRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding  = FragmentListItemsListBinding.inflate(inflater, container, false)
        listItemRepository = ListItemRepository(requireContext())
        //toDoItemRepository = ToDoItemRepository(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = binding.list
        recyclerView.adapter = adapter
        refreshTodoItems()
        binding.floatingActionBtn.setOnClickListener{
            showAddListDialogMaterial()
        }
        binding.btnSettings.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, SettingsFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    private fun showAddListDialogMaterial() {
        val editText = EditText(requireContext()).also {
            it.hint = "Enter text"
        }
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Add new list")
            .setView(editText)
            .setPositiveButton("Add") { dialog, _ ->
                onListItemAdded(editText.text.toString())
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
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

    private fun onEdit(itemId: Long, text: String) {
        lifecycleScope.launch {
            listItemRepository.editListItem(
                listItem = ListItem(itemId, text)
            )
            refreshTodoItems()
            Toast.makeText(requireContext(), "List item edited", Toast.LENGTH_SHORT).show()
        }
    }

    private fun onDelete(itemId: Long) {
        lifecycleScope.launch {
            listItemRepository.deleteListItem(itemId)
           // toDoItemRepository.deleteAllToDoItems()
            refreshTodoItems()
            Toast.makeText(requireContext(), "List item deleted", Toast.LENGTH_SHORT).show()
        }
    }

    private fun onListItemAdded(text: String) {
        lifecycleScope.launch {
            val newItem = ListItem(name = text)
            listItemRepository.insertListItem(newItem)
            refreshTodoItems()
            Toast.makeText(requireContext(), "List added: $text", Toast.LENGTH_SHORT).show()
        }
    }

    private fun navigateToDetails(listId: Long, title: String) {
        val detailsFragment = ListDetailsFragment.newInstance(listId, title)
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, detailsFragment)
            .addToBackStack(null)
            .commit()
    }

    private fun refreshTodoItems() {
       lifecycleScope.launch {
            val items = withContext(Dispatchers.IO) {
                listItemRepository.getAllListItems()
            }
            adapter.updateValues(items)
        }
    }
}