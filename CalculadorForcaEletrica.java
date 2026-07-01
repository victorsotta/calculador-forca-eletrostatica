import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

public class CalculadorForcaEletrica {

    // Componentes globais da janela
    private static JFrame janela;
    private static JTextField txtQ1, txtQ2, txtQ3, txtQ4, txtLadoA;
    private static JLabel lblForcaX, lblForcaY, lblForcaResultante;
    private static JTextArea txtCalculos;

    // Constante eletrostática no vácuo (k = 8.99 * 10^9 N.m^2/C^2)
    private static final double K = 8.99e9;

    public static void main(String[] args) {
        // Deixa o visual adaptado ao sistema operativo (Windows/Linux/Mac)
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // Caso falhe, usa o padrão do Java
        }

        // Criando a janela principal
        janela = new JFrame("Calculador de Força Eletrostática");
        janela.setSize(500, 650);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setLocationRelativeTo(null); // Centraliza no ecrã
        janela.setLayout(new BorderLayout(10, 10));

        // Painel central para empilhar os elementos na vertical
        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new BoxLayout(painelPrincipal, BoxLayout.Y_AXIS));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // --- 1. CAMPOS DE ENTRADA (INPUTS) ---
        // Inicializando com os valores do teu enunciado (em nC e cm)
        txtQ1 = new JTextField("100");
        txtQ2 = new JTextField("-100");
        txtQ3 = new JTextField("200");
        txtQ4 = new JTextField("-200");
        txtLadoA = new JTextField("5.0");

        // Adicionando os campos utilizando a nossa função auxiliar de layout
        painelPrincipal.add(criarLinhaInput("Carga q1 (nC):", "Valor positivo ou negativo", txtQ1));
        painelPrincipal.add(Box.createVerticalStrut(8));
        painelPrincipal.add(criarLinhaInput("Carga q2 (nC):", "Valor positivo ou negativo", txtQ2));
        painelPrincipal.add(Box.createVerticalStrut(8));
        painelPrincipal.add(criarLinhaInput("Carga q3 (nC):", "Alvo da análise", txtQ3));
        painelPrincipal.add(Box.createVerticalStrut(8));
        painelPrincipal.add(criarLinhaInput("Carga q4 (nC):", "Valor positivo ou negativo", txtQ4));
        painelPrincipal.add(Box.createVerticalStrut(8));
        painelPrincipal.add(criarLinhaInput("Lado do quadrado 'a' (cm):", "Distância geométrica", txtLadoA));

        painelPrincipal.add(Box.createVerticalStrut(20));

        // --- 2. PAINEL DE RESULTADOS RÁPIDOS ---
        JPanel painelResultados = new JPanel(new GridLayout(3, 2, 5, 5));
        painelResultados.setAlignmentX(Component.LEFT_ALIGNMENT);

        painelResultados.add(new JLabel("(a) Componente Fx na partícula 3:"));
        lblForcaX = new JLabel("0.000 N");
        painelResultados.add(lblForcaX);

        painelResultados.add(new JLabel("(b) Componente Fy na partícula 3:"));
        lblForcaY = new JLabel("0.000 N");
        painelResultados.add(lblForcaY);

        painelResultados.add(new JLabel("Força Resultante Total (Magnitude):"));
        lblForcaResultante = new JLabel("0.000 N");
        painelResultados.add(lblForcaResultante);

        painelPrincipal.add(painelResultados);
        painelPrincipal.add(Box.createVerticalStrut(20));

        // --- 3. ÁREA DA MEMÓRIA DE CÁLCULO ---
        JPanel painelBordaCalculos = new JPanel(new BorderLayout());
        painelBordaCalculos.setBorder(BorderFactory.createTitledBorder("Passo a Passo das Equações"));
        painelBordaCalculos.setAlignmentX(Component.LEFT_ALIGNMENT);

        txtCalculos = new JTextArea(12, 30);
        txtCalculos.setEditable(false);
        txtCalculos.setFont(new Font("Monospaced", Font.PLAIN, 12));
        txtCalculos.setText("Clique em 'Calcular Respostas' para processar a Lei de Coulomb.");

        JScrollPane scrollPane = new JScrollPane(txtCalculos);
        painelBordaCalculos.add(scrollPane, BorderLayout.CENTER);
        painelPrincipal.add(painelBordaCalculos);

        // --- 4. BOTÃO DE AÇÃO ---
        JPanel painelBotao = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnCalcular = new JButton("Calcular Respostas");
        
        btnCalcular.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calcularForcaEletrica(); // Executa a lógica física
            }
        });
        painelBotao.add(btnCalcular);

        // Montando a aplicação
        janela.add(painelPrincipal, BorderLayout.CENTER);
        janela.add(painelBotao, BorderLayout.SOUTH);
        janela.setVisible(true);
    }

    /**
     * Função auxiliar para organizar as linhas de entrada de dados
     */
    private static JPanel criarLinhaInput(String textoLabel, String textoDica, JTextField campoTexto) {
        JPanel linha = new JPanel(new BorderLayout());
        linha.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel blocoTexto = new JPanel();
        blocoTexto.setLayout(new BoxLayout(blocoTexto, BoxLayout.Y_AXIS));
        
        JLabel labelPrincipal = new JLabel(textoLabel);
        labelPrincipal.setFont(new Font("Arial", Font.BOLD, 13));
        
        JLabel labelDica = new JLabel(textoDica);
        labelDica.setFont(new Font("Arial", Font.PLAIN, 10));
        labelDica.setForeground(Color.GRAY);

        blocoTexto.add(labelPrincipal);
        blocoTexto.add(labelDica);

        campoTexto.setPreferredSize(new Dimension(150, 25));

        linha.add(blocoTexto, BorderLayout.WEST);
        linha.add(campoTexto, BorderLayout.EAST);

        return linha;
    }

  

     * Lógica física da Fig 21.15: Calcula as componentes X e Y da força sobre q3
     */
    private static void calcularForcaEletrica() {
        try {
            // 1. Conversão de unidades (nC para C, e cm para metros)
            double q1 = Double.parseDouble(txtQ1.getText().replace(",", ".")) * 1e-9;
            double q2 = Double.parseDouble(txtQ2.getText().replace(",", ".")) * 1e-9;
            double q3 = Double.parseDouble(txtQ3.getText().replace(",", ".")) * 1e-9;
            double q4 = Double.parseDouble(txtQ4.getText().replace(",", ".")) * 1e-9;
            double a = Double.parseDouble(txtLadoA.getText().replace(",", ".")) * 0.01;

            if (a <= 0) {
                throw new ArithmeticException("O lado 'a' deve ser maior que zero.");
            }

            // Distâncias entre as partículas e a partícula 3 (na origem)
            double r31 = a;
            double r34 = a;
            double r32 = a * Math.sqrt(2); // Diagonal do quadrado

            // 2. Cálculo das magnitudes puras de cada força (Lei de Coulomb)
            double f31_mag = (K * Math.abs(q3 * q1)) / (r31 * r31);
            double f34_mag = (K * Math.abs(q3 * q4)) / (r34 * r34);
            double f32_mag = (K * Math.abs(q3 * q2)) / (r32 * r32);

            // Ângulo da diagonal (45 graus) para a decomposição de F32
            double cos45 = Math.sqrt(2) / 2.0;
            double sen45 = Math.sqrt(2) / 2.0;

            // 3. SOMA DAS COMPONENTES NO EIXO X
            // F34: q3(+) e q4(-) ATRAÇÃO -> Puxa q3 para a direita (+x)
            double f34_x = f34_mag; 
            
            // F32_x: q3(+) e q2(-) ATRAÇÃO -> Puxa q3 na diagonal para a direita (+x)
            double f32_x = f32_mag * cos45;
            
            // Componente X Final Resultante
            double f_total_x = f34_x + f32_x;


            // 4. SOMA DAS COMPONENTES NO EIXO Y
            // F31: q3(+) e q1(+) REPULSÃO -> Empurra q3 para baixo (-y)
            double f31_y = -f31_mag; 
            
            // F32_y: q3(+) e q2(-) ATRAÇÃO -> Puxa q3 na diagonal para cima (+y)
            double f32_y = f32_mag * sen45;
            
            // Componente Y Final Resultante
            double f_total_y = f31_y + f32_y;


            // Força Resultante Total (Magnitude/Módulo)
            double f_resultante = Math.sqrt(f_total_x * f_total_x + f_total_y * f_total_y);

            // 5. Atualização da Interface (Exibição com 4 casas decimais para melhor precisão)
            lblForcaX.setText(String.format(Locale.US, "%.4f N", f_total_x));
            lblForcaY.setText(String.format(Locale.US, "%.4f N", f_total_y));
            lblForcaResultante.setText(String.format(Locale.US, "%.4f N", f_resultante));

            // 6. Histórico detalhado na Área de Texto (Memória de Cálculo)
            String memoriaCalculo = 
                "--- RESULTADO DA ANÁLISE VETORIAL (Partícula 3) ---\n" +
                String.format(Locale.US, "Lado do quadrado (a): %.3f m\n\n", a) +
                "1. Magnitudes das Forças Individuais:\n" +
                String.format(Locale.US, "   • |F31| (q1 sobre q3): %.4f N (Repulsão -> empurra para -y)\n", f31_mag) +
                String.format(Locale.US, "   • |F34| (q4 sobre q3): %.4f N (Atração -> puxa para +x)\n", f34_mag) +
                String.format(Locale.US, "   • |F32| (q2 sobre q3): %.4f N (Atração -> puxa a 45°)\n\n", f32_mag) +
                "2. Componente X Final (Fx):\n" +
                String.format(Locale.US, "   Fx = F34_x + F32_x\n") +
                String.format(Locale.US, "   Fx = %.4f N + (%.4f N × cos(45°))\n", f34_mag, f32_mag) +
                String.format(Locale.US, "   Fx = %.4f N + %.4f N = %.4f N\n\n", f34_x, f32_x, f_total_x) +
                "3. Componente Y Final (Fy):\n" +
                String.format(Locale.US, "   Fy = F31_y + F32_y\n") +
                String.format(Locale.US, "   Fy = -%.4f N + (%.4f N × sen(45°))\n", f31_mag, f32_mag) +
                String.format(Locale.US, "   Fy = -%.4f N + %.4f N = %.4f N\n\n", f31_mag, f32_y, f_total_y) +
                "--------------------------------------------------\n" +
                String.format(Locale.US, "Força Resultante Total |F| = %.4f N", f_resultante);

            txtCalculos.setText(memoriaCalculo);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(janela, 
                "Erro de Formatação: Verifique se todas as entradas são números.", 
                "Erro de Entrada", 
                JOptionPane.ERROR_MESSAGE);
        } catch (ArithmeticException ex) {
            JOptionPane.showMessageDialog(janela, ex.getMessage(), "Erro Matemático", JOptionPane.ERROR_MESSAGE);
        }
    }
}
