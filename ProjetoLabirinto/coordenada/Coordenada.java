package coordenada;

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

	public Coordenada(int x1, int y1)throws Exception
	{
		if(x1 < 0 || y1 < 0)
		   throw new Exception("Não existem coordenadas negativas");

		this.x = x1;
		this.y = y1;
	}

	public void setCoordenada(int x1, int y1)throws Exception
	{
		if(x1 < 0 || y1 < 0)
		   throw new Exception("Não existem coordenadas negativas");

		this.x = x1;
		this.y = y1;
	}

	public void setCoordenada(Coordenada outra)
	{
		this.x = outra.x;
		this.y = outra.y;
	}

	public int getX()
	{
		return this.x;
	}

	public int getY()
	{
		return this.y;
	}

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

	public String toString()
	{
		String texto = "(" + this.x + "," + this.y + ")";
		return texto;
	}

	public int hashCode()
	{
		int ret = 666;

		ret += 2 * ret + new Integer(this.x).hashCode();
		ret += 2 * ret + new Integer(this.y).hashCode();

		return ret;
	}

	public Coordenada(Coordenada modelo)throws Exception
	{
		if(modelo == null)
		  throw new Exception("Modelo ausente");

	    this.x = modelo.x;
	    this.y = modelo.y;
	}

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