// MIGUEL ANGELO SIMOES SOEIRO 
// NUMERO DE ALUNO: 21401797

import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.Locale;

/* INPUTS */	
/* DECLARAÇAO DA CLASS FUNCIONARIOS E SUAS VARIAVEIS (COM OS SEUS TIPOD DE DADOS) */
class Funcionarios {
	public int id;
	public int experiencia;
	public int zona_A;
	public int zona_B;
	public int zona_C;
	public double peso_A;
	public double peso_B;
	public double peso_C;
	public String nome;
}

/* DECLARAÇAO DA CLASS ENTREGAS E SUAS VARIAVEIS (COM OS SEUS TIPOD DE DADOS) */
class Entregas {
	public int ID;
	public String zona;
	public double peso;
}

/* DECLARAÇAO DA CLASS TOTAL E SUAS VARIAVEIS (COM OS SEUS TIPOD DE DADOS) */
class Total{
	public double bruto;
	public double base;
	public double bonus;
	public double liquido;
	public double imposto;
	//public double percentagem_imposto;
	public double remuneracao = 500.500;
	
	/*CALCULO DO SALARIO BASE (FUNÇAO) */
	public double salario_base ( int Nivel ) {
		base = remuneracao + (Nivel * 5);
		return base;
		}
	/*CALCULO DO SALARIO BONUS (FUNÇAO) */
	public double salario_bonus ( int zona_A, int zona_B, int zona_C ) {
		bonus = zona_A * 0.5 + zona_B *0.75 + zona_C * 1.5;
		return bonus;
		}
	/*CALCULO DO SALARIO BRUTO (FUNÇAO) */
	public double salario_bruto () {
		bruto = base + bonus;
		return bruto;	
		}
	/*CALCULO DA TAXA (FUNÇAO) */
	public int taxa () {
		if ( bruto < 505) return 0;
		else if ( 505 <= bruto && bruto < 1000) return 10;
		else if ( 1000 <= bruto && bruto < 1500) return  15 ;
		else return  20; // o retorno é 20 quando o Bruto >= 1500
		} 
	
	/* public int taxa () {
		switch {
			case ( < 505 ):
				return 0;
				break;
			case (505 <= bruto && bruto < 1000 ):
				return 10;
				break;
			case ( 1000 <= bruto && bruto < 1500):
				return 15;
				break;
			default:
				return 20;
		}
	} */   // NÃO FUNCIONA !!
	
	/*CALCULO DO VALOR DO IMPOSTO (FUNÇAO) */
	public double valor_imposto () {
		imposto = bruto * taxa() *0.01;
		return imposto;
		}
	/*CALCULO DO SALARIO LIQUIDO (FUNÇAO) */
	public double salario_liquido () {
		liquido = bruto - imposto;
		return liquido;
		}
}

public class Main {
	
	/*CALCULO DAS ENCOMENDAS TOTAIS (FUNÇAO) */
	public static int encomendas(int zona_A, int zona_B, int zona_C) {
		return zona_A + zona_B+ zona_C;
	}
	/*CALCULO DO PESO TOTAL (FUNÇAO) */
	public static double pesototal(double peso_A, double peso_B, double peso_C) {
		return peso_A + peso_B+ peso_C;
	}
	
