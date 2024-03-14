package edu.deniskonchik.calculator.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;

import edu.deniskonchik.calculator.R;
import edu.deniskonchik.calculator.models.CalculatorViewModel;

public class MainActivity extends AppCompatActivity {

    private TextView resultField;
    private TextView operationField;
    private CalculatorViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultField = findViewById(R.id.resultField);
        operationField = findViewById(R.id.operationField);

        viewModel = new ViewModelProvider(this).get(CalculatorViewModel.class);
        viewModel.operation.observe(this, operation -> {
            operationField.setText(operation);
        });
        viewModel.result.observe(this, result -> {
            resultField.setText(result);
        });

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

        switch (text) {
            case "sqrt":
            case "log2":
            case "log":
            case "sin":
            case "cos":
                viewModel.Append(text + "(");
                break;
            case "x^y":
                viewModel.Append("^");
                break;
            case "AC":
                viewModel.Clear();
                break;
            case "back":
                viewModel.RemoveLast();
                break;
            case "=":
                viewModel.Evaluate();
                break;
            default:
                viewModel.Append(text);
                break;
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