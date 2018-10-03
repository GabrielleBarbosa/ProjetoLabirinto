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

            //System.out.println(labirinto.toString());


            PrintWriter resultado = new PrintWriter(new File(arquivo+".res.txt"));
            resultado.print(labirinto);
            resultado.println();
            resultado.println("Caminho percorrido: ");

			Pilha<Coordenada> caminho = labirinto.caminhoPercorrido();
			while(!caminho.isVazia())
			{
				resultado.println(caminho.getUmItem());
				caminho.jogueForaUmItem();
			}

			resultado.close();

            System.out.println("O labirinto e o caminho percorrido estão gravados em " + arquivo + ".res.txt");


	    }
	    catch(Exception erro)
	    {System.out.print(erro.getMessage());}
	}
}