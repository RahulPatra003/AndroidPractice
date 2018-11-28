package com.example.rahulp.myfirstapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {
    int quantity = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void increment(View view) {
        if (quantity==100){
            return;
        }
        quantity = quantity+1;
        display(quantity);
    }

    public void decrement(View view) {
        if (quantity ==1){
            return;
        }
        quantity = quantity-1;
        display(quantity);
    }

    public void submitOrder(View view) {
        EditText namefield = (EditText) findViewById(R.id.name_field);
        String Name = namefield.getText().toString();
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkBox);
        boolean hasChocolate = chocolateCheckBox.isChecked();
        //Log.v("MainActivity", "has whipped cream" + hasWhippedCream);
        int price = calculatePrice(hasWhippedCream , hasChocolate);
        String pricemessage=createOrderSummary(price, hasWhippedCream , hasChocolate, Name);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(intent.EXTRA_SUBJECT, "Coffee Order For " + Name);
        intent.putExtra(intent.EXTRA_TEXT,pricemessage);
        if (intent.resolveActivity(getPackageManager()) !=null){
            startActivity(intent);
        }
        //displayMessage(pricemessage);

    }

    private int calculatePrice(boolean addWhippedCream,boolean addChocolate){
        int basePrice = 95;
        if (addWhippedCream){
            basePrice = basePrice+25;
        }
        if (addChocolate){
            basePrice = basePrice+30;
        }
        return  quantity * basePrice;
    }


    private String createOrderSummary(int price, boolean hasWhippedCream ,boolean hasChocolate , String Name){
        String pricemessage="Name: " + Name;
        pricemessage += "\nAdd Whipped Cream? " + hasWhippedCream;
        pricemessage += "\nAdd Chocolate? " + hasChocolate;
        pricemessage = pricemessage + "\nQuantity: " + quantity;
        pricemessage = pricemessage + "\nTotal=Rs." + price  +"\n Thank You!!";
        return pricemessage;
    }

    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    //private void displayMessage(String message){
    //TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
    //orderSummaryTextView.setText(message);}
}
