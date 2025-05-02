package com.example.quanlisinhvien

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class AddUpdateActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_update)

        val position = intent.getIntExtra("position", -1)
        // Khởi tạo các thành phần giao diện và gán sự kiện cho các thành phần
        val etM = findViewById<EditText>(R.id.etMssv)
        val etN = findViewById<EditText>(R.id.etName)
        val etE = findViewById<EditText>(R.id.etEmail)
        val etP = findViewById<EditText>(R.id.etPhone)
        // Nếu đang edit (pos >= 0), tiền điền dữ liệu cũ
        if (position >= 0) {
            etN.setText(intent.getStringExtra("name"))
            etM.setText(intent.getStringExtra("mssv"))
            etE.setText(intent.getStringExtra("email"))
            etP.setText(intent.getStringExtra("phone"))
        }
        findViewById<Button>(R.id.btnSave).setOnClickListener {
            intent.putExtra("position", position)
            intent.putExtra("name", etN.text.toString())
            intent.putExtra("mssv", etM.text.toString())
            intent.putExtra("email", etE.text.toString())
            intent.putExtra("phone", etP.text.toString())
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}