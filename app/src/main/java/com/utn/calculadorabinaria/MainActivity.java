package com.utn.calculadorabinaria;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // Variables de Views
    TextView lbl_Resultado;
    Button btn_Igual, btn_Suma, btn_Resta, btn_Multiplicacion,
            btn_Division, btn_Borrar, btn_Limpiar, btn_0, btn_1;

    // Variables de cálculo
    String numeroActual = "";
    String operador = "";
    String operando1 = "";
    String operando2 = "";

    // Variable para almacenar el botón de operación activo
    Button botonOperacionActivo = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Instanciar Views
        lbl_Resultado = findViewById(R.id.lbl_Resultado);
        btn_Igual = findViewById(R.id.btn_Igual);
        btn_Suma = findViewById(R.id.btn_Suma);
        btn_Resta = findViewById(R.id.btn_Resta);
        btn_Multiplicacion = findViewById(R.id.btn_Multiplicacion);
        btn_Division = findViewById(R.id.btn_Division);
        btn_1 = findViewById(R.id.btn_1);
        btn_0 = findViewById(R.id.btn_0);
        btn_Borrar = findViewById(R.id.btn_Borrar);
        btn_Limpiar = findViewById(R.id.btn_Limpiar);

        // Asignar listeners a los botones numéricos
        btn_1.setOnClickListener(v -> agregarNumero("1"));
        btn_0.setOnClickListener(v -> agregarNumero("0"));

        // Asignar listeners a los botones de operaciones
        btn_Suma.setOnClickListener(v -> agregarOperador(btn_Suma, "+"));
        btn_Resta.setOnClickListener(v -> agregarOperador(btn_Resta, "-"));
        btn_Multiplicacion.setOnClickListener(v -> agregarOperador(btn_Multiplicacion, "*"));
        btn_Division.setOnClickListener(v -> agregarOperador(btn_Division, "/"));

        // Botón igual para realizar el cálculo
        btn_Igual.setOnClickListener(v -> calcularResultado());

        // Botón para limpiar la operación actual
        btn_Limpiar.setOnClickListener(v -> limpiar());

        // Botón para borrar el último carácter
        btn_Borrar.setOnClickListener(v -> borrarUltimo());
    }

    // Función para agregar número al input
    private void agregarNumero(String num) {
        numeroActual += num;
        lbl_Resultado.setText(numeroActual);
    }

    // Función para agregar operador y cambiar el color del botón
    private void agregarOperador(Button boton, String op) {
        if (!numeroActual.isEmpty()) {
            operando1 = numeroActual;
            operador = op;
            numeroActual = "";

            // Cambiar el color del botón de operación activo
            resetearColorBoton();
            boton.setBackgroundColor(getResources().getColor(R.color.boton_operacion_activo));
            botonOperacionActivo = boton;
        }
    }

    // Función para realizar el cálculo
    private void calcularResultado() {
        if (!numeroActual.isEmpty() && !operando1.isEmpty() && !operador.isEmpty()) {
            operando2 = numeroActual;
            int num1 = Integer.parseInt(operando1, 2); // Convertir binario a decimal
            int num2 = Integer.parseInt(operando2, 2);
            int resultado = 0;

            switch (operador) {
                case "+":
                    resultado = num1 + num2;
                    break;
                case "-":
                    resultado = num1 - num2;
                    break;
                case "*":
                    resultado = num1 * num2;
                    break;
                case "/":
                    if (num2 != 0) {
                        resultado = num1 / num2;
                    } else {
                        lbl_Resultado.setText("Error");
                        return;
                    }
                    break;
            }

            // Convertir el resultado a binario y mostrarlo
            lbl_Resultado.setText(Integer.toBinaryString(resultado));
            numeroActual = Integer.toBinaryString(resultado);
            operando1 = "";
            operando2 = "";
            operador = "";

            // Restaurar el color del botón de operación al calcular
            resetearColorBoton();
        }
    }

    // Función para restaurar el color del botón de operación activo
    private void resetearColorBoton() {
        if (botonOperacionActivo != null) {
            botonOperacionActivo.setBackgroundColor(getResources().getColor(R.color.boton_operacion));
            botonOperacionActivo = null;
        }
    }

    // Función para limpiar todo
    private void limpiar() {
        numeroActual = "";
        operando1 = "";
        operando2 = "";
        operador = "";
        lbl_Resultado.setText("0");
        resetearColorBoton(); // Restaurar el color si hay un botón activo
    }

    // Función para borrar el último carácter ingresado
    private void borrarUltimo() {
        if (!numeroActual.isEmpty()) {
            numeroActual = numeroActual.substring(0, numeroActual.length() - 1);
            lbl_Resultado.setText(numeroActual.isEmpty() ? "0" : numeroActual);
        }
    }
}
