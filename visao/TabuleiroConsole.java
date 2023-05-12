package visao;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

import excecao.ExplosaoException;
import excecao.SairException;
import modelo.Tabuleiro;

public class TabuleiroConsole {
    private Tabuleiro tabuleiro;
    private Scanner input = new Scanner(System.in);

    public TabuleiroConsole(Tabuleiro tabuleiro){
        this.tabuleiro = tabuleiro;
        executarJogo();
    }

    private void executarJogo(){
        try{
            boolean continuar = true;
            while(continuar){
                loop();
                System.out.println("Quer iniciar outra partida? (S/n) ");
                String resposta = input.nextLine();
                if(resposta.equalsIgnoreCase("n")){
                    continuar = false;
                }else{
                    tabuleiro.reiniciar();
                }
            }

        }catch(SairException e){
            System.out.println("Saindo...");
        } finally{
            input.close();
        }
    }

    private void loop(){
        try{
            while(!tabuleiro.objetivoAlcancado()){
                System.out.println(tabuleiro);
                String digitado = pegarValorDigitado("Digite (i, j): ");
                Iterator<Integer> ij = Arrays.stream(digitado.split(",")).map(e -> Integer.parseInt(e.trim())).iterator();
                digitado = pegarValorDigitado("1 - abrir ou 2 - (Des)marcar: ");
                if("1".equalsIgnoreCase(digitado)){
                    tabuleiro.abrir(ij.next(), ij.next());
                } else if("2".equalsIgnoreCase(digitado)){
                    tabuleiro.alternarMarcacao(ij.next(), ij.next());
                }
            }
            System.out.println(tabuleiro);
            System.out.println("Você ganhou!");
        } catch(ExplosaoException e){
            System.out.println(tabuleiro);
            System.out.println("Você perdeu!");
        }
    }

    private String pegarValorDigitado(String texto){
        System.out.print(texto);
        String digitado = input.nextLine();
        if(digitado.equalsIgnoreCase("sair")){
            throw new SairException();
        }
        return digitado;
    }
}
