import java.io.BufferedReader;
import java.io.FileReader;
import java.io.*;

/**
A classe Labirinto representa uma forma de se percorrer um labirinto, pegando-o
de um arquivo texto, procurando uma entrada e uma saída.

Instâncias desta classe permitem uma forma de se sair de qualquer labirinto (no formato correto).
Nela encontramos métodos para ler arquivo de labirinto, percorrê-lo, retroceder e fornecer labirinto em String.
@authors Felipe Melchior de Britto e Gabrielle da Silva Barbosa.
@since 2018.
*/
public class Labirinto
{
	protected char[][] labirinto;
	protected Coordenada atual;
	protected Pilha<Coordenada> caminho;
	protected Pilha<Fila<Coordenada>> possibilidades;
	protected Fila<Coordenada> fila;
	protected int linhas;
	protected int colunas;

    /**
    Instancia um objeto do tipo Labirinto e, para tanto, recebe como parâmetro
    o arquivo no qual se encontra o labirinto. Além disso, armazena esse labirinto
    numa matriz e instancia os demais atributos da classe.

    @param arquivo arquivo de labirinto a ser lido.
    @throws Exception as variáveis instanciadas tem métodos que lançam excessão e
    o arquivo passado por parâmetro pode não existir.
    */
	public Labirinto(String arquivo) throws Exception
	{
		if(!new File(arquivo).exists())
		   throw new Exception("O arquivo de leitura de labirinto fornecido não existe");

		FileReader arq = new FileReader(arquivo);
		BufferedReader leitorDeArq = new BufferedReader(arq);

		this.linhas = Integer.parseInt(leitorDeArq.readLine());
		this.colunas = Integer.parseInt(leitorDeArq.readLine());

		labirinto = new char[this.linhas][this.colunas];

		for(int x=0; x<this.linhas; x++)
		{
			String linhaArq = leitorDeArq.readLine();
			for(int y=0; y<this.colunas; y++)
			   this.labirinto[x][y] = linhaArq.charAt(y);
	    }

	    caminho = new Pilha<Coordenada>(colunas * linhas);
	    possibilidades = new Pilha<Fila<Coordenada>>(linhas*colunas);
		arq.close();
	}
	/**
	    Procura a entrada do labirinto, que é representada pelo caracter 'E' e deve estar
	    em alguma posição das bordas. Soma-se y enquanto não for maior que o número de colunas,
	    percorrendo a parte superior, quando y chega na última posição, soma-se x, para percorrer
	    o lado direito e assim por diante.

	    @throws Exception se a entrada do labirinto não for localizada.
    */
	public void acharEntrada() throws Exception
	{
		Boolean achado = false;
		int x=0, y=0;
		for(int i=1; i<(2*colunas+2*linhas)-4 || !achado; i++)
		{
			if(labirinto[x][y] == 'E')
			{
			   this.atual = new Coordenada(x, y);
			   achado = true;
			}

			if(x == 0 && y < colunas - 1)
			   y++;
			else if(y == colunas - 1 && x < linhas - 1)
			   x++;
			else if(x == linhas - 1 && y > 0)
			   y--;
			else if(y == 0 && x > 0)
			   x-;
		}-

		if(!achado)
		    throw new Exception("A anta que criou este labirinto não criou uma entrada!");
	}

    /**
    Dá um passo no labirinto, caso não houver mais um caminho possível, entra-se no
    método privado retorceder que volta até haver um outro caminho possível.

    @throws Exception atual não pode ser usado antes de ser instanciado (método de achar a Entrada).
    */
	public void andar() throws Exception
	{
		if(atual == null)
			throw new Exception("Não há como andar sem ter um ponto de início(procure a entrada primeiro)!");

		this.fila = new Fila<Coordenada>(3);
		int x = this.atual.getX();
		int y = this.atual.getY();

		if(x-1>=0)
		   if (this.labirinto[x-1][y] == ' ' || this.labirinto[x-1][y] == 'S')
			  this.fila.guarde(new Coordenada(x-1,y));

		if(x+1<this.linhas)
		   if(this.labirinto[x+1][y] == ' ' ||this.labirinto[x+1][y] == 'S' )
			  this.fila.guarde(new Coordenada(x+1,y));

		if(y+1<this.colunas)
		   if(this.labirinto[x][y+1] == ' ' ||this.labirinto[x][y+1] == 'S' )
			  this.fila.guarde(new Coordenada(x,y+1));

		if(y-1>=0)
		   if(this.labirinto[x][y-1] == ' ' ||this.labirinto[x][y-1] == 'S' )
			  this.fila.guarde(new Coordenada(x,y-1));


		while(this.fila.isVazia())
		{
			retroceder();
		}

		this.atual.setCoordenada(fila.getUmItem());
		this.fila.jogueForaUmItem();

		if(!achouFim())
		{
			this.labirinto[atual.getX()][atual.getY()] = '*';
			this.caminho.guarde(atual);
			this.possibilidades.guarde(fila);
		}
	}

