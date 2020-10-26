package com.example;

import junit.framework.TestCase;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.Collections;

public class CompositeIdQueryTest extends TestCase {
  private SessionFactory sessionFactory;

  @Override
  protected void setUp() throws Exception {
    // A SessionFactory is set up once for an application
    sessionFactory = new Configuration()
      .configure() // configures settings from hibernate.cfg.xml
      .buildSessionFactory();
  }

  @Override
  protected void tearDown() throws Exception {
    if (sessionFactory != null) {
      sessionFactory.close();
    }
  }

  public void test() {
    var domainA = new DomainA();
    var domainB = new DomainB();
    var composite = new CompositeIdDomain(domainA, domainB, "foo");
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    session.save(domainA);
    session.save(domainB);
    session.save(composite);
    session.getTransaction().commit();
    session.close();

    session = sessionFactory.openSession();
    session.beginTransaction();

    // Basic Queries
    assertEquals(session.createQuery("select count(*) from DomainA").list().get(0), 1L);
    assertEquals(session.createQuery("select count(*) from DomainB").list().get(0), 1L);
    assertEquals(session.createQuery("select count(*) from CompositeIdDomain").list().get(0), 1L);

    // Basic JPA Query
    var criteriaBuilder = session.getCriteriaBuilder();
    var criteriaQuery = criteriaBuilder.createQuery();
    var root = criteriaQuery.from(CompositeIdDomain.class);
    criteriaQuery.select(root.get("otherProperty"));
    var query = session.createQuery(criteriaQuery);
    assertEquals(query.list(), new ArrayList<>(Collections.singletonList("foo")));

    // Basic JPA Query
    criteriaQuery = criteriaBuilder.createQuery();
    root = criteriaQuery.from(CompositeIdDomain.class);
    criteriaQuery.select(root.get("otherProperty"));
    criteriaQuery.where(criteriaBuilder.equal(root.get("domainA"), domainA));
    query = session.createQuery(criteriaQuery);
    assertEquals(query.list(), new ArrayList<>(Collections.singletonList("foo")));

    session.getTransaction().commit();
    session.close();
  }
}
