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

/**
	Construtor da classe Fila.

	@param inteiro que representa a capacidade do vetor de Object.
	@throws Exception se a capacidade for negativa.
*/
	    public Fila(int capacidade) throws Exception
	    {
			if(capacidade < 0)
			   throw new Exception("Capacidade inválida");

			this.vetor = new Object[capacidade];
		}

/**
	Método de chamar clone de um objeto quando a classe é genérica.

	@param objeto da classe da instância da Fila.
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
	@throws Exception se o objeto do parâmetro for null ou se o vetor já estiver cheio.
*/
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

/**
	Getter da classe Fila, como é first in first out ele fornece o
	último elemento do vetor.

	@throws Exception se o vetor estiver vazio(não há o que mostrar).
	@return retorna objeto da primeira posição.
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

	@throws Exception se o vetor estiver vazio(não há o que jogar fora).
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
	Verifica se o vetor está cheio, ou seja, se a quantidade(qtd) de elementos
	nele é igual ao seu tamanho.

	@return true ou false de acordo com a igualdade.
*/
	    public boolean isCheia()
	    {
			return this.qtd == this.vetor.length;
		}

/**
	Verifica se o vetor está vazio, ou seja, se a quantidade(qtd) de elementos
	nele é igual zero.

	@return true se for zero e false se for qualquer outro número.
*/
	    public boolean isVazia()
	    {
			return this.qtd == 0;
		}

/**
	Retorna uma String com o primeiro elemento do vetor e a quantidade de elementos
	que há nele.

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
	classe, verificando seus elementos para ver se são iguais.

    @param objeto da classe Object para ser comparado, mas que é
    retornado false quanto a igualdade se esse objeto não for da
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
	Calcula um código inteiro para o objeto(this) da classe.

	@return código inteiro formado.
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
	Construtor de cópia da classe Fila.

	@param de mesma classe da Fila<X> que serve para copiá-lo.
	@throws Exception se o modelo fornecido para cópia for null.
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
	Clone da classe; utiliza construtor de cópia para
	copiar o objeto, para que este não possa ser alterado
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