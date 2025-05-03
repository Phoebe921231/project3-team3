package com.example.dailytask

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class AllTasksActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_tasks)

        val taskList = findViewById<ListView>(R.id.allTaskList)
        val backBtn = findViewById<Button>(R.id.backBtn)

        val data = intent.getSerializableExtra("taskData") as? ArrayList<TaskData>
        val grouped = data?.groupBy { it.taskDate } ?: emptyMap()

        val taskStrings = mutableListOf<String>()
        for ((date, tasks) in grouped) {
            taskStrings.add("📅 $date")
            val groupedByType = tasks.groupBy { it.taskCategory }
            val order = listOf("學習", "工作", "生活")
            for (type in order) {
                val list = groupedByType[type]
                if (!list.isNullOrEmpty()) {
                    taskStrings.add("🔸 $type")
                    taskStrings.addAll(list.map { "‧ ${it.taskDescription}" })
                }
            }
        }

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, taskStrings)
        taskList.adapter = adapter

        backBtn.setOnClickListener {
            finish()
        }
    }
}

