package com.example.quanlisinhvien

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var addEditLauncher: ActivityResultLauncher<Intent>

    private lateinit var listView: ListView
    private lateinit var adapter: studentAdapter
    private val students = mutableListOf<Student>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        //Khởi tạo listview và gán adapter
        listView = findViewById(R.id.list_students)
        adapter = studentAdapter(this, students)
        listView.adapter = adapter

        // Đăng ký context menu cho ListView
        registerForContextMenu(listView)

        addEditLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK && result.data != null) {
                val data = result.data!!
                val position = data.getIntExtra("position", -1)
                val name = data.getStringExtra("name") ?: ""
                val mssv = data.getStringExtra("mssv") ?: ""
                val email = data.getStringExtra("email") ?: ""
                val phone = data.getStringExtra("phone") ?: ""
                if (position >= 0 && position < students.size){
                    students[position] = Student(name, mssv, email, phone)
                } else {
                    students.add(Student(name, mssv, email, phone))
                }
                adapter.notifyDataSetChanged()
                }
            }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if(item.itemId == R.id.menu_add){
            val intent = Intent(this, AddUpdateActivity::class.java)
            addEditLauncher.launch(intent)
            true
        } else {
            return super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.context_menu_student, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        val position = info.position
        val student = students[position]
        when (item.itemId) {
            R.id.action_edit -> {
                val intent = Intent(this, AddUpdateActivity::class.java).apply {
                    putExtra("position", position)
                    putExtra("name", student.name)
                    putExtra("mssv", student.mssv)
                    putExtra("email", student.email)
                    putExtra("phone", student.phone)
                }
                addEditLauncher.launch(intent)
            }
            R.id.action_delete -> {
                AlertDialog.Builder(this)
                    .setTitle("Xác nhận xóa")
                    .setMessage("Bạn có chắc chắn muốn xóa sinh viên ${student.name}?")
                    .setPositiveButton("Có") { _, _ ->
                        students.removeAt(position)
                        adapter.notifyDataSetChanged()
                    }
                    .setNegativeButton("Không", null)
                    .show()
            }
            R.id.action_call -> {
                val uri = Uri.parse("tel:${student.phone}")
                startActivity(Intent(Intent.ACTION_DIAL, uri))
            }
            R.id.action_email -> {
                val uri = Uri.parse("mailto:${student.email}")
                startActivity(Intent(Intent.ACTION_SENDTO, uri))
            }
        }
        return true
    }
}