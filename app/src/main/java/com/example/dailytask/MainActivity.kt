package com.example.dailytask

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var typeSpinner: Spinner
    private lateinit var taskInput: EditText
    private lateinit var addBtn: Button
    private lateinit var allBtn: Button
    private lateinit var dateBtn: Button
    private lateinit var taskList: ListView

    private val taskData = mutableListOf<TaskData>()
    private lateinit var adapter: ArrayAdapter<String>
    private var selectedDate: String = getTodayDate()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        typeSpinner = findViewById(R.id.typeSpinner)
        taskInput = findViewById(R.id.taskInput)
        addBtn = findViewById(R.id.addBtn)
        allBtn = findViewById(R.id.allBtn)
        dateBtn = findViewById(R.id.dateBtn)
        taskList = findViewById(R.id.taskList)

        val typeOptions = listOf("學習", "工作", "生活")
        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, typeOptions)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        typeSpinner.adapter = spinnerAdapter

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, mutableListOf())
        taskList.adapter = adapter

        dateBtn.text = selectedDate

        dateBtn.setOnClickListener {
            val cal = Calendar.getInstance()
            DatePickerDialog(this, { _, y, m, d ->
                selectedDate = "%04d-%02d-%02d".format(y, m + 1, d)
                dateBtn.text = selectedDate
                showTodayTasks()
            }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        addBtn.setOnClickListener {
            val text = taskInput.text.toString().trim()
            val type = typeSpinner.selectedItem.toString()
            if (text.isNotEmpty()) {
                taskData.add(TaskData(type, text, selectedDate))
                taskInput.text.clear()
                showTodayTasks()
            }
        }

        allBtn.setOnClickListener {
            val intent = Intent(this, AllTasksActivity::class.java)
            intent.putExtra("taskData", ArrayList(taskData))
            startActivity(intent)
        }

        showTodayTasks()
    }

    private fun showTodayTasks() {
        val todayTasks = taskData.filter { it.taskDate == selectedDate }
            .map { "${it.taskCategory}：${it.taskDescription}" }
        adapter.clear()
        adapter.addAll(todayTasks)
        adapter.notifyDataSetChanged()
    }

    private fun getTodayDate(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return sdf.format(Date())
    }
}

