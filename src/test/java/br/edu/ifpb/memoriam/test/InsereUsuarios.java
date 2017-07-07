package br.edu.ifpb.memoriam.test;

import java.text.SimpleDateFormat;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import br.edu.ifpb.memoriam.dao.ManagedEMContext;
import br.edu.ifpb.memoriam.dao.PersistenceUtil;
import br.edu.ifpb.memoriam.dao.UsuarioDAO;
import br.edu.ifpb.memoriam.entity.Perfil;
import br.edu.ifpb.memoriam.entity.Usuario;
import br.edu.ifpb.memoriam.util.PasswordUtil;

public class InsereUsuarios {
	private static EntityManagerFactory emf;
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
	
	@Test
	public void testInsereUsuarios() {
		Usuario u1 = new Usuario();
		u1.setNome("Carl Sagan");
		u1.setEmail("sagan@ifpb.edu.br");
		u1.setSenha(PasswordUtil.encryptMD5("cosmos"));
		u1.setAtivo(true);
		u1.setPerfil(Perfil.BASIC);
		
		Usuario u2 = new Usuario();
		u2.setNome("Alan Turing");
		u2.setEmail("turing@ifpb.edu.br");
		u2.setSenha(PasswordUtil.encryptMD5("enigma"));
		u2.setAtivo(true);
		u2.setPerfil(Perfil.BASIC);
		
		Usuario u3 = new Usuario();
		u3.setNome("Administrador");
		u3.setEmail("admin@ifpb.edu.br");
		u3.setSenha(PasswordUtil.encryptMD5("root123"));
		u3.setAtivo(true);
		u3.setPerfil(Perfil.ADMIN);
		
		UsuarioDAO udao = new UsuarioDAO(em);
		udao.beginTransaction();
		udao.insert(u1);
		udao.insert(u2);
		udao.insert(u3);
		udao.commit();
	}
}
