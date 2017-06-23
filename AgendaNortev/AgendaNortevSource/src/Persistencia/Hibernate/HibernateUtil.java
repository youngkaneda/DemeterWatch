package Persistencia.Hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;

public class HibernateUtil {

	public 	static SessionFactory sessionFactory;
	private static final ThreadLocal<Session> threadSession = new ThreadLocal<Session>();
	private static final ThreadLocal<Transaction> threadTransaction = new ThreadLocal<Transaction>();
	
	static {
		
		try {  
			AnnotationConfiguration conf = new AnnotationConfiguration().configure();
			sessionFactory = conf.buildSessionFactory();
			
			System.out.println("SessionFactory Iniciada");
		} catch (Throwable ex) { 
			System.out.println("Erro ao inicializar a SessionFactory." + ex); 
			throw new ExceptionInInitializerError(ex); 
		} 
	}
	
	public static Session getSession() throws Exception {
		Session session = threadSession.get();
		
		if (sessionFactory == null){
			try {  
				sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
				System.out.println("SessionFactory Iniciada");
			} catch (Throwable ex) { 
				System.out.println("Erro ao inicializar a SessionFactory." + ex); 
				throw new ExceptionInInitializerError(ex); 
			} 
		}
		
		if (session == null) {
			System.out.println("Sess�o Iniciada");
			session = sessionFactory.openSession();		
			threadSession.set(session);
		}
		return session;
	}
	
	public static void closeSession() throws Exception {
		Session s = threadSession.get();
		threadSession.set(null);
		if (s != null && s.isOpen()) {
			s.close();
		}
		System.out.println("Sess�o Fechada");
	}

	public static void beginTransaction() throws Exception {
		Transaction transaction = threadTransaction.get();
		if (transaction == null) {
			transaction = getSession().beginTransaction();
			threadTransaction.set(transaction);
		}
	}

	public static void commitTransaction() throws Exception {
		Transaction transaction = threadTransaction.get();
		if (transaction != null && !transaction.wasCommitted() && !transaction.wasRolledBack()) {
			transaction.commit();
		}
		threadTransaction.set(null);
	}

	public static void rollbackTransaction() throws Exception {
		Transaction transaction = threadTransaction.get();
		threadTransaction.set(null);
		if (transaction != null && !transaction.wasCommitted() && !transaction.wasRolledBack()) {
			transaction.rollback();
		}
	}
	
	public static void refresh(Object object) throws Exception {
		getSession().refresh(object);
	}

	

}

