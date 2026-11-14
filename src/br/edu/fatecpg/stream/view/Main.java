package br.edu.fatecpg.stream.view;

import br.edu.fatecpg.stream.model.Funcionario;
import br.edu.fatecpg.stream.model.Produto;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    private static final NumberFormat MOEDA = NumberFormat.getNumberInstance(Locale.forLanguageTag("pt-BR"));

    static {
        MOEDA.setMinimumFractionDigits(2);
        MOEDA.setMaximumFractionDigits(2);
    }

    public static void main(String[] args) {
        executarAtividadeProdutos();
        executarAtividadeFuncionarios();
    }

    private static void executarAtividadeProdutos() {
        List<Produto> produtos = Arrays.asList(
                new Produto("Smartphone", "Eletronicos", 1200.00),
                new Produto("Notebook", "Eletronicos", 2500.00),
                new Produto("Livro Java", "Livros", 90.00),
                new Produto("Fone de Ouvido", "Eletronicos", 150.00),
                new Produto("Camiseta", "Roupas", 50.00),
                new Produto("Calca", "Roupas", 100.00),
                new Produto("Livro Design", "Livros", 120.00)
        );

        System.out.println("====================================================");
        System.out.println("             ATIVIDADE 1 - PRODUTOS                 ");
        System.out.println("====================================================");
        System.out.println("\n========== PRODUTOS CADASTRADOS ==========");
        produtos.forEach(System.out::println);

        List<Produto> eletronicos = produtos.stream()
                .filter(produto -> produto.getCategoria().equalsIgnoreCase("Eletronicos"))
                .collect(Collectors.toList());

        System.out.println("\n========== PRODUTOS DA CATEGORIA ELETRONICOS ==========");
        eletronicos.forEach(System.out::println);

        List<Produto> eletronicosComDesconto = eletronicos.stream()
                .map(produto -> new Produto(
                        produto.getNome(),
                        produto.getCategoria(),
                        produto.getPreco() * 0.90
                ))
                .sorted(Comparator.comparingDouble(Produto::getPreco))
                .collect(Collectors.toList());

        System.out.println("\n===== ELETRONICOS COM 10% DE DESCONTO - ORDEM CRESCENTE =====");
        eletronicosComDesconto.forEach(System.out::println);

        double totalRoupas = produtos.stream()
                .filter(produto -> produto.getCategoria().equalsIgnoreCase("Roupas"))
                .map(Produto::getPreco)
                .reduce(0.0, Double::sum);

        System.out.println("\n========== TOTAL DOS PRODUTOS DE ROUPAS ==========");
        System.out.println("R$ " + MOEDA.format(totalRoupas));

        Map<String, Double> mediaPrecosPorCategoria = produtos.stream()
                .collect(Collectors.groupingBy(
                        Produto::getCategoria,
                        LinkedHashMap::new,
                        Collectors.averagingDouble(Produto::getPreco)
                ));

        System.out.println("\n========== MEDIA DE PRECO POR CATEGORIA ==========");
        mediaPrecosPorCategoria.forEach((categoria, media) ->
                System.out.println(categoria + ": R$ " + MOEDA.format(media))
        );
    }

    private static void executarAtividadeFuncionarios() {
        List<Funcionario> funcionarios = Arrays.asList(
                new Funcionario("Ana", "Recursos Humanos", 2800.00, 12),
                new Funcionario("Bruno", "TI", 5000.00, 8),
                new Funcionario("Carla", "Financeiro", 4200.00, 15),
                new Funcionario("Diego", "TI", 3200.00, 11),
                new Funcionario("Eduarda", "Recursos Humanos", 3500.00, 5),
                new Funcionario("Felipe", "Financeiro", 2900.00, 20),
                new Funcionario("Gabriela", "TI", 6100.00, 14),
                new Funcionario("Henrique", "Recursos Humanos", 2700.00, 2),
                new Funcionario("Isabela", "Financeiro", 3800.00, 9),
                new Funcionario("Joao", "TI", 3000.00, 13)
        );

        System.out.println("\n\n====================================================");
        System.out.println("          ATIVIDADE 2 - FUNCIONARIOS                ");
        System.out.println("====================================================");
        System.out.println("\n========== FUNCIONARIOS CADASTRADOS ==========");
        funcionarios.forEach(System.out::println);

        List<Funcionario> funcionariosSalarioSuperior = funcionarios.stream()
                .filter(funcionario -> funcionario.getSalario() > 3000.00)
                .collect(Collectors.toList());

        System.out.println("\n========== SALARIO SUPERIOR A R$ 3.000,00 ==========");
        funcionariosSalarioSuperior.forEach(System.out::println);

        List<Funcionario> funcionariosReajustadosEOrdenados = funcionarios.stream()
                .map(funcionario -> new Funcionario(
                        funcionario.getNome(),
                        funcionario.getDepartamento(),
                        funcionario.getAnosDeServico() > 10
                                ? funcionario.getSalario() * 1.05
                                : funcionario.getSalario(),
                        funcionario.getAnosDeServico()
                ))
                .sorted(Comparator.comparing(Funcionario::getNome, String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.toList());

        System.out.println("\n========== FUNCIONARIOS APOS O REAJUSTE E ORDENADOS POR NOME ==========");
        System.out.println("Aumento de 5% aplicado somente a quem possui mais de 10 anos de servico.");
        funcionariosReajustadosEOrdenados.forEach(System.out::println);

        double totalSalarios = funcionariosReajustadosEOrdenados.stream()
                .map(Funcionario::getSalario)
                .reduce(0.0, Double::sum);

        System.out.println("\n========== TOTAL GASTO COM SALARIOS APOS REAJUSTES ==========");
        System.out.println("R$ " + MOEDA.format(totalSalarios));

        Map<String, Double> mediaSalariosPorDepartamento = funcionariosReajustadosEOrdenados.stream()
                .collect(Collectors.groupingBy(
                        Funcionario::getDepartamento,
                        LinkedHashMap::new,
                        Collectors.averagingDouble(Funcionario::getSalario)
                ));

        System.out.println("\n========== MEDIA SALARIAL POR DEPARTAMENTO ==========");
        mediaSalariosPorDepartamento.forEach((departamento, media) ->
                System.out.println(departamento + ": R$ " + MOEDA.format(media))
        );
    }
}
