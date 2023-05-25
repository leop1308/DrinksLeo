package com.drinksleo.dao;

import java.util.ArrayList;
import java.util.List;

public enum MeasureTypes {
    ML("ml"),
    RODELA("Rodela"),
    PEDACO("Pedaço"),
    UNIT("Unidade"),
    COMPLETAR("Completar"),
    BLOCO("Blocos");

    private String descricao;

    MeasureTypes(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

}
