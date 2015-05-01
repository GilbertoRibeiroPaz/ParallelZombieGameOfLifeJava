# ParallelZombieGameOfLifeJava
Versão aprimorada do jogo da vida com regras para vizinhos zumbis utilizando
multiprogramação.


Utilização do programa:
    Há dois modos de execução do jogo:
        - Execução do modulo de testes automatizados em que é possivel entrar
        com parâmetros de diretórios de origem dos arquivos de configuração e
        diretórios para imprimir seus resultados. O programa faz testes de 2 
        até o tamanho passado em parâmetro de execução. Serão executados testes
        de 2 até 10 iterações para cada problema e de 1 até n threads dos 
        parâmetros de entrada. Amostra de parâmetros:
        java -jar ParallelZombieGameOfLife MAKETEST <Diretório onde serão armazenados os arquivos de configuração> <Diretório onde estarão os resultados> <tamanho máximo do problema> <número máximo de threads>
        
        - Execução normal como descrita no problema do trabalho. Por padrão executa
        com o número de threads equivalente ao número de cores do processador.
        Amostra de parâmetros:
        java -jar ParallelZombieGameOfLife <tamanho do problema> <iterações> <arquivo de configuração> <arquivo de resultado>
           


