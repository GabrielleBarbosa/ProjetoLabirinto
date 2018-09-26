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
    protected int qtd = 0;

/**
	Construtor da classe Pilha.

	@param inteiro que representa a capacidade do vetor de Object.
	@throws Exception se a capacidade for negativa.
*/
    public Pilha(int capacidade) throws Exception
    {
		if(capacidade < 0)
		   throw new Exception("Capacidade inválida");

		this.vetor = new Object[capacidade];
	}

/**
	Método de chamar clone de um objeto quando a classe é genérica.

	@param objeto da classe da instância da Pilha.
	@return retorna objeto clonado da classe X.
*/
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
		   throw new Exception("Pilha cheio");

		if(s instanceof Cloneable)
			this.vetor[this.qtd] = meuCloneDeX(s);
		else
			this.vetor[this.qtd] = s;
        this.qtd++;
    }

/**
	Getter da classe Pilha, como é first in last out ele fornece o
	último elemento do vetor.

	@throws Exception se o vetor estiver vazio(não há o que mostrar).
	@return retorna objeto da última posição.
*/
    public X getUmItem() throws Exception
    {
		if(this.isVazia())
		   throw new Exception("Nada a recuperar");

		if(this.vetor[this.qtd-1] instanceof Cloneable)
        	return meuCloneDeX((X)this.vetor[this.qtd-1]);

        return (X)this.vetor[this.qtd-1];
    }

/**
	Joga fora o último item da pilha(último item do vetor).

	@throws Exception se o vetor estiver vazio(não há o que jogar fora).
*/
    public void jogueForaUmItem() throws Exception
    {
		if(this.isVazia())
		   throw new Exception("Pilha vazia");

        this.qtd--;
        this.vetor[this.qtd] = null;
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
	Retorna uma String com o último elemento do vetor e a quantidade de elementos
	que há nele.

	@return String com qtd e último elemento.
*/
	public String toString()
	{
		if(this.qtd==0)
		   return "Vazia";

		return this.qtd+" elementos, sendo o último "+this.vetor[this.qtd-1];
	}

/**
	Compara um objeto da classe Pilha<X> com outro objeto de mesma
	classe, verificando seus elementos para ver se são iguais.

    @param objeto da classe Object para ser comparado, mas que é
    retornado false quanto a igualdade se esse objeto não for da
    classe Pilha<X>.
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

		Pilha<X> pil = (Pilha<X>)obj;

        if(this.qtd!=pil.qtd)
           return false;

        for(int i = 0; i < this.qtd; i++)
           if(!this.vetor[i].equals(pil.vetor[i]))
              return false;

        return true;
	}

/**
	Calcula um código inteiro para o objeto(this) da classe.

	@return código inteiro formado.
*/
	public int hashCode()
	{
		int ret = 1;

		ret = ret * 2 + new Integer(this.qtd).hashCode();

		for(int i=0; i<this.qtd; i++)
			ret = ret*2 + this.vetor[i].hashCode();

		return ret;
	}

/**
	Construtor de cópia da classe Pilha.

	@param de mesma classe da Pilha<X> que serve para copiá-lo.
	@throws Exception se o modelo fornecido para cópia for null.
*/
	public Pilha (Pilha<X> modelo) throws Exception
	{
		if(modelo == null)
		   throw new Exception("Modelo ausente");

		this.qtd = modelo.qtd;

		this.vetor = new Object[modelo.vetor.length];

		for(int i=0; i<=modelo.qtd; i++)
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
