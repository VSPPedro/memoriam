package br.edu.ifpb.memoriam.test;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import br.edu.ifpb.memoriam.dao.ContatoDAO;
import br.edu.ifpb.memoriam.dao.ManagedEMContext;
import br.edu.ifpb.memoriam.dao.OperadoraDAO;
import br.edu.ifpb.memoriam.dao.PersistenceUtil;
import br.edu.ifpb.memoriam.entity.Contato;
import br.edu.ifpb.memoriam.entity.Operadora;

/**
 * @author fred
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class InsereOperadoras {
	private static EntityManagerFactory emf;
	private static SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
	private EntityManager em;

	@BeforeClass
	public static void init() {
		PersistenceUtil.createEntityManagerFactory("memoriam");
		emf = PersistenceUtil.getEntityManagerFactory();
		ManagedEMContext.bind(emf, emf.createEntityManager());
		System.out.println("init()");
	}

	@AfterClass
	public static void destroy() {
		if (emf != null) {
			emf.close();
			System.out.println("destroy()");
		}
	}

	@Before
	public void initEM() {
		em = emf.createEntityManager();
	}
	
	/**
	 * Insere Operadoras
	 */
	@Test
	public void test02InsereOperadoras() {
		try {
			OperadoraDAO odao = new OperadoraDAO(em);
			Operadora o1 = odao.find(1);
			System.out.println("Resultado da busca do odao: " + o1);
			Operadora o2 = odao.find(2);
			Operadora o3 = odao.find(3);
			
			ContatoDAO dao = new ContatoDAO(em);
			dao.beginTransaction();
			Contato a = new Contato();
			a.setNome("Jose Carlos da Silva");
			a.setFone("3422-9900");
			a.setDataAniversario(new Date());
			a.setOperadora(o1);
			dao.insert(a);
			a = new Contato();
			a.setNome("Maria Clara dos Santos");
			a.setFone("3662-5536");
			a.setDataAniversario(new Date());
			a.setOperadora(o2);
			dao.insert(a);
			a = new Contato();
			a.setNome("Joao Firmino da Costa");
			a.setFone("3556-8433");
			a.setDataAniversario(new Date());
			a.setOperadora(o1);
			dao.insert(a);
			a = new Contato();
			a.setNome("Priscila Almeida Pontes");
			a.setFone("3417-4237");
			a.setDataAniversario(new Date());
			a.setOperadora(o3);
			dao.insert(a);
			a = new Contato();
			a.setNome("Walter Pontes Fontes");
			a.setFone("3417-4645");
			a.setDataAniversario(new Date());
			a.setOperadora(o2);
			dao.insert(a);
			a = new Contato();
			a.setNome("Amanda Correia Lima");
			a.setFone("9888-4099");
			a.setDataAniversario(new Date());
			a.setOperadora(o2);
			dao.insert(a);
			a = new Contato();
			a.setNome("Rogerio Nunes");
			a.setFone("98388-4787");
			a.setOperadora(o1);
			a.setDataAniversario(new Date());
			dao.insert(a);
			a = new Contato();
			a.setNome("Carol Soares Barbosa");
			a.setFone("98747-4849");
			a.setDataAniversario(new Date());
			a.setOperadora(o3);
			dao.insert(a);
			a = new Contato();
			a.setNome("Cesar Ferreira da Silva");
			a.setFone("98821-4899");
			a.setDataAniversario(new Date());
			a.setOperadora(o1);
			dao.insert(a);
			a = new Contato();
			a.setNome("Natalia Seixas Gomes");
			a.setFone("94432-0199");
			a.setDataAniversario(new Date());
			a.setOperadora(o2);
			dao.insert(a);
			dao.commit();
		} catch (Exception e) {
			Assert.fail("Erro de BD: " + e);
		}
	}

}
