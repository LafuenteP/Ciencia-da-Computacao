import java.util.*;

abstract class Embarcacao {
    String name;
    int espaço;
    int[][] posicao; // Matriz para armazenar a posição da embarcação no tabuleiro

    Embarcacao(String name, int espaço) {
        this.name = name;
        this.espaço = espaço;
        this.posicao = new int[espaço][2]; // Cada posição tem duas coordenadas (linha e coluna)
    }

        // Método para verificar se a posição foi atingida
    public boolean foiAtingida(int linha, int coluna) {
        for (int i = 0; i < espaço; i++) {
            if (posicao[i][0] == linha && posicao[i][1] == coluna) {
                return true;
            }
        }
        return false;
    }
}

class Navio extends Embarcacao {
    Navio() {
        super("Navio", 4); // Passando um int (4) para o campo espaço
    }
}


class Barco extends Embarcacao {
    Barco(){
        super("Barco", 2);
    }
}


class Tabuleiro {

    private int TamanhoX = 0, TamanhoY = 0;
    private int totalNavios;
    private int limeteNavios;
    private int Board[][] = new int[TamanhoX][TamanhoY];;
    private boolean[][] acertos = gerarAcertos(TamanhoX, TamanhoY);

    public Tabuleiro() {
        this.TamanhoX = 10;
        this.TamanhoY = 10;
        this.Board = new int[TamanhoX][TamanhoY];
        this.acertos = gerarAcertos(TamanhoX, TamanhoY);
    }

    public Tabuleiro(int i, int j) {
        this.TamanhoX = i;
        this.TamanhoY = j;
        this.Board = new int[TamanhoX][TamanhoY];
        this.acertos = gerarAcertos(i, j);
    }

    public void obterTamanho() {
        Scanner input = new Scanner(System.in);
        boolean tudoCerto = false;
        do {
            try {
                System.out.println("\n\nDigite a largura do tabuleiro: ");
                this.TamanhoX = input.nextInt();
                System.out.println("Digite a altura do tabuleiro: ");
                this.TamanhoY = input.nextInt();
                tudoCerto = true;
            } catch (InputMismatchException erro) {
                System.out.println("Valor inválido, digite um valor numérico: ");
                input.next(); // Limpa o buffer do Scanner após erro
            }
        } while (!tudoCerto);
        this.Board = new int[TamanhoX][TamanhoY];
        this.limeteNavios = (TamanhoX + TamanhoY)/3;
        this.totalNavios = this.limeteNavios;
    }

    public boolean verificarPosicaoLivre(int linha, int coluna) {
        return Board[linha][coluna] == 0; // Verifica se a posição está livre (valor 0)
    }

    public void posicionarEmbarcacaoAleatoria() {
        int naviosRestantes = limeteNavios;
        Random random = new Random();

        while (naviosRestantes > 0) {
            Embarcacao embarcacao;
            if (random.nextBoolean()) {
                embarcacao = new Navio();
            } else {
                embarcacao = new Barco();
            }

            int espaço = embarcacao.espaço;
            String orientacao;
            int linha, coluna;
            boolean posicaoValida;

            do {
                orientacao = random.nextBoolean() ? "horizontal" : "vertical";
                linha = random.nextInt(TamanhoX);
                coluna = random.nextInt(TamanhoY);

                posicaoValida = false;

                if (embarcacao instanceof Navio) {
                    if (orientacao.equalsIgnoreCase("horizontal") && coluna + espaço <= TamanhoY) {
                        if (verificarPosicaoLivreComDistanciaParaNavio(linha, coluna, espaço, orientacao)) {
                            posicaoValida = posicionarNavio(embarcacao, linha, coluna, orientacao);
                        }
                    } else if (orientacao.equalsIgnoreCase("vertical") && linha + espaço <= TamanhoX) {
                        if (verificarPosicaoLivreComDistanciaParaNavio(linha, coluna, espaço, orientacao)) {
                            posicaoValida = posicionarNavio(embarcacao, linha, coluna, orientacao);
                        }
                    }
                } else if (embarcacao instanceof Barco) {
                    if (orientacao.equalsIgnoreCase("horizontal") && coluna + espaço <= TamanhoY) {
                        if (verificarPosicaoLivreComDistanciaParaBarco(linha, coluna, espaço, orientacao)) {
                            posicaoValida = posicionarBarco(embarcacao, linha, coluna, orientacao);
                        }
                    } else if (orientacao.equalsIgnoreCase("vertical") && linha + espaço <= TamanhoX) {
                        if (verificarPosicaoLivreComDistanciaParaBarco(linha, coluna, espaço, orientacao)) {
                            posicaoValida = posicionarBarco(embarcacao, linha, coluna, orientacao);
                        }
                    }
                }

                if (posicaoValida) {
                    naviosRestantes--;
                }
            } while (!posicaoValida && naviosRestantes > 0);
        }
    }


