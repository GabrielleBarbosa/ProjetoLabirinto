package pilha;
import java.lang.reflect.*;

/**
A classe Pilha é uma classe genérica que empilha Objetos(Object) e os últimos a serem
adicionados são os primeiros a serem descartados(first in last out).

Instâncias desta classe permitem o armazenamento de objetos da forma como uma pilha funciona.
Nela encontramos métodos para adicionar, jogar fora e consultar o último elemento da pilha.
@authors Felipe Melchior de Britto e Gabrielle da Silva Barbosa.
@since 2018.
*/
public class Pilha<X> implements Cloneable
{
    protected Object[] vetor;
    private int qtd = 0;

    public Pilha(int capacidade) throws Exception
    {
		if(capacidade < 0)
		   throw new Exception("Capacidade inválida");

		this.vetor = new Object[capacidade];
	}

	private X meuCloneDeX(X x)
	{
		X ret = null;
		try
		{
			Class<?> classe = x.getClass();
			Class<?>[] tiposDosParametrosFormais = null;
			Method metodo = classe.getMethod("clone", tiposDosParametrosFormais);
			Object[] parametrosReais = null;
			ret = (X)metodo.invoke(x, parametrosReais);
		}
		catch(NoSuchMethodException erro)
		{}
		catch(IllegalAccessException erro)
		{}
		catch(InvocationTargetException erro)
		{}

		return ret;
	}

    public void guarde(X s) throws Exception
    {
		if(s==null)
		   throw new Exception("Informação ausente");

		if(this.isCheia())
		   throw new Exception("Pilha cheio");

		if(s instanceof Cloneable)
			this.vetor[this.qtd] = meuCloneDeX(s);
		else
			this.vetor[this.qtd] = s;
        this.qtd++;
    }

    public X getUmItem() throws Exception
    {
		if(this.isVazia())
		   throw new Exception("Nada a recuperar");

		if(this.vetor[this.qtd-1] instanceof Cloneable)
        	return meuCloneDeX((X)this.vetor[this.qtd-1]);

        return (X)this.vetor[this.qtd-1];
    }

    public void jogueForaUmItem() throws Exception
    {
		if(this.isVazia())
		   throw new Exception("Pilha vazia");

        this.qtd--;
        this.vetor[this.qtd] = null;
    }

    public boolean isCheia()
    {
		return this.qtd == this.vetor.length;
	}

    public boolean isVazia()
    {
		return this.qtd == 0;
	}

	public String toString()
	{
		if(this.qtd==0)
		   return "Vazia";

		return this.qtd+" elementos, sendo o último "+this.vetor[this.qtd-1];
	}

	public boolean equals (Object obj)
	{
		if(this==obj)
		   return true;

		if(obj == null)
		   return false;

		if(this.getClass()!=obj.getClass())
		   return false;

		Pilha<X> pil = (Pilha<X>)obj;

        if(this.qtd!=pil.qtd)
           return false;

        for(int i = 0; i < this.qtd; i++)
           if(!this.vetor[i].equals(pil.vetor[i]))
              return false;

        return true;
	}

	public int hashCode()
	{
		int ret = 1;

		ret = ret * 2 + new Integer(this.qtd).hashCode();

		for(int i=0; i<this.qtd; i++)
			ret = ret*2 + this.vetor[i].hashCode();

		return ret;
	}

	public Pilha (Pilha<X> modelo) throws Exception
	{
		if(modelo == null)
		   throw new Exception("Modelo ausente");

		this.qtd = modelo.qtd;

		this.vetor = new Object[modelo.vetor.length];

		for(int i=0; i<=modelo.qtd; i++)
		    this.vetor[i] = modelo.vetor[i];
	}

	public Object clone()
	{
		Pilha<X> ret = null;
		try
		{
			ret = new Pilha<X>(this);
		}
		catch(Exception erro)
		{}

		return ret;
	}
}
