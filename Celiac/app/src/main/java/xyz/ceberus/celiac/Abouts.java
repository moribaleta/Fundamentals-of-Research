package xyz.ceberus.celiac;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class Abouts extends AppCompatActivity {

    String str1stBlock ="CDiag is a system that can diagnose if you have a Celiac Disease,\n" +
            "this system has no capability of recommending the initial treatment.\n" +
            "It is recommended that you have to be 16 yrs old to be able to use this app.";
    String str2ndBlock ="Celiac disease (CD) is an autoimmune disorder that’s triggered by eating gluten-containing\n" +
            "food such as bread products. Gluten-containing food damages the intestinal villi of people with CD, \n" +
            "which results in impaired absorption of nutrients and its consequences, i.e., malnutrition, \n" +
            "osteoporosis, and iron deficiency.";

    String strCredits = "" +
            "this application is developed by:\n\n" +
            "\u2022 Baleta, Gabriel Mori\n" +
            "\u2022 Pajanustan, Joven \n";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abouts);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView textViewAbouts = (TextView)findViewById(R.id.textAbouts);
        textViewAbouts.setText(str1stBlock);
        TextView textViewCeliac = (TextView)findViewById(R.id.textCeliac);
        textViewCeliac.setText(str2ndBlock);
        TextView textCredit = (TextView)findViewById(R.id.textCredits);
        textCredit.setText(strCredits);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }
}
