package de.tallerik.bas_vertretung;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

public class ScrollingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_bas));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://bas-vertretung.de/"));
                startActivity(browserIntent);
            }
        });
        try {
            refresh();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
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


    public void refresh() throws Exception {

        String out = (String) new AsyncDownload().execute("http://tallerikyt.ddns.net/VT/getVT.php").get();
        System.out.println(out);
        TableLayout table = (TableLayout) findViewById(R.id.contentTable);
//
//
//        for (int i = 0; i < 2; i++) {
//            TableRow row = new TableRow(this);
//            LinearLayout layout = new LinearLayout(this);
//            layout.setOrientation(LinearLayout.HORIZONTAL);
//            TextView view = new TextView(this);
//            TextView view2 = new TextView(this);
//            view.setText("1");
//            view.setPadding(10, 5, 10, 0);
//            view2.setText("2");
//            view2.setPadding(10, 5, 10, 0);
//            layout.addView(view);
//            layout.addView(view2);
//            row.addView(layout);
//            table.addView(row);
//        }

        for (int i = 0; i < table.getChildCount(); i++) {
            table.removeView(table.getChildAt(i));
        }


        JSONObject json = new JSONObject(out);

        JSONArray heute = json.getJSONArray("heute");

        TextView heutetext = new TextView(this);
        heutetext.setText("Vertretung Heute");
        LinearLayout heutelayout = new LinearLayout(this);
        heutelayout.setOrientation(LinearLayout.HORIZONTAL);
        heutelayout.addView(heutetext);
        table.addView(heutelayout);

        for (int i = 0; i < heute.length(); i++) {
            JSONArray array = heute.getJSONArray(i);
            LinearLayout layout = new LinearLayout(this);
            layout.setOrientation(LinearLayout.HORIZONTAL);
            for (int j = 0; j < array.length(); j++) {
                TextView text = new TextView(this);
                text.setText(convertUnicode(array.getString(j)));
                text.setPadding(10, 0, 25, 0);
                layout.addView(text);
            }
            table.addView(layout);
        }

        LinearLayout leer = new LinearLayout(this);
        TextView leertext = new TextView(this);
        leertext.setText(" ");
        leer.addView(leertext);
        table.addView(leer);


        JSONArray morgen = json.getJSONArray("morgen");

        TextView morgentext = new TextView(this);
        morgentext.setText("Vertretung Morgen");
        LinearLayout morgenLayout = new LinearLayout(this);
        morgenLayout.setOrientation(LinearLayout.HORIZONTAL);
        morgenLayout.addView(morgentext);
        table.addView(morgenLayout);
        for (int i = 0; i < morgen.length(); i++) {
            JSONArray array = morgen.getJSONArray(i);
            LinearLayout layout = new LinearLayout(this);
            layout.setOrientation(LinearLayout.HORIZONTAL);
            for (int j = 0; j < array.length(); j++) {
                TextView text = new TextView(this);
                String content = array.getString(j);
                text.setText(convertUnicode(content));
                text.setPadding(10, 0, 25, 0);
                layout.addView(text);
            }
            table.addView(layout);
        }



    }

public String convertUnicode(String in) {
        return in.replaceAll("\\\\\\\\xf6", "ö").replaceAll("\\\\\\\\xe4", "ä");
}


}
