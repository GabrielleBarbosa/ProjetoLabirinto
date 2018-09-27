import coordenada.*;
import pilha.*;
import labirinto.*;
import java.io.*;

public class Programa
{
	public static void main(String args[])
	{
		try
		{
			System.out.println("Bom Dia/Noite professor ");
			System.out.println("Nos arquivos do projeto, temos três labirintos prontos para testes caso queira  utilizá-los");

			String arquivo = "";

			while(arquivo == null || arquivo == "" || !(new File(arquivo).exists()))
			{
				System.out.print("Digite o nome do arquivo de labirinto: ");

				BufferedReader teclado = new BufferedReader (new InputStreamReader(System.in));
				arquivo = teclado.readLine();
		    }

			Labirinto labirinto = new Labirinto(arquivo);

			labirinto.acharEntrada();

			while(!labirinto.achouFim())
				labirinto.andar();

            System.out.println(labirinto.toString());

            System.out.println("\nO caminho agora será mostrado: ");

			Pilha<Coordenada> caminho = labirinto.caminhoPercorrido();
			while(!caminho.isVazia())
			{
				System.out.println(caminho.getUmItem()+"");
				caminho.jogueForaUmItem();
			}
	    }
	    catch(Exception erro)
	    {System.out.print(erro.getMessage());}
	}
}