	/**
	    Retrocede os passos dados no labirinto até que um outro caminho seja possível, caso
	    não tiver, é lançada excessão. Private pois só é para ser usado no método andar().

	    @throws Exception se a Pilha<Fila<Coordenada>> possibilidades estiver vazia,
	    significa que não há mais caminhos para percorrer e achar a saída.
    */
	private void retroceder() throws Exception
	{
		if(this.possibilidades.isVazia())
		   throw new Exception("Anta!!! Não há caminhos");

		this.fila = new Fila<Coordenada>(3);
		this.labirinto[atual.getX()][atual.getY()] = ' ';
		this.atual = caminho.getUmItem();
		this.caminho.jogueForaUmItem();

		/*Fila<Coordenada> semCriatividade = possibilidades.getUmItem();


		while(!semCriatividade.isVazia())
		{
		   fila.guarde(semCriatividade.getUmItem());
		   semCriatividade.jogueForaUmItem();
		}*/

		this.fila = possibilidades.getUmItem();
		this.possibilidades.jogueForaUmItem();
	}

	/**
	    Verifica se a posição(coordenada) atual possui o caracter 'S', se tiver significa que a
	    saída foi encontrada.
    */
	public Boolean achouFim()
	{
		if(this.atual == null)
		   return false;

		if(this.labirinto[atual.getX()][atual.getY()] == 'S')
		   return true;

		return false;
	}

	/**
	    Desempilha a pilha de coordenadas caminho em inverso, deixando na ordem
	    correta e retornando inverso.

	    @param arquivo arquivo de labirinto a ser lido.
	    @throws Exception as variáveis instanciadas tem métodos que lançam excessão e
	    o arquivo passado por parâmetro pode não existir.
    */
	public Pilha<Coordenada> caminhoPercorrido() throws Exception
	{
		Pilha<Coordenada> inverso = new Pilha<Coordenada> (colunas*linhas);

		while(!caminho.isVazia())
		{
			inverso.guarde(caminho.getUmItem());
			this.caminho.jogueForaUmItem();
		}

		return inverso;
	}

	/**
		Compara objetos da classe labirinto, levando em conta o conteúdo da
		matriz de labirinto.

		@param obj objeto a ser comparado.
		@return retorna falso ou true dependendo da comparação com o objeto.
    */
	public boolean equals(Object obj)
	{
		if(obj == null)
		   return false;

		if(this == obj)
		   return true;

		if(this.getClass() != obj.getClass())
		   return false;

		Labirinto lab = (Labirinto)obj;

	    if(this.colunas != lab.colunas)
	       return false;

	    if(this.linhas != lab.linhas)
	       return false;

	    for(int x=0; x<this.linhas; x++)
	       for(int y=0; y<this.colunas; y++)
	          if(this.labirinto[x][y] != lab.labirinto[x][y])
	             return false;

	    return true;
	}

   /**
	    Retorna o labirinto completo(caminho) em forma de string, pulando linha ("\n")
	    quando se muda a linha do labirinto.

	    @return retorna String que contém labirinto completo.
    */
	public String toString()
	{
		String linhasMatriz = "";

		for(int x1=0; x1<this.linhas; x1++)
		{
			for(int y1=0; y1<this.colunas; y1++)
				linhasMatriz += this.labirinto[x1][y1] + "";

			linhasMatriz += "\n";
	    }

		return linhasMatriz;
	}

	/**
		Cria código para labirinto, levando em conta todos os seus atributos.

		@return inteiro formado como código pelos passos do hashCode().
    */
	public int hashCode()
	{
		int ret = 1;

		ret += ret*2 + this.atual.hashCode();
		ret += ret*2 + this.caminho.hashCode();
		ret += ret*2 + this.possibilidades.hashCode();
		ret += ret*2 + this.fila.hashCode();
		ret += ret*2 + new Integer(this.linhas).hashCode();
		ret += ret*2 + new Integer(this.colunas).hashCode();

		for(int x=0; x<this.linhas; x++)
			for(int y=0; y<this.colunas; y++)
			    ret += ret*2 + new Character(labirinto[x][y]).hashCode();

		return ret;
	}
}