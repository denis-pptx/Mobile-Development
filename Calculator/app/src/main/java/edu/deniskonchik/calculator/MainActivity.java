package edu.deniskonchik.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import net.objecthunter.exp4j.*;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView resultField;
    private TextView operationField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultField = findViewById(R.id.resultField);
        operationField = findViewById(R.id.operationField);

        GridLayout gridLayout = findViewById(R.id.buttonGrid);
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            View view = gridLayout.getChildAt(i);
            if (view instanceof AppCompatButton) {
                AppCompatButton button = (AppCompatButton) view;
                button.setOnClickListener((View v) -> onButtonClick(button));
            }
        }
    }

    protected void onButtonClick(AppCompatButton button){
        String text = button.getText().toString();

        if (text.equals("sqrt") || text.equals("log2") ||
                text.equals("log") || text.equals("sin") ||
                text.equals("cos"))
        {
            operationField.append(text + "(");
        }
        else if (text.equals("x^y")) {
            operationField.append("^");
        }
        else if (text.equals("AC")) {
            resultField.setText("");
            operationField.setText("");
        }
        else if (text.equals("back")) {
            String s = operationField.getText().toString();
            if (!s.isEmpty()) {
                operationField.setText(s.substring(0, s.length() - 1));
            }
        }
        else if (text.equals("=")) {
            String operationText = operationField.getText().toString();
            if (!operationText.isEmpty()) {
                try {
                    Expression expression = new ExpressionBuilder(operationText).build();
                    double result = expression.evaluate();
                    resultField.setText(Double.toString(result));

                } catch (Exception exception) {
                    resultField.setText("Invalid input");
                }
            }
        }
        else {
            operationField.append(text);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("result", resultField.getText().toString());
        outState.putString("operation", operationField.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String result = savedInstanceState.getString("result");
        String operation = savedInstanceState.getString("operation");
        resultField.setText(result);
        operationField.setText(operation);
    }


}