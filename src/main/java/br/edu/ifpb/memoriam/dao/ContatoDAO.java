package br.edu.ifpb.memoriam.dao;

import javax.persistence.EntityManager;

import br.edu.ifpb.memoriam.entity.Contato;

public class ContatoDAO extends GenericDAO<Contato, Integer> {
	
	public ContatoDAO() {
		this(PersistenceUtil.getCurrentEntityManager());
	}

	public ContatoDAO(EntityManager em) {
		super(em);
	}

	
}
