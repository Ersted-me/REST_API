package com.ersted.repository.impl;

import com.ersted.model.User;
import com.ersted.model.Status;
import com.ersted.repository.UserRepository;
import com.ersted.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserRepositoryImpl implements UserRepository {
    @Override
    public User create(User obj) {
        Session session = null;
        Transaction transaction = null;

        try {
            session = HibernateUtil.getSession();
            transaction = session.beginTransaction();
            obj.setStatus(Status.ACTIVE);
            Long userId = (Long) session.save(obj);

            obj.setId(userId);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            return null;
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }

        return obj;
    }

    @Override
    public User getById(Long id) {
        Session session = null;
        Transaction transaction = null;
        User user = null;

        try {
            session = HibernateUtil.getSession();
            transaction = session.beginTransaction();

            user = session.createQuery(
                    "from User u left join fetch u.events where u.id = :id", User.class)
                    .setParameter("id", id)
                    .getSingleResult();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            return null;
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }

        return user;
    }

    @Override
    public User getByLogin(String login) {
        Session session = null;
        Transaction transaction = null;
        User user = null;

        try {
            session = HibernateUtil.getSession();
            transaction = session.beginTransaction();

            user = session.createQuery(
                    "from User u left join fetch u.events where u.login = :login", User.class)
                    .setParameter("login", login)
                    .getSingleResult();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            return null;
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }

        return user;
    }

    @Override
    public User update(User obj) {
        Session session = null;
        Transaction transaction = null;

        try {
            session = HibernateUtil.getSession();
            transaction = session.beginTransaction();

            session.update(obj);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            return null;
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

            User user = session.get(User.class, id);
            user.setStatus(Status.DELETED);

            session.update(user);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            return false;
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
        return true;
    }

    @Override
    public List<User> getAll() {
        Session session = null;
        Transaction transaction = null;
        List<User> users = null;
        try {
            session = HibernateUtil.getSession();
            transaction = session.beginTransaction();

            users = session.createQuery("from User user left join fetch user.events events", User.class)
                    .getResultList();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            return null;
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
        return users;
    }
}
