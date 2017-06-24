package br.edu.ifpb.memoriam.dao;

import javax.persistence.EntityManager;

import br.edu.ifpb.memoriam.entity.Operadora;

public class OperadoraDAO extends GenericDAO<Operadora, Integer> {
	
	public OperadoraDAO() {
		this(PersistenceUtil.getCurrentEntityManager());
	}

	public OperadoraDAO(EntityManager em) {
		super(em);
	}

}