	@SuppressWarnings("resource")
	public static void main( String[] args ) throws IOException
	{
		/* CALCULO DA DATA E HORA ACTUAL, NO FORMATO PEDIDO */
		Locale.setDefault(new Locale("en", "US"));
		java.util.Date Data = new java.util.Date();
		SimpleDateFormat Nome = new SimpleDateFormat ("yyyyMMddHHmmss");
		DecimalFormat df = new DecimalFormat ("0.000");
		
		/* CRIAÇÃO DO FICHEIRO .txt COM O REGISTO DOS OUTPUTS NO FORMATO resultados-<data-hora>.txt */	
		FileWriter Output = new FileWriter( "resultados-" + Nome.format(Data) + ".txt");
		PrintWriter Outputs = new PrintWriter (Output);
		
		/* FUNÇAO FUNCIONARIOS E FUNÇAO ENTRGAS, DECLARAÇAO DE CADA UMA */
		Funcionarios[] funcionarios = new Funcionarios[ 100 ];
		Total salario = new Total();
		Entregas entregas = new Entregas();
		int linha;
		int no_funcionarios = 0;
		Scanner scanner;
		
		/* LEITURA(scanner) DO FICHEIRO funcionarios.txt */
		try	{
			scanner = new Scanner( new File("funcionarios.txt") ).useDelimiter( "\\s*:\\s*|\\s*\n\\s*" );
			}
		catch( FileNotFoundException ex )
			{
			System.out.println( ex );
			return;
			}
		scanner.skip( "\\s*" );
		
		/* CICLO DE LEITURA DO FICHEIRO E ATRIBUIÇAO DE VALORES */
		for( linha = 0;  scanner.hasNextLine();  linha++ ) {
			funcionarios[linha] = new Funcionarios();
			funcionarios[linha].id = scanner.nextInt();
			funcionarios[linha].nome = scanner.next();
			funcionarios[linha].experiencia = scanner.nextInt();
			if (funcionarios[linha].experiencia < 0 || funcionarios[linha].experiencia > 5) {
				System.out.println("O funcionario com o id " + funcionarios[linha].id + " tem um nivel de experiencia fora dos padrões de 0 a 5.");
			}
			scanner.skip( "\\s*" );
			no_funcionarios++;
			}
		
		/* LEITURA(scanner) DO FICHEIRO entregas.txt */
		try	{
			scanner = new Scanner( new File("entregas.txt") ).useDelimiter( "\\s*:\\s*|\\s*\n\\s*" );
			}
		catch( FileNotFoundException ex )
			{
			System.out.println( ex );
			return;
			}
			scanner.skip( "\\s*" );
			
		/* CICLO DE LEITURA DO FICHEIRO E ATRIBUIÇAO DE VALORES, CONSOANTES A ZONA E O PESO DA ENTREGA */	
		for(;scanner.hasNextLine();) {
			entregas.ID = scanner.nextInt();
			for (linha = 0; linha < no_funcionarios ;  linha++) {
				if (funcionarios[linha].id == entregas.ID){
					entregas.zona = scanner.next();
					entregas.peso = scanner.nextFloat();
					if (entregas.zona.equals("A")){
						funcionarios[linha].zona_A++;
						funcionarios[linha].peso_A = funcionarios[linha].peso_A + (entregas.peso * 0.001);
						}
					else if (entregas.zona.equals("B")){
						funcionarios[linha].zona_B++;
						funcionarios[linha].peso_B = funcionarios[linha].peso_B + (entregas.peso * 0.001);
						} 
					else if (entregas.zona.equals("C")){
						funcionarios[linha].zona_C++;
						funcionarios[linha].peso_C = funcionarios[linha].peso_C + (entregas.peso * 0.001);
						}
					else 
						System.out.println("O funcionario com o id " + funcionarios[linha].id + " tem uma zona fora dos registos (A, B ou C).");
				}
			}
			scanner.skip( "\\s*" );
			}
		
		/* OUTPUTS */			
		/* FORMATO DOS OUTPUTOS DENTRO DO FICHEIRO .txt */	
		for (linha = 0; linha < no_funcionarios ;  linha++){
			Outputs.print(funcionarios[linha].nome + " | ");
			Outputs.print(funcionarios[linha].id + " | ");
			Outputs.printf(funcionarios[linha].experiencia + "%n");
			Outputs.print(encomendas(funcionarios[linha].zona_A,funcionarios[linha].zona_B,funcionarios[linha].zona_C) + " | ");
			Outputs.print(funcionarios[linha].zona_A + " | ");
			Outputs.print(funcionarios[linha].zona_B + " | ");
			Outputs.printf(funcionarios[linha].zona_C + "%n");			
			Outputs.print(df.format(pesototal(funcionarios[linha].peso_A, funcionarios[linha].peso_B, funcionarios[linha].peso_C)) + " | ");
			Outputs.print(df.format(funcionarios[linha].peso_A) + " | ");
			Outputs.print(df.format(funcionarios[linha].peso_B) + " | ");
			Outputs.printf(df.format(funcionarios[linha].peso_C) + "%n");
			Outputs.printf(df.format(salario.salario_base(funcionarios[linha].experiencia)) + "%n");		
			Outputs.printf(df.format(salario.salario_bonus(funcionarios[linha].zona_A, funcionarios[linha].zona_B, funcionarios[linha].zona_C)) + "%n");
			Outputs.printf(df.format(salario.salario_bruto()) + "%n");
			Outputs.print(salario.taxa() + "% | ");
			Outputs.printf(df.format(salario.valor_imposto())+ "%n");
			Outputs.printf(df.format(salario.salario_liquido())+ "%n %n");		
		}
		Output.close();
	}
}
