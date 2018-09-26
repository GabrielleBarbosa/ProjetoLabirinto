import java.io.BufferedReader;
import java.io.FileReader;
import java.io.*;

/**
A classe Labirinto representa uma forma de se percorrer um labirinto, pegando-o
de um arquivo texto, procurando uma entrada e uma sa�da.

Inst�ncias desta classe permitem uma forma de se sair de qualquer labirinto (no formato correto).
Nela encontramos m�todos para ler arquivo de labirinto, percorr�-lo, retroceder e fornecer labirinto em String.
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
    Instancia um objeto do tipo Labirinto e, para tanto, recebe como par�metro
    o arquivo no qual se encontra o labirinto. Al�m disso, armazena esse labirinto
    numa matriz e instancia os demais atributos da classe.

    @param arquivo arquivo de labirinto a ser lido.
    @throws Exception as vari�veis instanciadas tem m�todos que lan�am excess�o e
    o arquivo passado por par�metro pode n�o existir.
    */
	public Labirinto(String arquivo) throws Exception
	{
		if(!new File(arquivo).exists())
		   throw new Exception("O arquivo de leitura de labirinto fornecido n�o existe");

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
	    Procura a entrada do labirinto, que � representada pelo caracter 'E' e deve estar
	    em alguma posi��o das bordas. Soma-se y enquanto n�o for maior que o n�mero de colunas,
	    percorrendo a parte superior, quando y chega na �ltima posi��o, soma-se x, para percorrer
	    o lado direito e assim por diante.

	    @throws Exception se a entrada do labirinto n�o for localizada.
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
		    throw new Exception("A anta que criou este labirinto n�o criou uma entrada!");
	}

    /**
    D� um passo no labirinto, caso n�o houver mais um caminho poss�vel, entra-se no
    m�todo privado retorceder que volta at� haver um outro caminho poss�vel.

    @throws Exception atual n�o pode ser usado antes de ser instanciado (m�todo de achar a Entrada).
    */
	public void andar() throws Exception
	{
		if(atual == null)
			throw new Exception("N�o h� como andar sem ter um ponto de in�cio(procure a entrada primeiro)!");

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
	    Retrocede os passos dados no labirinto at� que um outro caminho seja poss�vel, caso
	    n�o tiver, � lan�ada excess�o. Private pois s� � para ser usado no m�todo andar().

	    @throws Exception se a Pilha<Fila<Coordenada>> possibilidades estiver vazia,
	    significa que n�o h� mais caminhos para percorrer e achar a sa�da.
    */
	private void retroceder() throws Exception
	{
		if(this.possibilidades.isVazia())
		   throw new Exception("Anta!!! N�o h� caminhos");

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
	    Verifica se a posi��o(coordenada) atual possui o caracter 'S', se tiver significa que a
	    sa�da foi encontrada.
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
	    @throws Exception as vari�veis instanciadas tem m�todos que lan�am excess�o e
	    o arquivo passado por par�metro pode n�o existir.
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
		Compara objetos da classe labirinto, levando em conta o conte�do da
		matriz de labirinto.

		@param obj objeto a ser comparado.
		@return retorna falso ou true dependendo da compara��o com o objeto.
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

	    @return retorna String que cont�m labirinto completo.
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
		Cria c�digo para labirinto, levando em conta todos os seus atributos.

		@return inteiro formado como c�digo pelos passos do hashCode().
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