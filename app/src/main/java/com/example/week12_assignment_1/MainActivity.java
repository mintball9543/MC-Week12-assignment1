package com.example.week12_assignment_1;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText edtName, edtNumber, edtNameResult, edtNumberResult;
    Button btnAdd, btnReset, btnSearch, btnModify, btnDelete;
    SQLiteDatabase sqlDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DatabaseHelper(this);
        edtName = (EditText) findViewById(R.id.edtName);
        edtNumber = (EditText) findViewById(R.id.edtNumber);
        edtNameResult = (EditText) findViewById(R.id.edtNameResult);
        edtNumberResult = (EditText) findViewById(R.id.edtNumberResult);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnReset = (Button) findViewById(R.id.btnReset);
        btnSearch = (Button) findViewById(R.id.btnSearch);
        btnModify = (Button) findViewById(R.id.btnModify);
        btnDelete = (Button) findViewById(R.id.btnDelete);

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqlDB = myDb.getWritableDatabase();
                myDb.onUpgrade(sqlDB, 1, 2);
                sqlDB.close();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                sqlDB = myDb.getWritableDatabase();
//                sqlDB.execSQL("INSERT INTO groupTBL VALUES ('" + edtName.getText().toString() + "', " + edtNumber.getText().toString() + ");");
//                sqlDB.close();
                boolean isInserted = myDb.insertData(edtName.getText().toString(), edtNumber.getText().toString());
                if (isInserted == true) {
                    Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
                    viewAll();
                }
                else
                    Toast.makeText(MainActivity.this, "Data not Inserted", Toast.LENGTH_LONG).show();
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewAll();
            }
        });

        btnModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isUpdate = myDb.updateData(edtName.getText().toString(), edtNumber.getText().toString());
                viewAll();
                Toast.makeText(MainActivity.this, "Data Updated", Toast.LENGTH_LONG).show();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDb.deleteData(edtName.getText().toString());
                viewAll();
                Toast.makeText(MainActivity.this, "Data Deleted", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void viewAll() {
        Cursor res = myDb.getAllData();

        StringBuilder names = new StringBuilder("그룹 이름" + "\r\n" + "==========" + "\r\n");
        StringBuilder numbers = new StringBuilder("인원" + "\r\n" + "==========" + "\r\n");

        while (res.moveToNext()) {
            names.append(res.getString(0)).append("\r\n");
            numbers.append(res.getString(1)).append("\r\n");
        }

        edtNameResult.setText(names.toString());
        edtNumberResult.setText(numbers.toString());

        res.close();
    }
}