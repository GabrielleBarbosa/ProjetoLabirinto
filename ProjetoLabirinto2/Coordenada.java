
/**
A classe Coordenada representa, como o próprio nome diz, uma coordenada, no formato (x,y).

Instâncias desta classe permitem o uso de coordenadas.
Nela encontramos métodos para criar, mudar e ver a coordenada.
@authors Felipe Melchior de Britto e Gabrielle da Silva Barbosa.
@since 2018.
*/
public class Coordenada implements Cloneable
{
	protected int x;
	protected int y;

/**
	Construtor da classe Coordenada.

	@param inteiros x1 e y1 que determinam os valores da coordenada
	(x,y).
	@throws Exception se uma coordenada fornecida for negativa.
*/
	public Coordenada(int x1, int y1)throws Exception
	{
		if(x1 < 0 || y1 < 0)
		   throw new Exception("Não existem coordenadas negativas");

		this.x = x1;
		this.y = y1;
	}


    /**
		Define uma coordenada para a instância desta classe.
		Verificando se a coordenada fornecida como parãmtero é válida,
		lançando exceções caso haja erros.
		@throws Exception se a coordenada fornecida for negativa.
    */
	public void setCoordenada(int x1, int y1)throws Exception
	{
		if(x1 < 0 || y1 < 0)
		   throw new Exception("Não existem coordenadas negativas");

		this.x = x1;
		this.y = y1;
	}
	/**
		Define uma coordenada a partir de outra coordenada já existente.
		utilização: faz com que uma coordenada receba outra coordenada de forma mais simples.
	*/
	public void setCoordenada(Coordenada outra)
	{
		this.x = outra.x;
		this.y = outra.y;
	}
	/**
		retorna o valor da posição x da coordenada.
		@return retorna o valor do eixo x.
	*/
	public int getX()
	{
		return this.x;
	}
	/**
		retorna o valor da posição y da coordenada.
		@return retorna o valor do eixo y.
	*/
	public int getY()
	{
		return this.y;
	}
	/**
		Compara objetos da classe Coordenada, levando em conta o conteúdo das posições nos eixos x e y.

		@param obj objeto a ser comparado.
		@return retorna falso ou true dependendo da comparação com o objeto.
    */
	public boolean equals(Object obj)
	{
		if(obj == this)
		   return true;

		if(this.getClass() != obj.getClass())
		   return false;

		Coordenada coord = (Coordenada)obj;

		if(this.x != coord.x)
		   return false;

		if(this.y != coord.y)
		   return false;

		return true;
	}
	 /**
		Retorna as posições x e y da coordenada em forma de string.

		@return retorna String que as posições x e y.
    */
	public String toString()
	{
		String texto = "(" + this.x + "," + this.y + ")";
		return texto;
	}

	/**
		Cria código para a coordenada, levando em conta todos os seus atributos.

		@return inteiro formado como código pelos passos do hashCode().
    */
	public int hashCode()
	{
		int ret = 666;

		ret += 2 * ret + new Integer(this.x).hashCode();
		ret += 2 * ret + new Integer(this.y).hashCode();

		return ret;
	}
    /**
		Contrutor de cópia, usado pelo clone.

        @param modelo da classe Coordenada para ser usado para cópia.
		@throws Exception se o modelo para o construtor fornecido
		for null.
    */
	public Coordenada(Coordenada modelo)throws Exception
	{
		if(modelo == null)
		  throw new Exception("Modelo ausente");

	    this.x = modelo.x;
	    this.y = modelo.y;
	}

	/**
		Faz um clone usando o construtor de cópia, necessário pois há
		setters na classe.

		@return clone de coordenada formado.
    */
	public Object clone()
	{
		Coordenada ret = null;
		try
		{
			ret = new Coordenada(this);
		}
		catch(Exception erro)
		{}

		return ret;
	}
}