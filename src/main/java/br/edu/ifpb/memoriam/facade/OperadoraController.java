package br.edu.ifpb.memoriam.facade;

import java.util.List;
import java.util.Map;

import br.edu.ifpb.memoriam.dao.OperadoraDAO;
import br.edu.ifpb.memoriam.entity.Contato;
import br.edu.ifpb.memoriam.entity.Operadora;

public class OperadoraController {
	
	public List<Operadora> consultar() {
		OperadoraDAO dao = new OperadoraDAO();
		List<Operadora> operadoras = dao.findAll();
		return operadoras;
	}
	
	public Operadora buscar(Map<String, String[]> parametros){
		List<Operadora> operadoras = consultar();
		String[] id = parametros.get("id");
		
		for (Operadora operadora : operadoras) {
			if (operadora.getId().toString().equals(id[0])){
				return operadora;
			}
		}
		
		System.out.println("Operadora não encontrada!");
		return null;
	}
}
