package Persistencia.Hibernate;

import logica.Anotacao;

public class AnotacaoDAO  extends Dao<Anotacao> {
	private static final long serialVersionUID = 1L;

	public AnotacaoDAO(){
           super(Anotacao.class);
	}
}
