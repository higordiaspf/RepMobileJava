package com.example.avaliao_1;

import android.os.Build;
import android.text.TextUtils;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CadastroDeAnimalService {
    @RequiresApi(api = Build.VERSION_CODES.O)
    public CadastroDeAnimalResposta Calcular(CadastroDeAnimal animal) {
        CadastroDeAnimalResposta resposta = new CadastroDeAnimalResposta();
        resposta.MensagemDeErros = MensagensDeErros(animal);
        if (!resposta.MensagemDeErros.isEmpty()) {
            return resposta;
        }
        resposta.GanhoDePesoTotal = animal.PesoAtual - animal.PesoNascimento;
        long diff = animal.DataDaPesagemAtual.getTime() - animal.DataDeNascimento.getTime();
        resposta.DiferençaEntreDias = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

        resposta.GanhoDePesoDiario = resposta.GanhoDePesoTotal / resposta.DiferençaEntreDias;

        return resposta;


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private List<String> MensagensDeErros(CadastroDeAnimal animal) {
        List<String> mensagensDeErro = new ArrayList<String>();

        if (TextUtils.isEmpty(animal.NumeroDoBrinco) || animal.NumeroDoBrinco == null) {
            mensagensDeErro.add("Numero do Brinco invalido");
        }

        Date now = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());

        if (animal.DataDeNascimento == null || animal.DataDeNascimento == now || animal.DataDeNascimento.after(now)) {
            mensagensDeErro.add("Data de nascimento invalida");
        }
        if (!animal.DataDaPesagemAtual.equals(now)) {
            mensagensDeErro.add("Data de pesagem invalida");
        }
        if (animal.PesoAtual <= 0 || TextUtils.isEmpty(animal.PesoAtual.toString())) {
            mensagensDeErro.add("Peso atual invalido");
        }
        if (animal.PesoNascimento <= 0) {
            mensagensDeErro.add("Peso do nascimento invalido");
        }
        if (!(animal.Sexo == 'M' || animal.Sexo == 'F')) {
            mensagensDeErro.add("Sexo invalido");
        }
        return mensagensDeErro;
    }

    private boolean StringIsEmptyOrNull(String string) {
        return string == null || string.equals("") || string.trim().equals("");
    }
}
