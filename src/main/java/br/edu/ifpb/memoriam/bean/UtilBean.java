package br.edu.ifpb.memoriam.bean;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifpb.memoriam.dao.OperadoraDAO;
import br.edu.ifpb.memoriam.dao.PersistenceUtil;
import br.edu.ifpb.memoriam.entity.Operadora;
import br.edu.ifpb.memoriam.entity.Perfil;

public class UtilBean {
	public List<Operadora> getOperadoras() {
		OperadoraDAO dao = new OperadoraDAO(PersistenceUtil.getCurrentEntityManager());
		List<Operadora> operadoras = dao.findAll();
		return operadoras;
	}
	
	public List<String> getPerfis(){
		List<String> perfils = new ArrayList<String>();
		
		for (Perfil perfil : Perfil.values()){
			perfils.add(perfil.getNome());
		}
		
		return perfils;
	}
}
