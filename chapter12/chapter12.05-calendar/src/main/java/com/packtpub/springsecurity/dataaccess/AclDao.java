package com.packtpub.springsecurity.dataaccess;

import java.io.Serializable;
import java.util.List;

import com.packtpub.springsecurity.domain.acl.AclClass;
import com.packtpub.springsecurity.domain.acl.AclEntry;
import com.packtpub.springsecurity.domain.acl.AclObjectIdentity;
import com.packtpub.springsecurity.domain.acl.AclSid;
import org.springframework.security.acls.model.ObjectIdentity;

public interface AclDao {

    List<ObjectIdentity> findChildren(Serializable identifier, String type);

    AclObjectIdentity getObjectIdentity(String type, Serializable identifier);

    void createObjectIdentity(AclObjectIdentity identity);

    List<AclSid> findAclSidList(Boolean valueOf, String sidName);

    AclSid createAclSid(AclSid sid2);

    List<AclClass> findAclClassList(String type);

    AclClass createAclClass(AclClass clazz);

    void deleteEntries(AclObjectIdentity oidPrimaryKey);

    void deleteObjectIdentity(AclObjectIdentity oidPrimaryKey);

    void createEntries(List<AclEntry> entries);

    boolean updateObjectIdentity(AclObjectIdentity aclObject);

    AclSid findAclSid(String principal);

}
