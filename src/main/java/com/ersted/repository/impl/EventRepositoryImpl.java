package com.ersted.repository.impl;

import com.ersted.model.Event;
import com.ersted.model.Status;
import com.ersted.repository.EventRepository;
import com.ersted.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class EventRepositoryImpl implements EventRepository {
    @Override
    public Event create(Event obj) {
        Session session = null;
        Transaction transaction = null;

        try {
            session = HibernateUtil.getSession();
            transaction = session.beginTransaction();

            obj.setStatus(Status.ACTIVE);
            Long eventId = (Long) session.save(obj);

            obj.setId(eventId);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }

        return obj;
    }

    @Override
    public Event getById(Long id) {
        Session session = null;
        Transaction transaction = null;
        Event event = null;

        try {
            session = HibernateUtil.getSession();
            transaction = session.beginTransaction();

            event = session.createQuery(
                    "from Event e " +
                            "left join fetch e.user " +
                            "left join fetch e.file " +
                            "where e.id = :id", Event.class)
                    .setParameter("id", id)
                    .getSingleResult();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }

        return event;
    }

    @Override
    public Event update(Event obj) {
        Session session = null;
        Transaction transaction = null;

        try {
            session = HibernateUtil.getSession();
            transaction = session.beginTransaction();

            session.update(obj);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }

        return obj;
    }

    @Override
    public boolean deleteById(Long id) {
        Session session = null;
        Transaction transaction = null;

        try {
            session = HibernateUtil.getSession();
            transaction = session.beginTransaction();

            Event event = session.get(Event.class, id);
            event.setStatus(Status.DELETED);

            session.update(event);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
        return true;
    }

    @Override
    public List<Event> getAll() {
        Session session = null;
        Transaction transaction = null;
        List<Event> events = null;
        try {
            session = HibernateUtil.getSession();
            transaction = session.beginTransaction();

            events = session.createQuery(
                    "from Event e " +
                            "left join fetch e.user " +
                            "left join fetch e.file", Event.class)
                    .getResultList();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
        return events;
    }
}
