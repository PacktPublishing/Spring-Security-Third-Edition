package com.packtpub.springsecurity.dataaccess;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.packtpub.springsecurity.domain.acl.AclClass;
import com.packtpub.springsecurity.domain.acl.AclEntry;
import com.packtpub.springsecurity.domain.acl.AclObjectIdentity;
import com.packtpub.springsecurity.domain.acl.AclSid;
import com.packtpub.springsecurity.repository.CalendarUserRepository;
import com.packtpub.springsecurity.repository.RoleRepository;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * TODO: Need to add spring-data repositories
 */
@Repository
public class JpaAclDao implements AclDao {


    // --- members ---
    private CalendarUserRepository userRepository;
    private RoleRepository roleRepository;
    /*
    TODO: Need to add the following:
    CustomPermission
    AclClass
    AclEntry
    --> AclObjectIdentity
    AclSid
     */

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * TODO: Need to convert to:
     * <pre><T> TypedQuery<T> createQuery(CriteriaQuery<T> criteriaQuery);</pre>
     * @param identifier
     * @param type
     * @return
     */
    @Override
    public List<ObjectIdentity> findChildren(Serializable identifier, String type) {
        Query query = entityManager.createQuery("select aoi from AclObjectIdentity aoi, AclObjectIdentity parent, AclClass aclClass where aoi.parentObject = parent and aoi.objIdClass = aclClass and parent.objIdIdentity = :objIdIdentity and parent.objIdClass = (select acl FROM AclClass acl where acl.clazz = :clazz)");
        query.setParameter("objIdIdentity", identifier);
        query.setParameter("clazz", type);

        return query.getResultList();
    }

    @Override
    public AclObjectIdentity getObjectIdentity(String type, Serializable identifier) {
        Query query = entityManager.createQuery("select aoi from AclObjectIdentity aoi, AclClass aclClass where  aoi.objIdIdentity = :objIdIdentity and aoi.objIdClass = aclClass and aclClass.clazz = :clazz)");
        query.setParameter("objIdIdentity", identifier);
        query.setParameter("clazz", type);

        return (AclObjectIdentity) query.getSingleResult();
    }

    @Override
    @Transactional
    public void createObjectIdentity(AclObjectIdentity identity) {
        // TODO Auto-generated method stub
        entityManager.persist(identity);
    }

    @Override
    public List<AclSid> findAclSidList(Boolean principal, String sidName) {
        Query query = entityManager.createQuery("select sid from AclSid sid where sid.principal=:principal and sid.sid=:sid");
        query.setParameter("principal", principal);
        query.setParameter("sid", sidName);
        return query.getResultList();
    }

    @Override
    @Transactional
    public AclSid createAclSid(AclSid sid2) {
        entityManager.persist(sid2);
        return sid2;
    }

    @Override
    public List<AclClass> findAclClassList(String type) {
        Query query = entityManager.createQuery("select clazz from AclClass clazz where clazz.clazz=:clazz");

        query.setParameter("clazz", type);
        return query.getResultList();
    }

    @Override
    @Transactional
    public AclClass createAclClass(AclClass clazz) {
        entityManager.persist(clazz);
        return clazz;
    }

    @Override
    @Transactional
    public void deleteEntries(AclObjectIdentity objectIdentity) {
        objectIdentity = entityManager.find(AclObjectIdentity.class, objectIdentity.getId());
        if(objectIdentity.getEntries()!=null) {
            for(AclEntry entry:objectIdentity.getEntries()) {
                entityManager.remove((AclEntry)entry);
            }
        }

    }

    @Override
    public void deleteObjectIdentity(AclObjectIdentity oidPrimaryKey) {
        // TODO Auto-generated method stub
        entityManager.remove(oidPrimaryKey);
    }

    @Override
    @Transactional
    public void createEntries(List<AclEntry> entries) {
        for(AclEntry entry:entries) {
            entityManager.persist(entry);
        }

    }

    @Override
    public boolean updateObjectIdentity(AclObjectIdentity aclObject) {
        // TODO Auto-generated method stub
        entityManager.merge(aclObject);
        return true;
    }

    @Override
    public AclSid findAclSid(String principal) {
        Query query = entityManager.createQuery("select sid from AclSid sid where sid.sid=:sid");

        query.setParameter("sid", principal);
        List<AclSid> results = query.getResultList();
        if(results.size()>0) {
            return results.get(0);
        }
        return null;
    }

}