    private boolean verificarPosicaoLivreComDistanciaParaNavio(int linha, int coluna, int espaço, String orientacao) {
        int margem = 1; // Distância mínima entre embarcações

        if (orientacao.equalsIgnoreCase("horizontal")) {
            for (int i = 0; i < espaço; i++) {
                if (!verificarPosicaoLivre(linha, coluna + i)) {
                    return false;
                }

                for (int j = -margem; j <= margem; j++) {
                    if (linha + j >= 0 && linha + j < TamanhoX && coluna + i >= 0 && coluna + i < TamanhoY) {
                        if (Board[linha + j][coluna + i] != 0) {
                            return false;
                        }
                    }
                }
            }
        } else if (orientacao.equalsIgnoreCase("vertical")) {
            for (int i = 0; i < espaço; i++) {
                if (!verificarPosicaoLivre(linha + i, coluna)) {
                    return false;
                }

                for (int j = -margem; j <= margem; j++) {
                    if (linha + i >= 0 && linha + i < TamanhoX && coluna + j >= 0 && coluna + j < TamanhoY) {
                        if (Board[linha + i][coluna + j] != 0) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    private boolean verificarPosicaoLivreComDistanciaParaBarco(int linha, int coluna, int espaço, String orientacao) {
        int margem = 1; // Distância mínima entre embarcações

        if (orientacao.equalsIgnoreCase("horizontal")) {
            for (int i = 0; i < espaço; i++) {
                if (!verificarPosicaoLivre(linha, coluna + i)) {
                    return false;
                }

                for (int j = -margem; j <= margem; j++) {
                    if (linha + j >= 0 && linha + j < TamanhoX && coluna + i >= 0 && coluna + i < TamanhoY) {
                        if (Board[linha + j][coluna + i] != 0) {
                            return false;
                        }
                    }
                }
            }
        } else if (orientacao.equalsIgnoreCase("vertical")) {
            for (int i = 0; i < espaço; i++) {
                if (!verificarPosicaoLivre(linha + i, coluna)) {
                    return false;
                }

                for (int j = -margem; j <= margem; j++) {
                    if (linha + i >= 0 && linha + i < TamanhoX && coluna + j >= 0 && coluna + j < TamanhoY) {
                        if (Board[linha + i][coluna + j] != 0) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    public boolean posicionarNavio(Embarcacao embarcacao, int linha, int coluna, String orientacao) {
        int espaço = embarcacao.espaço;
        boolean posicaoValida = true;

        if (orientacao.equalsIgnoreCase("horizontal")) {
            if (coluna + espaço > TamanhoY) {
                System.out.println("\u001B[31mNavio não cabe na posição especificada.\u001B[0m");
                return false;
            }
            for (int i = 0; i < espaço; i++) {
                if (!verificarPosicaoLivre(linha, coluna + i)) {
                    System.out.println("\u001B[31mPosição ocupada por outra embarcação.\u001B[0m");
                    return false;
                }
                embarcacao.posicao[i][0] = linha;
                embarcacao.posicao[i][1] = coluna + i;
                Board[linha][coluna + i] = 1; // Marcar a posição do navio no tabuleiro
            }
        } else if (orientacao.equalsIgnoreCase("vertical")) {
            if (linha + espaço > TamanhoX) {
                System.out.println("\u001B[31mNavio não cabe na posição especificada.\u001B[0m");
                return false;
            }
            for (int i = 0; i < espaço; i++) {
                if (!verificarPosicaoLivre(linha + i, coluna)) {
                    System.out.println("\u001B[31mPosição ocupada por outra embarcação.\u001B[0m");
                    return false;
                }
                embarcacao.posicao[i][0] = linha + i;
                embarcacao.posicao[i][1] = coluna;
                Board[linha + i][coluna] = 1; // Marcar a posição do navio no tabuleiro
            }
        }
        return posicaoValida;
    }

    public boolean posicionarBarco(Embarcacao embarcacao, int linha, int coluna, String orientacao) {
        int espaço = embarcacao.espaço;
        boolean posicaoValida = true;

        if (orientacao.equalsIgnoreCase("horizontal")) {
            if (coluna + espaço > TamanhoY) {
                System.out.println("\u001B[31mBarco não cabe na posição especificada.\u001B[0m");
                return false;
            }
            for (int i = 0; i < espaço; i++) {
                if (!verificarPosicaoLivre(linha, coluna + i)) {
                    System.out.println("\u001B[31mPosição ocupada por outra embarcação.\u001B[0m");
                    return false;
                }
                embarcacao.posicao[i][0] = linha;
                embarcacao.posicao[i][1] = coluna + i;
                Board[linha][coluna + i] = 2; // Marcar a posição do barco no tabuleiro com 2
            }
        } else if (orientacao.equalsIgnoreCase("vertical")) {
            if (linha + espaço > TamanhoX) {
                System.out.println("\u001B[31mBarco não cabe na posição especificada.\u001B[0m");
                return false;
            }
            for (int i = 0; i < espaço; i++) {
                if (!verificarPosicaoLivre(linha + i, coluna)) {
                    System.out.println("\u001B[31mPosição ocupada por outra embarcação.\u001B[0m");
                    return false;
                }
                embarcacao.posicao[i][0] = linha + i;
                embarcacao.posicao[i][1] = coluna;
                Board[linha + i][coluna] = 2; // Marcar a posição do barco no tabuleiro com 2
            }
        }
        return posicaoValida;
    }

    public boolean atacarTabuleiro(Tabuleiro tabuleiro, int linha, int coluna) {
        if (linha < 0 || linha >= TamanhoX || coluna < 0 || coluna >= TamanhoY) {
            System.out.println("\n\u001B[33mCoordenadas inválidas.\u001B[0m\n");
            return false;
        }

        if (tabuleiro.Board[linha][coluna] == -1 || tabuleiro.Board[linha][coluna] == -2) {
            System.out.println("\n\u001B[33mPosição já foi atacada anteriormente.\u001B[0m\n");
            return false;
        } else if (tabuleiro.Board[linha][coluna] == 1 || tabuleiro.Board[linha][coluna] == 2) {
            // ... Se o ataque acertar uma embarcação
            System.out.println("\n\u001B[32mAtaque acertou uma embarcação!\u001B[0m\n");
            tabuleiro.Board[linha][coluna] = -2; // Marcar o ataque no tabuleiro do oponente
            tabuleiro.registrarAcerto(linha, coluna); // Registrar o acerto na matriz de acertos
            return true;
        } else {
            // ... Se o ataque não acertar uma embarcação
            System.out.println("\n\u001B[31mAtaque falhou.\u001B[0m\n");
            tabuleiro.Board[linha][coluna] = -1; // Marcar o ataque no tabuleiro do oponente
            tabuleiro.registrarDisparoErrado(linha, coluna); // Registrar disparo errado na matriz de acertos
            return false;
        }
    }

    public void ataqueDaCPU(Tabuleiro tabuleiro) {
        Random random = new Random();
        int linhaAtaque, colunaAtaque;

        do {
            linhaAtaque = random.nextInt(tabuleiro.getTamanhoX());
            colunaAtaque = random.nextInt(tabuleiro.getTamanhoY());
        } while (tabuleiro.Board[linhaAtaque][colunaAtaque] == -1  || tabuleiro.Board[linhaAtaque][colunaAtaque] == -2 ); // Verifica se a posição já foi atacada antes

        if (tabuleiro.Board[linhaAtaque][colunaAtaque] == 1 || tabuleiro.Board[linhaAtaque][colunaAtaque] == 2) {
            System.out.println("\n\u001B[31mO oponente acertou uma embarcação do Tabuleiro 1!\u001B[0m\n");
            tabuleiro.Board[linhaAtaque][colunaAtaque] = -2; // Marcar o ataque no tabuleiro 1
            
            // Tentar atacar posições adjacentes
            tentarAtaqueAdjacente(tabuleiro, linhaAtaque, colunaAtaque);
        } else {
            System.out.println("\n\u001B[32mO oponente atacou e errou.\u001B[0m\n");
            tabuleiro.Board[linhaAtaque][colunaAtaque] = -1; // Marcar o ataque no tabuleiro 1
        }
    }

    public void tentarAtaqueAdjacente(Tabuleiro tabuleiro, int linha, int coluna) {
        // Verificar as posições adjacentes (acima, abaixo, esquerda, direita)
        int[][] direcoes = { {-1, 0}, {1, 0}, {0, -1}, {0, 1} };

        for (int[] direcao : direcoes) {
            int novaLinha = linha + direcao[0];
            int novaColuna = coluna + direcao[1];

            // Verificar se a posição está dentro do tabuleiro e não foi atacada ainda
            if (novaLinha >= 0 && novaLinha < tabuleiro.getTamanhoX() &&
                novaColuna >= 0 && novaColuna < tabuleiro.getTamanhoY() &&
                (tabuleiro.Board[novaLinha][novaColuna] == -1  || tabuleiro.Board[novaLinha][novaColuna] == -2 )) {

                if (tabuleiro.Board[novaLinha][novaColuna] == 1 || tabuleiro.Board[novaLinha][novaColuna] == 2) {
                    System.out.println("\n\u001B[31mO oponente acertou uma embarcação adjacente!\u001B[0m\n");
                    tabuleiro.Board[novaLinha][novaColuna] = -2; // Marcar o ataque adjacente
                    tentarAtaqueAdjacente(tabuleiro, novaLinha, novaColuna); // Tentar atacar adjacências desta nova posição
                } else {
                    System.out.println("\n\u001B[32mO oponente atacou uma posição adjacente e errou.\u001B[0m\n");
                    tabuleiro.Board[novaLinha][novaColuna] = -1; // Marcar o ataque na posição adjacente
                }
            }
        }
    }

    public void imprimirTabuleiro() {
        System.out.print("  ");
        for (int c = 0; c < TamanhoY; c++) {
            System.out.print("\u001B[33m" + c + " \u001B[0m");
        }
        System.out.println();

        for (int i = 0; i < TamanhoX; i++) {
            System.out.print("\u001B[33m" + i + " \u001B[0m");

            for (int j = 0; j < TamanhoY; j++) {
                if (Board[i][j] == 0) {
                    System.out.print("\u001B[34m- \u001B[0m"); // - para água
                } else if (Board[i][j] == 1) {
                    System.out.print("\u001B[31mX \u001B[0m"); // X para navio
                } else if (Board[i][j] == 2) {
                    System.out.print("\u001B[32mX \u001B[0m"); // X para barco
                } else if (Board[i][j] == -1) {
                    System.out.print("\u001B[33m~ \u001B[0m"); // ~ para ataque errado
                } else if (Board[i][j] == -2) {
                    System.out.print("\u001B[37m* \u001B[0m"); // * para ataque certo
                }
            }
            System.out.println();
        }
    }

    public void imprimirTabuleiroOculto(boolean[][] acertos) {
        System.out.print("  ");
        for (int c = 0; c < TamanhoY; c++) {
            System.out.print("\u001B[33m" + c + " \u001B[0m");
        }
        System.out.println();

        for (int i = 0; i < TamanhoX; i++) {
            System.out.print("\u001B[33m" + i + " \u001B[0m");

            for (int j = 0; j < TamanhoY; j++) {
                if (acertos[i][j]) {
                    System.out.print("\u001B[31mX \u001B[0m"); // Símbolo para acertos no tabuleiro oculto
                } else if (Board[i][j] == 0 || Board[i][j] == 1 || Board[i][j] == 2) {
                    System.out.print("\u001B[34m- \u001B[0m");
                } else if (Board[i][j] == -1) {
                    System.out.print("\u001B[33m~ \u001B[0m"); // Símbolo para disparo errado no tabuleiro oculto
                }
            }
            System.out.println();
        }
    }

    public void imprimirBatalha(Tabuleiro tabuleiroOponente, boolean[][] acertos) {
        System.out.println("\nTabuleiro do Oponente");
        tabuleiroOponente.imprimirTabuleiroOculto(acertos); // Imprimir o tabuleiro do oponente ocultando as embarcações

        System.out.println("\nSeu Tabuleiro");
        imprimirTabuleiro(); // Imprimir o seu tabuleiro
    }

    public void imprimirTabuleiroCompleto() {
        System.out.print("  ");
        for (int c = 0; c < TamanhoY; c++) {
            System.out.print("\u001B[33m" + c + " \u001B[0m");
        }
        System.out.println();

        for (int i = 0; i < TamanhoX; i++) {
            System.out.print("\u001B[33m" + i + " \u001B[0m");

            for (int j = 0; j < TamanhoY; j++) {
                if (Board[i][j] == 0) {
                    System.out.print("\u001B[34m- \u001B[0m");
                } else if (Board[i][j] == 1 || Board[i][j] == 2) {
                    System.out.print("\u001B[31mX \u001B[0m");
                } else if (Board[i][j] == -1) {
                    System.out.print("\u001B[33m~ \u001B[0m");
                } else if (Board[i][j] == -2) {
                    System.out.print("X ");
                }
            }
            System.out.println();
        }
    }

    public boolean[][] gerarAcertos(int tamanhoX, int tamanhoY) {
        boolean acertos[][] = new boolean[tamanhoX][tamanhoY];
            for (int i = 0; i < tamanhoX; i++) {
                for (int j = 0; j < tamanhoY; j++) {
                    acertos[i][j] = false;
                }
            }

        return acertos;
    }
    
    public void registrarAcerto(int linha, int coluna) {
        this.acertos[linha][coluna] = true;
    }

    public void registrarDisparoErrado(int linha, int coluna) {
        this.acertos[linha][coluna] = false;
    }

    public boolean[][] getAcertos() {
        return this.acertos;
    }

    public boolean aindaHaEmbarcacoes() {
        for (int i = 0; i < TamanhoX; i++) {
            for (int j = 0; j < TamanhoY; j++) {
                if (Board[i][j] == 1 || Board[i][j] == 2) {
                    return true; // Se encontrar uma embarcação, retorna true indicando que ainda há embarcações
                }
            }
        }
        return false; // Se não encontrar embarcações, retorna false
    }

    public int getTamanhoX() {
        return this.TamanhoX;
    }

    public int getTamanhoY() {
        return this.TamanhoY;
    }

    public int getLimite() {
        return this.limeteNavios;
    }

    public void variarLimite(int num){
        this.limeteNavios -= num;
    }

    public int getTotalNavios(){
        return this.totalNavios;
    }

    public void setTotalNavios(int num){
        this.totalNavios = num;
    }

    public void setLimite(int num){
        this.limeteNavios = num;
    }

    public void variarTotalNavios(int sum){
        this.totalNavios -= sum;
    }
}

public class BatalhaNaval {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Tabuleiro tabuleiro = new Tabuleiro();
        tabuleiro.obterTamanho();
        System.out.println("\nDigite a dificuldade:\n\u001B[32mFácil\u001B[0m\n\u001B[33mNormal\u001B[0m\n\u001B[31mDifícil\u001B[0m\n");
        Tabuleiro tabuleiro2;
        for(;;){
            String dificuldade = input.next(); //Insere por meio do teclado a dificuldade
            if(dificuldade.equalsIgnoreCase("Fácil") || dificuldade.equalsIgnoreCase("fácil") || dificuldade.equalsIgnoreCase("Facil") || dificuldade.equalsIgnoreCase("facil")){ //Tabuleiro da CPU será 6x6
                tabuleiro2 = new Tabuleiro(6,6);
                tabuleiro2.setTotalNavios(4);
                tabuleiro2.setLimite(4);
                break;
            }
            else if(dificuldade.equalsIgnoreCase("Normal") || dificuldade.equalsIgnoreCase("normal")){ //Tabuleiro da CPU será do tamanho do do jogador
                tabuleiro2 = new Tabuleiro(tabuleiro.getTamanhoX(), tabuleiro.getTamanhoY());
                tabuleiro2.setTotalNavios(tabuleiro.getTotalNavios());
                tabuleiro2.setLimite(tabuleiro2.getTotalNavios());
                break;
            }
            else if(dificuldade.equalsIgnoreCase("Difícil") || dificuldade.equalsIgnoreCase("difícil") || dificuldade.equalsIgnoreCase("Dificil") || dificuldade.equalsIgnoreCase("dificil")){ // Tabuleiro será 12x12
                tabuleiro2 = new Tabuleiro(12,12);
                tabuleiro2.setTotalNavios((12+12)/3);
                tabuleiro2.setLimite((12+12)/3);
                break;
            }
            else{
                System.out.println("Digite uma dificuldade.");
            }
        }
        tabuleiro.imprimirTabuleiro();
        System.out.println("\nAdicione embarcações ao seu tabuleiro\n");

        // Solicitar posição e orientação do navio ao usuário
        System.out.println("Adicionar manualmente ou aleatoriamente? \u001B[31m(M ou A)\u001B[0m");

        String metodo = input.next();
        if(metodo.equalsIgnoreCase("M") || metodo.equalsIgnoreCase("m")){

            while(tabuleiro.getLimite() > 0){
                System.out.println("\nQual embarcação deseja inserir? (Numero restante = " + tabuleiro.getLimite() + ")\nNavio = 4 espaços\u001B[31m(vermelho)\u001B[0m\nBarco = 2 espaços\u001B[32m(verde)\u001B[0m\n\u001B[31m(caso nao deseje mais, digite END)\u001B[0m");
                String embarcacao = input.next();
                if(embarcacao.equalsIgnoreCase("Navio") || embarcacao.equalsIgnoreCase("navio")){
                    System.out.println("Digite a linha para posicionar o navio:");
                    int linha = input.nextInt();
                    System.out.println("Digite a coluna para posicionar o navio:");
                    int coluna = input.nextInt();
                    System.out.println("Digite a orientação (horizontal/vertical):");
                    String orientacao = input.next();

                    // Posicionar o navio no tabuleiro
                    Embarcacao navio = new Navio();
                    if(tabuleiro.posicionarNavio(navio, linha, coluna, orientacao))
                        tabuleiro.variarLimite(1);
                    tabuleiro.imprimirTabuleiro();
                }else if(embarcacao.equalsIgnoreCase("Barco") || embarcacao.equalsIgnoreCase("barco")){
                    System.out.println("Digite a linha para posicionar o barco:");
                    int linha = input.nextInt();
                    System.out.println("Digite a coluna para posicionar o barco:");
                    int coluna = input.nextInt();
                    System.out.println("Digite a orientação (horizontal/vertical):");
                    String orientacao = input.next();

                    // Posicionar o barco no tabuleiro
                    Embarcacao barco = new Barco();
                    if(tabuleiro.posicionarBarco(barco, linha, coluna, orientacao))
                        tabuleiro.variarLimite(1);
                    tabuleiro.imprimirTabuleiro();
                }else if(embarcacao.equalsIgnoreCase("END")){
                    break;
                }else{
                    System.out.println("\u001B[31mEmbarcação invalida, digite Navio ou Barco.\u001B[0m");
                }
            }
    }else if(metodo.equalsIgnoreCase("A") || metodo.equalsIgnoreCase("a")){
        tabuleiro.posicionarEmbarcacaoAleatoria();
    }
    tabuleiro2.posicionarEmbarcacaoAleatoria();

    System.out.println("\n\n\u001B[33mPREPARE-SE PARA BATALHA!!!\u001B[0m\n\n");
    while (tabuleiro.aindaHaEmbarcacoes() && tabuleiro2.aindaHaEmbarcacoes()) {
        tabuleiro.imprimirBatalha(tabuleiro2, tabuleiro2.getAcertos());

        // Jogador ataca
        System.out.println("Sua vez! Realize um ataque! Digite a linha e a coluna!");
        int linha = input.nextInt();
        int coluna = input.nextInt();
        try {
            if (tabuleiro.atacarTabuleiro(tabuleiro2, linha, coluna)) {
                // Se o ataque foi bem-sucedido, o jogador ataca novamente
                System.out.println("\nAtaque bem-sucedido! Você tem outra jogada!\n");
                continue; // Retorna ao início do loop para o próximo ataque do jogador
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("\nAlgum valor digitado foge do tabuleiro.\n");
        }

        // Oponente ataca
        System.out.println("\nCuidado!! Ataques vindo!!\n");
        tabuleiro2.ataqueDaCPU(tabuleiro);
    }

    if(tabuleiro.aindaHaEmbarcacoes()){
        System.out.println("\n\n\u001B[32mParabéns!! Você e sua tripulação alcançaram a vitória!\u001B[0m\n\n");
    }else{
        System.out.println("\n\n\u001B[31mQue pena!! Você e sua tripulação conheceram a derrota!\u001B[0m\n\n");
    }

        // Exibir o tabuleiro do oponente completamente
        System.out.println("Tabuleiro do Oponente:");
        tabuleiro2.imprimirTabuleiroCompleto();
        
         input.close();// Fechando o Scanner
    }
    
}

