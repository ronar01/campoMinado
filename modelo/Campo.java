package modelo;

import java.util.List;

import excecao.ExplosaoException;

import java.util.ArrayList;

public class Campo {

    private boolean marcado = false;
    private boolean aberto = false;
    private boolean minado = false;

    private final int LINHA;
    private final int COLUNA;

    List<Campo> vizinhos = new ArrayList<>();
    
    
    Campo(int linha, int coluna){
        this.LINHA = linha;
        this.COLUNA = coluna;
    }

    boolean addVizinho(Campo vizinho){
        boolean linhaDiferente = LINHA != vizinho.LINHA;
        boolean colunaDifrente = COLUNA != vizinho.COLUNA;
        boolean diagonal = linhaDiferente && colunaDifrente;

        int deltaLinha = Math.abs(LINHA - vizinho.LINHA);
        int deltaColuna = Math.abs(COLUNA - vizinho.COLUNA);
        int deltaGeral = deltaLinha + deltaColuna;

        if(deltaGeral == 1 && !diagonal){
            vizinhos.add(vizinho);
            return true;
        } else if(deltaGeral == 2 && diagonal){
            vizinhos.add(vizinho);
            return true;
        } else{
            return false;
        }
    }

    void alternarMarcacao(){
        if(!aberto){
            marcado = !marcado;
        }
    }

    boolean abrir(){

        if(!aberto && !marcado){
            aberto = true;
            if(minado){
                throw new ExplosaoException();   
            }
            if(vizinhancaSegura()){
                vizinhos.forEach(v -> v.abrir());
            }
            return true;
        } else{
            return false;
        }
    }

    boolean vizinhancaSegura () {
        return vizinhos.stream().noneMatch(v -> v.minado);
    }
    
    void minar(){
        minado = true;
    }
    
    public boolean isMinado(){
        return minado;
    }

    public boolean isMarcado(){
        return marcado;
    }
    
    public boolean isAberto(){
        return aberto;
    }

    void setAberto(boolean aberto){
        this.aberto = aberto;
    }

    public boolean isFechado(){
        return !isAberto();
    }

    public int getLINHA() {
        return LINHA;
    }

    public int getCOLUNA() {
        return COLUNA;
    }    

    boolean objetivoAlcancado(){
        boolean desvendado = !minado && aberto;
        boolean protegido = minado && marcado;
        return desvendado || protegido;
    }

    long minasNaVizinhanca(){
        return vizinhos.stream().filter(v -> v.minado).count();
    }
    
    void reiniciar(){
        aberto = false;
        minado = false;
        marcado = false;
    }

    public String toString(){
        if(marcado){
            return "x";
        } else if(aberto && minado){
            return "*";
        } else if(aberto && minasNaVizinhanca() > 0){
            return Long.toString(minasNaVizinhanca());
        } else if(aberto){
            return " ";
        } else{
            return "?";
        }
    }
}
