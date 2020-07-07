package comp3350.pbbs.presentation.addObject;

import androidx.appcompat.app.AppCompatActivity;

import comp3350.pbbs.R;
import comp3350.pbbs.business.AccessBudgetCategory;
import comp3350.pbbs.business.AccessCreditCard;
import comp3350.pbbs.business.AccessTransaction;
import comp3350.pbbs.objects.BudgetCategory;
import comp3350.pbbs.objects.CreditCard;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class addTransaction extends AppCompatActivity
    implements OnItemSelectedListener
{
    DatePickerDialog datePickerDialog;
    EditText dateText;
    TimePickerDialog timePickerDialog;
    EditText timeText;
    final Calendar c = Calendar.getInstance();
    AccessTransaction accessTransaction;
    AccessCreditCard accessCreditCard;
    AccessBudgetCategory accessBudget;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Add Transaction");

        ///////// Date Input //////////
        accessTransaction = new AccessTransaction();
        dateText = findViewById(R.id.dateInput);
        dateText.setOnClickListener(v -> dateText.setOnClickListener(v1 ->
        {
             datePickerDialog = new DatePickerDialog(addTransaction.this,
                                                     (view, year1, monthOfYear, dayOfMonth) ->
                                                     {
                                                         dateText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year1);
                                                     }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
             datePickerDialog.show();
        }));

        ///////// Time Input //////////
        timeText = findViewById(R.id.timeInput);
        timeText.setOnClickListener(v -> timeText.setOnClickListener(v1 ->
        {
             timePickerDialog = new TimePickerDialog(addTransaction.this,
                                                     (timePicker, hourOfDay, minute) ->
                                                     {
                                                         timeText.setText(hourOfDay + ":" + minute);
                                                     }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), false);
             timePickerDialog.show();
        }));

        ///////// Card Selector //////////
        accessCreditCard = new AccessCreditCard();
        List<String> cardList = new ArrayList<>();
        ArrayList<CreditCard> cardArrayList = accessCreditCard.getCreditCards();
        cardList.add("Select card");
        for (CreditCard c : cardArrayList)
        {
            cardList.add(c.getCardName() + "\n" + c.getCardNum());
        }
        Spinner cardSelector = findViewById(R.id.cardSelector);
        cardSelector.setOnItemSelectedListener(this);
        cardSelector.setAdapter(new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, cardList));

        ///////// Budget Selector //////////
        accessBudget = new AccessBudgetCategory();
        List<String> budgetList = new ArrayList<>();
        ArrayList<BudgetCategory> budgetArrayList = accessBudget.getAllBudgetCategories();
        cardList.add("Select budget category");
        for (BudgetCategory b : budgetArrayList)
        {
//            cardList.add(b.getCardName() + "\n" + b.getCardNum());
            // TODO: display budget category in the dropdown menu
        }
        Spinner BudgetSelector = findViewById(R.id.budgetSelector);
        BudgetSelector.setOnItemSelectedListener(this);
        cardSelector.setAdapter(new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, budgetList));


        ///////// Add Transaction Button //////////
        findViewById(R.id.addTransSubmit).setOnClickListener(view ->
        {
            boolean valid = true;

            // validate fields, use methods from business class
            if (!accessTransaction.isValidDateTime(dateText.getText().toString(), timeText.getText().toString()))
            {
                timeText.setError("Invalid time.");
                dateText.setError("Invalid date.");
                valid = false;
            }
            if (!accessTransaction.isValidAmount(((EditText) findViewById(R.id.addTransAmount)).getText().toString()))
            {
                ((EditText) findViewById(R.id.addTransAmount)).setError("Invalid amount.");
                valid = false;
            }
            if (!accessTransaction.isValidDescription(((EditText) findViewById(R.id.addTransDescription)).getText().toString()))
            {
                ((EditText) findViewById(R.id.addTransDescription)).setError("Invalid description.");
                valid = false;
            }
            if (BudgetSelector.getSelectedItemPosition() - 1 == -1)
            {
                // TODO: setBackgroundColor didn't work, user should be clearly notified they didn't select a card.
                ((Spinner) findViewById(R.id.cardSelector)).setBackgroundColor(0xFF6666);
                valid = false;
            }

//            String s = cardArrayList.get(cardSelector.getSelectedItemPosition()-1).toString();
//            Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();


            if  (valid && accessTransaction.addTransaction
                (
                    ((EditText) findViewById(R.id.addTransDescription)).getText().toString(),
                    dateText.getText().toString(),
                    timeText.getText().toString(),
                    ((EditText) findViewById(R.id.addTransAmount)).getText().toString(),
                    cardArrayList.get(BudgetSelector.getSelectedItemPosition()-1),
                    null   // TODO: budgetCategory selector
                ))
            {
                Snackbar.make(view, "Transaction Added!", Snackbar.LENGTH_SHORT)
                        .addCallback(new Snackbar.Callback()
                        {
                            @Override
                            public void onDismissed(Snackbar transientBottomBar, int event)
                            {
                                super.onDismissed(transientBottomBar, event);
                                finish();
                            }
                        }).show();
            }
            else
            {
                Snackbar.make(view, "Failed to add Transaction.", Snackbar.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id)
    {
//        Toast.makeText(getApplicationContext(), "onItemSelected\nposition: " + position + ", id: " + id, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView)
    {
//        Toast.makeText(getApplicationContext(), "onNothingSelected", Toast.LENGTH_LONG);
    }
}