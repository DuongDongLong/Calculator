package com.example.jiren.calculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "CALCULATOR";

    private double firstNumber = Double.MIN_VALUE;
    private double secondNumber = Double.MIN_VALUE;
    private String operator = null;
    private TextView resultTextView;
    private TableLayout mTableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        resultTextView = findViewById(R.id.txtResult);
        mTableLayout = findViewById(R.id.table_Layout);
        setEvenButton();
    }

    private void setEvenButton() {
        int rowCount = mTableLayout.getChildCount();
        for (int i = 0; i < rowCount; i++) {
            View mView = mTableLayout.getChildAt(i);
            if (mView instanceof TableRow) {
                TableRow mTableRow = (TableRow) mView;
                int tableRowCount = mTableRow.getChildCount();
                for (int j = 0; j < tableRowCount; j++) {
                    mView = mTableRow.getChildAt(j);
                    if (mView instanceof Button) mView.setOnClickListener(this);
                }
            }
        }
    }

    @Override
    public void onClick(View view) {
        if (view instanceof Button) {
            Button mButton = (Button) view;
            String strTextButton = mButton.getText().toString();
            int buttonNumber = isInteger(strTextButton);

            String result = resultTextView.getText().toString();
            if (buttonNumber != -1) {

                if ("0".equalsIgnoreCase(result)
                        || "+".equalsIgnoreCase(operator)
                        || "x".equalsIgnoreCase(operator)
                        || "-".equalsIgnoreCase(operator)
                        || "/".equalsIgnoreCase(operator)) {
                    resultTextView.setText(strTextButton);
                } else {
                    resultTextView.setText(result + strTextButton);
                }
            } else if ("+".equalsIgnoreCase(strTextButton)
                    || "-".equalsIgnoreCase(strTextButton)
                    || "x".equalsIgnoreCase(strTextButton)
                    || "/".equalsIgnoreCase(strTextButton)) {
                Log.e(TAG, "onClick: " + firstNumber + " " + secondNumber);
                /*if (firstNumber != Double.MIN_VALUE) {
                    secondNumber = Double.parseDouble(result);
                } else {
                    firstNumber = Double.parseDouble(result);
                }*/
                firstNumber = Double.parseDouble(result);
                Log.e(TAG, "onClick: " + firstNumber + " " + secondNumber);
                operator = strTextButton;
            } else if ("=".equals(strTextButton)) {

                secondNumber = Double.parseDouble(result);
                functionEquals();
            }
            else if("AC".equalsIgnoreCase(strTextButton)) clearResult();
            else if("%".equals(strTextButton)){
                Log.e(TAG, "onClick: %%%%%%%%%%"+result );
                double phantram= Double.parseDouble(result)/100;
                resultTextView.setText(String.valueOf(phantram));
            }
           /* else if ("+/-".equals(strTextButton))
                if(Double.parseDouble(result)>0)
                    resultTextView.setText("-"+result);
                else if (Double.parseDouble(result)==0)
                    resultTextView.setText(String.valueOf(-Integer.parseInt(result)));
                else
                    resultTextView.setText(String.valueOf(-Double.parseDouble(result)));*/
        }
    }

    private void functionEquals() {

        if (firstNumber != Double.MIN_VALUE && secondNumber != Double.MIN_VALUE) {
            Log.e(TAG, "functionEquals: ");
            double resultNumber = 0;
            if ("+".equals(operator)) {
                resultNumber = firstNumber + secondNumber;
            } else if ("-".equals(operator)) {
                resultNumber = firstNumber - secondNumber;
            } else if ("x".equals(operator)) {
                resultNumber = firstNumber * secondNumber;
            } else if ("/".equals(operator)) {
                resultNumber = firstNumber / secondNumber;
            }

            resultTextView.setText(String.valueOf(resultNumber));
            firstNumber = resultNumber;
            secondNumber = Double.MIN_VALUE;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menuClear) clearResult();
        if (item.getItemId() == R.id.menuSaveResult) resultTextView.setText("23123123");
        return super.onOptionsItemSelected(item);
    }

    public int isInteger(String s) {
        int value = -1;
        try {
            value = Integer.parseInt(s);
        } catch (NumberFormatException ex) {
            Log.e(TAG, "isInteger: " + ex.getMessage());
        } finally {
            return value;
        }
    }

    public void clearResult() {
        firstNumber = Double.MIN_VALUE;
        secondNumber = Double.MIN_VALUE;
        resultTextView.setText("0");
        operator = null;
    }
}
