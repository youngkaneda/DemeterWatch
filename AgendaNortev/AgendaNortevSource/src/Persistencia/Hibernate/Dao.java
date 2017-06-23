package Persistencia.Hibernate;

import java.io.Serializable;
import java.sql.Connection;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;


@SuppressWarnings({ "unchecked", "deprecation" })
public class Dao<E> implements Serializable {

	private static final long serialVersionUID = 1L;
	@SuppressWarnings("rawtypes")
	private Class clazz;
	private static String dataBaseURL 		= null;
	private static String dataBaseDriver 	= null;
	private static String dataBaseUser 		= null;
	private static String dataBasePassword 	= null;
	private static String dataBaseDialect 	= null;
	private String nomeClasse;

	@SuppressWarnings("rawtypes")
	public Dao(Class clazz) {		
		this.clazz = clazz;
		this.nomeClasse = clazz.getSimpleName();
	}

	public List<E> listar() throws Exception{
		Session session;
		try{
			session = HibernateUtil.getSession();
			Criteria criteria = session.createCriteria(clazz);
			criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			return criteria.list();
		}catch(Exception e){
			throw erroGenerico(nomeClasse, "listar()",e);
		}finally{
                    HibernateUtil.closeSession();
                }
	}
	
	public List<E> listar(String campoOrdenacao) throws Exception{
		Session session;
		try{
			session = HibernateUtil.getSession();
			Criteria criteria = session.createCriteria(clazz);
			criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			criteria.addOrder(Order.asc(campoOrdenacao));
			return criteria.list();
		}catch(Exception e){
			throw erroGenerico(nomeClasse, "listar(CampoOrdenacao)", e);
		}finally{
                    HibernateUtil.closeSession();
                }
	}
	
	public List<E> listar(String campoOrdenacao, String ordemPesquisa, String campoBD, String pesquisa)throws Exception {
		Session session;
		// TODO: Verificar o polimorfiscmo da classe flex
		try{
			session = HibernateUtil.getSession();
			Criteria criteria = session.createCriteria(clazz);
		
			if (pesquisa == "")		
				criteria = session.createCriteria(clazz).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);					
			else
				criteria = session.createCriteria(clazz).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).add(Restrictions.eq(campoBD, pesquisa));
		
			if("desc".equals(ordemPesquisa))
				criteria.addOrder(Order.desc(campoOrdenacao));
			else
				criteria.addOrder(Order.asc(campoOrdenacao));
		
