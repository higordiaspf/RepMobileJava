package com.example.avaliao_1;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity {

    private TextView tv_Cadastro_de_Animal;
    private TextView tv_Numero_de_brinco;
    private EditText et_Numero_de_brinco;
    private TextView tv_Data_Nascimento;
    private EditText et_Data_Nascimento;
    private TextView tv_Peso_Nascimento;
    private EditText et_Peso_Nascimento;
    private TextView tv_Data_Pesagem_Atual;
    private EditText et_Data_Pesagem_Atual;
    private TextView tv_Peso_Atual;
    private EditText et_Peso_Atual;
    private TextView et_Resultado_DiferençaEntreDias;
    private TextView et_Resultado_GanhoDiario;
    private TextView et_Resultado_Pesototal;
    private TextView et_erros;
    private CheckBox check_Fêmea;
    private CheckBox check_Macho;
    private Button calcular;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_Cadastro_de_Animal = findViewById(R.id.tv_Cadastro_de_Animal);

        tv_Numero_de_brinco = findViewById(R.id.tv_Numero_de_brinco);
        et_Numero_de_brinco = findViewById(R.id.et_Numero_de_brinco);

        tv_Data_Nascimento = findViewById(R.id.tv_Data_Nascimento);
        et_Data_Nascimento = findViewById(R.id.et_Data_Nascimento);

        tv_Peso_Nascimento = findViewById(R.id.tv_Peso_Nascimento);
        et_Peso_Nascimento = findViewById(R.id.et_Peso_Nascimento);

        tv_Data_Pesagem_Atual = findViewById(R.id.tv_Data_Pesagem_Atual);
        et_Data_Pesagem_Atual = findViewById(R.id.et_Data_Pesagem_Atual);

        tv_Peso_Atual = findViewById(R.id.tv_Peso_Atual);
        et_Peso_Atual = findViewById(R.id.et_Peso_Atual);

        check_Fêmea = findViewById(R.id.check_Fêmea);
        check_Macho = findViewById(R.id.check_Macho);

        calcular = findViewById(R.id.calcular);
        et_Resultado_DiferençaEntreDias = findViewById(R.id.et_Resultado_DiferençaEntreDias);
        et_Resultado_GanhoDiario = findViewById(R.id.et_Resultado_GanhoDiario);
        et_Resultado_Pesototal = findViewById(R.id.et_Resultado_Pesototal);
        et_erros = findViewById(R.id.et_erros);

        CalcularTempoDePesagem(calcular);


    }


    public void CalcularTempoDePesagem(View V) {
        CadastroDeAnimalService service = new CadastroDeAnimalService();
        SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
        calcular.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {

                et_erros.setText(" ");
                et_Resultado_DiferençaEntreDias.setText(" ");
                et_Resultado_Pesototal.setText(" ");
                et_Resultado_GanhoDiario.setText(" ");

                CadastroDeAnimal animal = new CadastroDeAnimal();
                animal.NumeroDoBrinco = et_Numero_de_brinco.getText().toString();
                try {
                    animal.DataDeNascimento = formatoData.parse(et_Data_Nascimento.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (verificaString(et_Peso_Nascimento.getText().toString())) {
                    animal.PesoNascimento = Float.parseFloat(et_Peso_Nascimento.getText().toString());
                }
                // animal.PesoNascimento = Float.parseFloat(et_Peso_Nascimento.getText().toString());
                try {
                    animal.DataDaPesagemAtual = formatoData.parse(et_Data_Pesagem_Atual.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (verificaString(et_Peso_Atual.getText().toString())) {
                    animal.PesoAtual = Float.parseFloat(et_Peso_Atual.getText().toString());
                }
                // animal.PesoAtual = Float.parseFloat(et_Peso_Atual.getText().toString());
                animal.Sexo = obterSexo(check_Macho.isChecked(), check_Fêmea.isChecked());
                CadastroDeAnimalResposta resposta = service.Calcular(animal);

                if (resposta.MensagemDeErros.isEmpty()) {
                    et_Resultado_DiferençaEntreDias.setText(resposta.DiferençaEntreDias.toString());
                    et_Resultado_Pesototal.setText(resposta.GanhoDePesoTotal.toString());
                    et_Resultado_GanhoDiario.setText(resposta.GanhoDePesoDiario.toString());

                } else {
                    et_erros.setText(resposta.MensagemDeErros.toString());
                }
            }

        });


    }


    public boolean verificaString(String string) {
        try {
            Float.parseFloat(string);

        } catch (NumberFormatException exception) {
            return false;
        }
        return true;
    }

    private char obterSexo(boolean c1, boolean c2) {
        if (c1 && c2) {
            return 'N';
        }
        if (c1) {
            return 'M';
        }
        if (c2) {
            return 'F';
        }
        return 'N';
    }


}