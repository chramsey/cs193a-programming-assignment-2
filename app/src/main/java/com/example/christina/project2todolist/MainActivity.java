// Christina Ramsey <cmramsey@stanford.edu>
// To do List - This app allows the input to add a checklist of things "To Do"
// Clicking them will turn them red to prioritize them;
// Holding them down will remove them from the list.
// Note: hard to do on the emulator, as it's hard to distinguish between holding down
//  one element and clicking the next by accident
package com.example.christina.project2todolist;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> listMain;
    private ArrayAdapter<String> listAdapter;
    private ArrayList<View> isNormal;
    private ArrayList<View> isPriority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        isNormal = new ArrayList<>();
        isPriority = new ArrayList<>();

        ListView listView = (ListView) findViewById(R.id.item_list);
        listMain = new ArrayList<>();
        listAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                listMain);
        listView.setAdapter(listAdapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView parentView, View childView, int position, long id) {

                String itemClicked = listMain.get(position);

                    String completed = "\"" + itemClicked + "\" completed";
                    Toast.makeText(MainActivity.this, completed, Toast.LENGTH_SHORT).show();

                    if (isPriority.contains(childView)) isPriority.remove(childView);
                    isNormal.add(childView);
                    listMain.remove(position);
                    listAdapter.notifyDataSetChanged();

                updateColors();
                return false;
            }

        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parentView, View childView, int position, long id) {

                    if (!isPriority.contains(childView)) {

                        String itemClicked = listMain.get(position);
                        String reminder = "Remember: \n" + itemClicked + " is a priority!";
                        Toast.makeText(MainActivity.this, reminder, Toast.LENGTH_SHORT).show();

                        isPriority.add(childView);
                        if (isNormal.contains(childView)) isNormal.remove(childView);

                    } else {
                        isNormal.add(childView);
                        if(isPriority.contains(childView)) isPriority.remove(childView);
                    }

                updateColors();
            }
        });
    }

    private void updateColors() {
        String normalColor = "#FFFFFF";
        String priorityColor = "#FF0000";

        for (View childView : isNormal) {
            childView.setBackgroundColor(Color.parseColor(normalColor));
        }
        for (View childView : isPriority) {
            childView.setBackgroundColor(Color.parseColor(priorityColor));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putStringArrayList("list", listMain);
    }

    @Override
    protected void onRestoreInstanceState(Bundle bundle) {
        super.onRestoreInstanceState(bundle);
        if (bundle.containsKey("list")) {
            listMain = bundle.getStringArrayList("list");
            listAdapter.notifyDataSetChanged();
        }
    }


    public void addItem(View view) {
        EditText entry = (EditText) findViewById(R.id.enteredText);
        String entryText = entry.getText().toString();
        if (!entryText.equals("")) {
            listMain.add(entryText);
            listAdapter.notifyDataSetChanged();
            entry.setText("");
        }
    }

}
