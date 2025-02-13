package technical_challenge;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    private static final BigDecimal SALARIO_MINIMO = new BigDecimal("1212.00");

    private static String formatarSalario(BigDecimal valor) {
        NumberFormat formatador = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        return formatador.format(valor);
    }

    public static void main(String[] args) {

        List<Funcionario> funcionarios = new ArrayList<>();

        funcionarios.add(new Funcionario("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador"));
        funcionarios.add(new Funcionario("Joao", LocalDate.of(1990, 5, 12), new BigDecimal("2284.38"), "Operador"));
        funcionarios.add(new Funcionario("Caio", LocalDate.of(1961, 5, 2), new BigDecimal("9836.14"), "Coordenador"));
        funcionarios.add(new Funcionario("Miguel", LocalDate.of(1988, 10, 14), new BigDecimal("19119.88"), "Diretor"));
        funcionarios.add(new Funcionario("Alice", LocalDate.of(1995, 1, 5), new BigDecimal("2234.68"), "Recepcionista"));
        funcionarios.add(new Funcionario("Heitor", LocalDate.of(1999, 11, 19), new BigDecimal("1582.72"), "Operador"));
        funcionarios.add(new Funcionario("Arthur", LocalDate.of(1993, 3, 31), new BigDecimal("4071.84"), "Contador"));
        funcionarios.add(new Funcionario("Laura", LocalDate.of(1994, 7, 8), new BigDecimal("3017.45"), "Gerente"));
        funcionarios.add(new Funcionario("Heloisa", LocalDate.of(2003, 5, 24), new BigDecimal("1606.85"), "Eletricista"));
        funcionarios.add(new Funcionario("Helena", LocalDate.of(1996, 9, 2), new BigDecimal("2799.93"), "Gerente"));

        funcionarios.forEach(System.out::println);


        //Removendo da lista o item com nome "joao"
        funcionarios.removeIf(funcionario -> funcionario.getNome().equalsIgnoreCase("joao"));

        System.out.println("\nImprimindo Lista de Funcionarios apos remover Joao\n");
        funcionarios.forEach(System.out::println);

        //Aumentando os salarios em 10%
        for (Funcionario funcionario : funcionarios) {
            BigDecimal aumento = funcionario.getSalario().multiply(BigDecimal.valueOf(0.10));
            BigDecimal salarioComAumento = funcionario.getSalario().add(aumento);
            funcionario.setSalario(salarioComAumento);
        }
        System.out.println("\nImprimindo funcionarios com aumento de 10% em seus salarios\n");
        funcionarios.forEach(System.out::println);

        Map<String, List<Funcionario>> agruparPorFuncao = funcionarios.stream()
                .collect(Collectors.groupingBy(Funcionario::getFuncao));

        System.out.println("\nFuncionarios agrupados por funcao: ");
        agruparPorFuncao.forEach((funcao, lista) -> {
            System.out.println("\nFuncao: " + funcao);
            lista.forEach(System.out::println);
        });


        System.out.println("\nImprimindo funcionarios que fazem aniversario nos meses 10 e 12\n");
        funcionarios.stream().filter(funcionario -> {
            int mesDeNascimento = funcionario.getDataNascimento().getMonthValue();
            return mesDeNascimento == 10 || mesDeNascimento == 12;
        }).forEach(System.out::println);

        Optional<Funcionario> funcionarioMaisVelho = funcionarios.stream()
                .min(Comparator.comparing(Funcionario::getDataNascimento));

        funcionarioMaisVelho.ifPresent(funcionario -> {
            int idade = Period.between(funcionario.getDataNascimento(), LocalDate.now()).getYears();
            System.out.println("\nFuncionario mais velho:\n");
            System.out.println("Nome: " + funcionario.getNome() + "| Idade: " + idade + " anos");
        });

        List<Funcionario> funcionariosEmOrdem = funcionarios.stream()
                .sorted(Comparator.comparing(Funcionario::getNome))
                .toList();
        System.out.println("\nLista de funcionario por ordem alfabetica:\n");
        funcionariosEmOrdem.forEach(System.out::println);


        BigDecimal totalSalario = funcionarios.stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println("\nTotal dos salarios: " + formatarSalario(totalSalario));


        System.out.println("\nSalarios minimo por funcionario:");
        for (Funcionario funcionario : funcionarios) {
            BigDecimal quantosSalariosMin = funcionario.getSalario()
                    .divide(SALARIO_MINIMO, 2, BigDecimal.ROUND_HALF_UP);
            System.out.println(funcionario.getNome() + " recebe: " + quantosSalariosMin + "Salarios minimos");

        }

    }



}
