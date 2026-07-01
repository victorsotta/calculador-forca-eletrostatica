Calculador de Força Eletrostática

Interface gráfica em Java desenvolvida para automatizar a análise vetorial de forças eletrostáticas entre múltiplas partículas carregadas, utilizando a Lei de Coulomb e o princípio da superposição. O projeto foi criado para resolver numericamente o problema de geometria quadrada fixa apresentado na Figura 21.15.

O Problema Físico:

O programa calcula as forças eletrostáticas que atuam sobre a Partícula 3, localizada na origem (0,0) de um plano cartesiano. Essa partícula sofre influência de outras três cargas posicionadas nos vértices de um quadrado de lado a.

Configuração Geométrica
Partícula 3 (alvo): posição (0,0)
Partícula 1: posição (0,a)
Partícula 4: posição (a,0)
Partícula 2: posição (a,a) (localizada na diagonal)

O software calcula as forças causadas por cada carga, determina se elas são de atração ou repulsão e realiza a decomposição vetorial das forças para encontrar as componentes resultantes nos eixos X e Y, além do módulo da força total.

Funcionalidades:
Interface gráfica utilizando Java Swing, com campos de entrada para as cargas q1, q2, q3 e q4 em nanoCoulombs (nC), além do lado do quadrado a em centímetros (cm).
Validação de dados através de tratamento de exceções (try-catch), evitando entradas vazias, caracteres inválidos e valores físicos incorretos, como um lado do quadrado menor ou igual a zero.
Memória de cálculo através de uma área de texto (JTextArea), mostrando o desenvolvimento das fórmulas e o passo a passo dos cálculos realizados.
Saída formatada exibindo as componentes da força nos eixos X e Y e a força resultante em Newtons (N).

Tecnologias Utilizadas:
Linguagem Java (JDK 21)
Interface gráfica utilizando javax.swing e java.awt
Ambiente de desenvolvimento Visual Studio Code (VS Code)

Modelagem Matemática e Implementação:

O projeto foi desenvolvido em um único arquivo chamado CalculadorForcaEletrica.java. O código separa a criação dos componentes visuais da parte responsável pelos cálculos físicos, realizada pelo método calcularForcaEletrica().

Fórmulas Implementadas

Lei de Coulomb:

F = k × |qA × qB| / r²

Onde:

F representa a força elétrica entre duas cargas;
k é a constante eletrostática;
qA e qB são os valores das cargas;
r representa a distância entre as cargas.

Decomposição vetorial das forças diagonais:

Fx = F × cos(45°)

Fy = F × sen(45°)

As componentes das forças são somadas separadamente nos eixos X e Y. Depois disso, o módulo da força resultante é calculado utilizando o Teorema de Pitágoras:

Fr = √(Fx² + Fy²)

Como Executar o Projeto

A aplicação utiliza apenas bibliotecas nativas do Java, não sendo necessário instalar gerenciadores de dependências como Maven ou Gradle.

Pré-requisitos
Java JDK instalado e configurado nas variáveis de ambiente.
Versão recomendada: JDK 11 ou superior.

Comandos para execução:

Compile o arquivo fonte:
javac CalculadorForcaEletrica.java

Execute o programa:
java CalculadorForcaEletrica