			return criteria.list();
		}catch(Exception e){
			throw erroGenerico(nomeClasse, "listar(CampoOrdenacao, OrdemPesquisa, CampoBD, Pesquisa)", e);
		}finally{
                    HibernateUtil.closeSession();
                }
	}
		
	public Boolean salvar(E object) throws Exception {
		try {
			Transaction t = HibernateUtil.getSession().getTransaction();
			
			t.begin();
			Session session = HibernateUtil.getSession();
			session.save(object);
			t.commit();

			return true;
		} catch (Exception exe) {
			HibernateUtil.rollbackTransaction();
			throw erroGenerico(nomeClasse, "salvar("+nomeClasse+")", exe);
		}finally{
                    HibernateUtil.closeSession();
                }
	}
		
	public Boolean atualizar(E object) throws Exception {
		try {
			HibernateUtil.beginTransaction();
			Session session = HibernateUtil.getSession();
			session.merge(object);
			HibernateUtil.commitTransaction();
			return true;
		} catch (Exception exe) {
			HibernateUtil.rollbackTransaction();
			throw erroGenerico(nomeClasse, "atualizar("+nomeClasse+")", exe);
		}finally{
                    HibernateUtil.closeSession();
                }
	}
	
	public Boolean excluir(E object) throws Exception {
		try {
			HibernateUtil.beginTransaction();
			Session session = HibernateUtil.getSession();
			session.delete(object);
			HibernateUtil.commitTransaction();
			return true;
		} catch (Exception exe) {
			HibernateUtil.rollbackTransaction();
			throw erroGenerico(nomeClasse, "excluir("+nomeClasse+")", exe);
		}finally{
                    HibernateUtil.closeSession();
                }
	}

	public Boolean excluirID(Integer codigo)throws Exception{
		try{
			return excluir(pesquisarID(codigo));
		}catch(Exception exe){
			throw erroGenerico(nomeClasse, "excluirID(Inteiro)", exe);
		}finally{
                    HibernateUtil.closeSession();
                }
	}
	
	
	public E pesquisarID(Integer id) throws Exception {		
		try{
			return (E) HibernateUtil.getSession().get(clazz, id);
		}catch(Exception exe){
			throw erroGenerico(nomeClasse, "pesquisarID(Inteiro)", exe);
		}finally{
                    HibernateUtil.closeSession();
                }
	}
	
	public E pesquisarID(String id) throws Exception {		
		try{
			return (E) HibernateUtil.getSession().get(clazz, Integer.parseInt(id));
		}catch(Exception exe){
			throw erroGenerico(nomeClasse, "pesquisarID(Inteiro)", exe);
		}finally{
                    HibernateUtil.closeSession();
                }
	}
	
	public E pesquisarCampo(String campoBD, String pesquisa) throws Exception{		
		Session session;
		try{
			session = HibernateUtil.getSession();
			return (E) session.createCriteria(clazz).add(Restrictions.eq(campoBD, pesquisa)).uniqueResult();
		}catch(Exception e){
			throw erroGenerico(nomeClasse, "pesquisarCampo(CampoBD, Pesquisa)", e);
		}finally{
                    HibernateUtil.closeSession();
                }
	}
	
	public E pesquisarCampo(String campoBD, String pesquisa, String campoBD2, String pesquisa2) throws Exception{
		Session session = null;
		try{
			session = HibernateUtil.getSession();
			return (E) session.createCriteria(clazz).add(Restrictions.eq(campoBD, pesquisa))
													  .add(Restrictions.eq(campoBD2, pesquisa2))
													  .uniqueResult();
		}catch (Exception e) {
			throw erroGenerico(nomeClasse, "pesquisarCampo(CampoBD, Pesquisa, CampoBD, Pesquisa)", e);
		}finally{
                    HibernateUtil.closeSession();
                }		
	}
	
	
	
	
	
	
	
	// TODO:  Verificar a necessidade - sen�o excluir
	public E carregarByEquals(Criterion criterion) throws Exception {
		Session session = HibernateUtil.getSession();
		Criteria criteria = session.createCriteria(clazz);
		criteria.add(criterion);
                HibernateUtil.closeSession();
                
                return (E) criteria.uniqueResult();
	}
	
	// TODO: Verificar a necessidade - sen�o excluir
	public List<E> pesquisar(String campo, String argumento) throws Exception {
		Criteria criteria = HibernateUtil.getSession().createCriteria(clazz);
		criteria.add(Expression.ilike(campo, argumento, MatchMode.ANYWHERE));
                    HibernateUtil.closeSession();
             
                return criteria.list();
	}

	// Classe generica de erros
	public Exception erroGenerico(String nomeClasse, String nomeMetodo, Exception exe){		
		return new Exception("\nClasse: " + nomeClasse + "\n\nM�TODO: " +nomeMetodo +"\n\nERRO:\n" + exe.getMessage());
	}
	
	
	
	public static String getDataBaseURL() {
		return dataBaseURL;
	}

	public static void setDataBaseURL(String dataBaseURL) {
		Dao.dataBaseURL = dataBaseURL;
	}

	public static String getDataBaseDriver() {
		return dataBaseDriver;
	}

	public static void setDataBaseDriver(String dataBaseDriver) {
		Dao.dataBaseDriver = dataBaseDriver;
	}

	public static String getDataBaseUser() {
		return dataBaseUser;
	}

	public static void setDataBaseUser(String dataBaseUser) {
		Dao.dataBaseUser = dataBaseUser;
	}

	public static String getDataBasePassword() {
		return dataBasePassword;
	}

	public static void setDataBasePassword(String dataBasePassword) {
		Dao.dataBasePassword = dataBasePassword;
	}

	public static String getDataBaseDialect() {
		return dataBaseDialect;
	}

	public static void setDataBaseDialect(String dataBaseDialect) {
		Dao.dataBaseDialect = dataBaseDialect;
	}

}

