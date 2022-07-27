package com.ersted.repository.impl;

import com.ersted.model.File;
import com.ersted.model.Status;
import com.ersted.repository.FileRepository;
import com.ersted.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class FileRepositoryImpl implements FileRepository {
    @Override
    public File create(File obj) {
        Session session = null;
        Transaction transaction = null;

        try {
            session = HibernateUtil.getSession();
            transaction = session.beginTransaction();

            obj.setStatus(Status.ACTIVE);
            Long fileId = (Long) session.save(obj);

            obj.setId(fileId);

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
    public File getById(Long id) {
        Session session = null;
        Transaction transaction = null;
        File file = null;

        try {
            session = HibernateUtil.getSession();
            transaction = session.beginTransaction();

            file = session.createQuery(
                    "from File f left join fetch f.user where f.id = :id", File.class)
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

        return file;
    }

    @Override
    public File update(File obj) {
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

            File file = session.get(File.class, id);
            file.setStatus(Status.DELETED);

            session.update(file);

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
    public List<File> getAll() {
        Session session = null;
        Transaction transaction = null;
        List<File> files = null;
        try {
            session = HibernateUtil.getSession();
            transaction = session.beginTransaction();

            files = session.createQuery("from File f left join fetch f.user", File.class)
                    .getResultList();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            return null;
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
        return files;
    }
}
