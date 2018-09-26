import java.lang.reflect.*;

/**
A classe Pilha � uma classe gen�rica que enfileira Objetos(Object) e os primeiros a serem
adicionados s�o os primeiros a serem descartados(first in first out).

Inst�ncias desta classe permitem uma forma de se manejar o armazenamento de objetos na forma de fila.
Nela encontramos m�todos para adicionar, apagar e ver elementos de uma fila.
@authors Felipe Melchior de Britto e Gabrielle da Silva Barbosa.
@since 2018.
*/
public class Fila<X> implements Cloneable
{
	    private Object[] vetor;
	    private int qtd = 0;
	    private int inicio = 0;
	    private int fim = 0;

/**
	Construtor da classe Fila.

	@param inteiro que representa a capacidade do vetor de Object.
	@throws Exception se a capacidade for negativa.
*/
	    public Fila(int capacidade) throws Exception
	    {
			if(capacidade < 0)
			   throw new Exception("Capacidade inv�lida");

			this.vetor = new Object[capacidade];
		}

/**
	M�todo de chamar clone de um objeto quando a classe � gen�rica.

	@param objeto da classe da inst�ncia da Fila.
	@return retorna objeto clonado da classe X.
*/
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

/**
	Adiciona novo elemento da classe X no vetor de Object, verifica se classe
	tem clone para guardar o clone.

	@param objeto a ser guardado.
	@throws Exception se o objeto do par�metro for null ou se o vetor j� estiver cheio.
*/
	    public void guarde(X s) throws Exception
	    {
			if(s==null)
			   throw new Exception("Informa��o ausente");

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

/**
	Getter da classe Fila, como � first in first out ele fornece o
	�ltimo elemento do vetor.

	@throws Exception se o vetor estiver vazio(n�o h� o que mostrar).
	@return retorna objeto da primeira posi��o.
*/
	    public X getUmItem() throws Exception
	    {
			if(this.isVazia())
			   throw new Exception("Nada a recuperar");

	        if(this.vetor[this.inicio] instanceof Cloneable)
	        	return meuCloneDeX((X)this.vetor[this.inicio]);

	        return (X)this.vetor[this.inicio];
	    }

/**
	Joga fora o primeiro item da fila(primeiro item do vetor).

	@throws Exception se o vetor estiver vazio(n�o h� o que jogar fora).
*/
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

/**
	Verifica se o vetor est� cheio, ou seja, se a quantidade(qtd) de elementos
	nele � igual ao seu tamanho.

	@return true ou false de acordo com a igualdade.
*/
	    public boolean isCheia()
	    {
			return this.qtd == this.vetor.length;
		}

/**
	Verifica se o vetor est� vazio, ou seja, se a quantidade(qtd) de elementos
	nele � igual zero.

	@return true se for zero e false se for qualquer outro n�mero.
*/
	    public boolean isVazia()
	    {
			return this.qtd == 0;
		}

/**
	Retorna uma String com o primeiro elemento do vetor e a quantidade de elementos
	que h� nele.

	@return String com qtd e primeiro elemento.
*/
		public String toString()
		{
			if(this.qtd==0)
			   return "Vazia";

			return this.qtd+" elementos, sendo o primeiro "+this.vetor[inicio];
		}

/**
	Compara um objeto da classe Fila<X> com outro objeto de mesma
	classe, verificando seus elementos para ver se s�o iguais.

    @param objeto da classe Object para ser comparado, mas que �
    retornado false quanto a igualdade se esse objeto n�o for da
    classe Fila<X>.
	@return true ou false de acordo com a igualdade.
*/
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

/**
	Calcula um c�digo inteiro para o objeto(this) da classe.

	@return c�digo inteiro formado.
*/
		public int hashCode()
		{
			int ret = 666;

			ret = ret * 2 + new Integer(this.qtd).hashCode();

			for(int i=0, pos=inicio; i<this.qtd; i++, pos=(pos<vetor.length-1?pos+1:0))
				ret = ret*2 + this.vetor[pos].hashCode();

			return ret;
		}

/**
	Construtor de c�pia da classe Fila.

	@param de mesma classe da Fila<X> que serve para copi�-lo.
	@throws Exception se o modelo fornecido para c�pia for null.
*/
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

/**
	Clone da classe; utiliza construtor de c�pia para
	copiar o objeto, para que este n�o possa ser alterado
	em outras classes.

	@return objeto copiado.
*/
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