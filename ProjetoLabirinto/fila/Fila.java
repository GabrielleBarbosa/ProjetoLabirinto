package fila;
import java.lang.reflect.*;

/**
A classe Pilha é uma classe genérica que enfileira Objetos(Object) e os primeiros a serem
adicionados são os primeiros a serem descartados(first in first out).

Instâncias desta classe permitem uma forma de se manejar o armazenamento de objetos na forma de fila.
Nela encontramos métodos para adicionar, apagar e ver elementos de uma fila.
@authors Felipe Melchior de Britto e Gabrielle da Silva Barbosa.
@since 2018.
*/
public class Fila<X> implements Cloneable
{
	    private Object[] vetor;
	    private int qtd = 0;
	    private int inicio = 0;
	    private int fim = 0;

	    public Fila(int capacidade) throws Exception
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
				Class<?>[] tiposDeParametrosFormais = null;
				Method metodo = classe.getMethod("clone", tiposDeParametrosFormais);
				Object[] tiposDeParametrosReais = null;
				ret = (X)metodo.invoke(x,tiposDeParametrosReais);
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
			   throw new Exception("Fila cheia");

			if(s instanceof Cloneable)
			{
				if(fim == this.vetor.length-1)
				{
				   fim = 0;
				   this.vetor[this.fim] = meuCloneDeX(s);
				}
				else
					this.vetor[this.fim++] = meuCloneDeX(s);
			}
			else
			{
				if(fim == this.vetor.length-1)
				{
				   fim = 0;
				   this.vetor[this.fim] = s;
				}
				else
					this.vetor[this.fim++] = s;
			}
			this.qtd++;
	    }

	    public X getUmItem() throws Exception
	    {
			if(this.isVazia())
			   throw new Exception("Nada a recuperar");

	        if(this.vetor[this.inicio] instanceof Cloneable)
	        	return meuCloneDeX((X)this.vetor[this.inicio]);

	        return (X)this.vetor[this.inicio];
	    }

	    public void jogueForaUmItem() throws Exception
	    {
			if(this.isVazia())
			   throw new Exception("Pilha vazia");

			this.vetor[this.inicio] = null;

			if(this.inicio == this.vetor.length-1)
			   inicio = 0;
			else
				inicio++;

	        qtd--;
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

			return this.qtd+" elementos, sendo o primeiro "+this.vetor[inicio];
		}

		public boolean equals (Object obj)
		{
			if(this==obj)
			   return true;

			if(obj == null)
			   return false;

			if(this.getClass()!=obj.getClass())
			   return false;

			Fila<X> fila = (Fila<X>)obj;

	        if(this.qtd!=fila.qtd)
	           return false;

	        for(int i = 0,
	                posThis=this.inicio,
	                posFila=fila.inicio;

	            i < this.qtd;

	            i++,
	            posThis=(posThis<this.vetor.length-1?posThis+1:0),
	            posFila=(posFila<fila.vetor.length-1?posFila+1:0))

	           if(!this.vetor[posThis].equals(fila.vetor[posFila]))
	              return false;

	        return true;
		}

		public int hashCode()
		{
			int ret = 666;

			ret = ret * 2 + new Integer(this.qtd).hashCode();

			for(int i=0, pos=inicio; i<this.qtd; i++, pos=(pos<vetor.length-1?pos+1:0))
				ret = ret*2 + this.vetor[pos].hashCode();

			return ret;
		}

		public Fila (Fila<X> modelo) throws Exception
		{
			if(modelo == null)
		    	throw new Exception("Modelo ausente");

			this.qtd = modelo.qtd;

			this.inicio = modelo.inicio;

			this.fim = modelo.fim;

			this.vetor = new Object[modelo.vetor.length];

			for(int i=0; i<modelo.vetor.length-1; i++)
		    	this.vetor[i] = modelo.vetor[i];
		}

		public Object clone()
		{
			Fila<X> ret = null;
			try
			{
				ret = new Fila<X>(this);
			}
			catch(Exception erro)
			{}

			return ret;
		}

}