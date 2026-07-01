# Calculador de Forca Eletrostatica

Interface grafica em Java desenvolvida para automatizar a analise vetorial de forcas eletrostaticas entre multiplas particulas carregadas com base na Lei de Coulomb e o Pincípio da Superposição. O projeto foi projetado para resolver numericamente o problema de geometria quadrada fixa apresentado na Fig. 21.15.

---

## O Problema Fisico

O programa calcula as interacoes eletrostaticas que atuam especificamente sobre a Particula 3, localizada na origem (0,0) de um plano cartesiano. A particula alvo esta cercada por outras tres cargas dispostas nos vertices de um quadrado de lado $a$.

### Configuracao Geometrica
 Particula 3 (Alvo) Posicao $(0, 0)$
 Particula 1 Posicao $(0, a)$
 Particula 4 Posicao $(a, 0)$
 Particula 2 Posicao $(a, a)$ (Diagonal)

O software calcula as magnitudes puras das forcas de atracao ou repulsao e realiza a decomposicao vetorial para entregar as componentes resultantes $F_x$ e $F_y$, alem do modulo da forca total.

---

## Funcionalidades

 Interface Grafica (Swing) Campos de texto para entrada das cargas ($q_1, q_2, q_3, q_4$) em nanoCoulombs ($nC$) e o lado do quadrado ($a$) em centimetros ($cm$).
 Validacao de Dados Tratamento de excecoes (`try-catch`) para impedir campos vazios, caracteres invalidos ou valores geometricos inconsistentes (lado menor ou igual a zero).
 Memoria de Calculo Area de texto dedicada (`JTextArea`) que exibe o fluxo algebrico e o passo a passo das equacoes estruturadas.
 Saida Formatada Exibicao das componentes cartesianas da forca em Newtons ($N$) com precisao decimal.

---

## Tecnologias Utilizadas

 Linguagem Java (JDK 21)
 GUI (Interface Grafica) `javax.swing` e `java.awt` (utilizando o Look and Feel nativo do sistema operacional)
 Ambiente de Desenvolvimento Visual Studio Code (VS Code)

---

## Modelagem Matematica e Implementacao

O projeto foi centralizado em um unico arquivo estruturado (`CalculadorForcaEletrica.java`), isolando a construcao dos componentes visuais do metodo de processamento logico (`calcularForcaEletrica()`).

### Formulas Implementadas

 Lei de Coulomb
  $$F = k cdot frac{q_A cdot q_B}{r^2}$$

 Decomposicao Vetorial (45 graus)
  $$F_x = F_{mag} cdot cos(45^circ)$$
  $$F_y = F_{mag} cdot sin(45^circ)$$

---

## Como Executar o Projeto

A aplicacao utiliza apenas pacotes nativos da API do Java, dispensando gerenciadores de dependencias externos como Maven ou Gradle.

### Pre-requisitos
 Java JDK instalado e configurado nas variaveis de ambiente (versao 11 ou superior).

### Comandos para Execucao

1. Compile o arquivo fonte
   ```bash
   javac CalculadorForcaEletrica.java