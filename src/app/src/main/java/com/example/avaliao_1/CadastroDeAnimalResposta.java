package com.example.avaliao_1;

import java.util.List;

public class CadastroDeAnimalResposta {
    public List<String> MensagemDeErros;
    public Long DiferençaEntreDias;
    public Float GanhoDePesoTotal;
    public Float GanhoDePesoDiario;

    public CadastroDeAnimalResposta() {
        DiferençaEntreDias= 0l;
        GanhoDePesoTotal= 0f;
        GanhoDePesoDiario= 0f;
    }
}
