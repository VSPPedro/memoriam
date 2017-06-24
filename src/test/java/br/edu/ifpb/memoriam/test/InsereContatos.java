package br.edu.ifpb.memoriam.test;

import java.text.SimpleDateFormat;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import br.edu.ifpb.memoriam.dao.ManagedEMContext;
import br.edu.ifpb.memoriam.dao.OperadoraDAO;
import br.edu.ifpb.memoriam.dao.PersistenceUtil;
import br.edu.ifpb.memoriam.entity.Operadora;

/**
 * @author fred
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class InsereContatos {
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
	 * Insere Contatos
	 */
	@Test
	public void testInsereContatos() {
		try {
			OperadoraDAO dao = new OperadoraDAO(em);
			dao.beginTransaction();
			Operadora a = new Operadora();
			a.setNome("Oi");
			a.setPrefixo(31);
			dao.insert(a);
			a = new Operadora();
			a.setNome("Vivo");
			a.setPrefixo(53);
			dao.insert(a);
			a = new Operadora();
			a.setNome("Claro");
			a.setPrefixo(41);
			dao.insert(a);
			dao.commit();
		} catch (Exception e) {
			Assert.fail("Erro de BD" + e.getMessage());
		}
	}
}
