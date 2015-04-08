package com.example.sajja_000.test1;

import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TabHost;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;



public class MainActivity extends Activity {

    EditText txtFirstName,txtLastName,txtAddress;
    List<Student> Students=new ArrayList<Student>();
    ListView studentListView;
    databaseHandler dbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtFirstName=(EditText) findViewById(R.id.txtFirstName);
        txtLastName=(EditText) findViewById(R.id.txtLastName);
        txtAddress=(EditText) findViewById(R.id.txtAddress);
    //    studentListView = (ListView) findViewById(R.id.listView);
        dbHandler=new databaseHandler(getApplicationContext());

        TabHost tabHost = (TabHost) findViewById(R.id.tabHost);

        tabHost.setup();

        TabHost.TabSpec tabSpec = tabHost.newTabSpec("creator");
        tabSpec.setContent(R.id.tabCreator);
        tabSpec.setIndicator("Creator");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("list");
        tabSpec.setContent(R.id.tabStudentList);
        tabSpec.setIndicator("List");
        tabHost.addTab(tabSpec);

       final Button btnAdd=(Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Student student = new Student(dbHandler.GetStudentCount(), String.valueOf(txtFirstName.getText()), String.valueOf(txtLastName.getText()), String.valueOf(txtFirstName.getText()));
                if (!studentExists(student)) {
                    dbHandler.createStudent(student);
                    Students.add(student);
                    Toast.makeText(getApplicationContext(), String.valueOf(txtFirstName.getText()) + " has been added to your Contacts!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(getApplicationContext(), String.valueOf(txtFirstName.getText()) + " already exists. Please use a different name.", Toast.LENGTH_SHORT).show();


            }



        });
        txtFirstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                btnAdd.setEnabled(String.valueOf(txtFirstName.getText()).trim().length() > 0);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        if (dbHandler.GetStudentCount() != 0)
            Students.addAll(dbHandler.getAllStudents());

        populateList();


    }
    private boolean studentExists(Student contact) {
        String name = contact.getFirstName();
        int contactCount = Students.size();

        for (int i = 0; i < contactCount; i++) {
            if (name.compareToIgnoreCase(Students.get(i).getFirstName()) == 0)
                return true;
        }
        return false;
    }
    private void populateList() {
        ArrayAdapter<Student> adapter = new StudentLitAdapter();
        studentListView.setAdapter(adapter);
    }
    private class StudentLitAdapter extends ArrayAdapter<Student> {
        public StudentLitAdapter() {
            super(MainActivity.this, R.layout.listview_item, Students);
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            if (view == null)
                view = getLayoutInflater().inflate(R.layout.listview_item, parent, false);

            Student currentStudent = Students.get(position);

            TextView firstName = (TextView) view.findViewById(R.id.txtFirstName);
            firstName.setText(currentStudent.getFirstName());
            TextView lastName = (TextView) view.findViewById(R.id.txtLastName);
            lastName.setText(currentStudent.getLastName());
            TextView address = (TextView) view.findViewById(R.id.txtAddress);
            address.setText(currentStudent.getAddress());


            return view;
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
