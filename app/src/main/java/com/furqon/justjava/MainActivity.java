package com.furqon.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    int quantityCoffee = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText nameInput = findViewById(R.id.et_name);
        String isName = nameInput.getText().toString();

        CheckBox whippedCreamCheckBox = findViewById(R.id.cb_whipped_cream);
        boolean hasWhippedcream = whippedCreamCheckBox.isChecked();

        CheckBox chocolateCheckBox = findViewById(R.id.cb_chocolate);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        int price = calculatePrice(hasWhippedcream, hasChocolate);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:mariff@gmail.com")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.subject) + isName);
        intent.putExtra(Intent.EXTRA_TEXT, createOrderSummary(price, hasWhippedcream, hasChocolate, isName));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

        displayMessage(createOrderSummary(price, hasWhippedcream, hasChocolate, isName));
    }

    private String createOrderSummary(int price, boolean hasWhippedCream, boolean hasChocolate, String isName) {
        String priceMessage = getString(R.string.name) + isName;
        priceMessage += "\n" + (getString(R.string.whippedcream) + hasWhippedCream);
        priceMessage += "\n" + (getString(R.string.choco) + hasChocolate);
        priceMessage += "\n" + (getString(R.string._quantity) + quantityCoffee);
        priceMessage += "\n" + (getString(R.string.totalorder) + price);
        priceMessage += "\n" + (getString(R.string.thankyou));
        return priceMessage;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int quantity) {
        TextView quantityTextView = findViewById(R.id.tv_quantity);
        quantityTextView.setText("" + quantity);
    }

    public void increment(View view) {
        if (quantityCoffee == 100) {
            Toast.makeText(this, R.string.more100, Toast.LENGTH_SHORT).show();
            return;
        }
        quantityCoffee = quantityCoffee + 1;
        displayQuantity(quantityCoffee);
    }

    public void decrement(View view) {
        if (quantityCoffee == 1) {
            Toast.makeText(this, R.string.less1, Toast.LENGTH_SHORT).show();
            return;
        }
        quantityCoffee = quantityCoffee - 1;
        displayQuantity(quantityCoffee);
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = findViewById(R.id.tv_order_summary);
        orderSummaryTextView.setText(message);
    }

    /**
     * Calculates the price of the order based on the current quantity.
     *
     * @return the price
     */
    private int calculatePrice(boolean hasWhippedcream, boolean hasChocolate) {

        int basePrice = 5;

        if (hasWhippedcream) {
            basePrice = basePrice + 1;
        }

        if (hasChocolate) {
            basePrice = basePrice + 2;
        }

        return quantityCoffee * basePrice;
    }
}
