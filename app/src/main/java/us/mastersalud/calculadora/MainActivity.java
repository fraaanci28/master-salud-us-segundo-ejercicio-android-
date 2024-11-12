package us.mastersalud.calculadora;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Button[] buttons = new Button[10];
    private int[] ids = {R.id.button0, R.id.button1, R.id.button2,
    R.id.button3,R.id.button4,R.id.button5,R.id.button6,R.id.button7,
    R.id.button8,R.id.button9};
    private Button buttonDelete;
    private int result, counterRight = 0, counterFail = 0;
    private TextView resultTextView, operationTextView, rightTextView, failTextView;

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    //Enlace entre layout y lógica: findView

    //operationTextView: TextView donde aparece la operación aleatoria
    operationTextView = findViewById(R.id.operationTextView);
    //rightTextView: TextView con el número de aciertos
    rightTextView = findViewById(R.id.rightTextView);
    //failTextView = TextView con el número de fallos
    failTextView = findViewById(R.id.failTextView);
    //resultTextView = TextView con el resultado
    resultTextView = findViewById(R.id.resultTextView);
    //buttonDelete
    buttonDelete = findViewById(R.id.buttonBorrar);
    //Botones de la calculadora
    for (int i = 0; i<ids.length; i++){
        buttons[i] = findViewById(ids[i]);
        buttons[i].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultTextView.setText(resultTextView.getText().toString() + ((Button)v).getText().toString());
            }
        });
    }

    //Boton de borrado
    buttonDelete.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String actual = resultTextView.getText().toString();
            if(actual.length() > 0){
                resultTextView.setText(actual.substring(0,actual.length()-1));
            }
        }
    });

    resultTextView.addTextChangedListener(new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            boolean acierto = false;
            //Comprobación si acierto o no
            if(s.length() > 0) {
                int textChanged = Integer.parseInt(s.toString());
                if (textChanged == result) {
                    acierto = true;
                }

                if (acierto) {
                    Toast.makeText(MainActivity.this, "¡Acierto!", Toast.LENGTH_SHORT).show();
                    counterRight++;
                    rightTextView.setText("Aciertos: " + counterRight);

                    // Eliminamos temporalmente el TextWatcher antes de limpiar el TextView
                    resultTextView.removeTextChangedListener(this);
                    resultTextView.setText(""); // Reinicia el campo de resultado para la próxima operación
                    resultTextView.addTextChangedListener(this); // Vuelve a agregar el TextWatcher

                    generateOperation();
                } else {
                    Toast.makeText(MainActivity.this, "¡Fallo!", Toast.LENGTH_SHORT).show();
                    counterFail++;
                    failTextView.setText("Fallos: " + counterFail);
                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }

        
    });
// Genera la primera operación
    generateOperation();
}

    private void generateOperation() {
        Random random = new Random();
        int num1 = random.nextInt(5);
        int num2 = random.nextInt(4);
        boolean isAddition = random.nextBoolean();
        String operationText;
        if (isAddition) {
            result = num1 + num2;
            operationText = num1 + " + " + num2;
        } else if(num1>num2) {
            result = num1 - num2;
            operationText = num1 + " - " + num2;
        }else{
            result = num2 - num1;
            operationText = num2 + " - " + num1;
        }
        operationTextView.setText(operationText);
    }

}