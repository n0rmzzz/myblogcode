package com.tinywebgears.sso.cas.server;

import org.jasig.services.persondir.IPersonAttributes;
import org.jasig.services.persondir.support.CaseInsensitiveAttributeNamedPersonImpl;
import org.jasig.services.persondir.support.MultivaluedPersonAttributeUtils;
import org.jasig.services.persondir.support.jdbc.SingleRowJdbcPersonAttributeDao;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabasePersonAttributeAndRoleDao extends SingleRowJdbcPersonAttributeDao
{
    private final String rolePrefix = "ROLE_";
    private final boolean convertToUpperCase = true;
    private String authoritiesColumnName = "authorities";
    private GrantedAuthority defaultRole;

    public void setAuthoritiesColumnName(String authoritiesColumnName)
    {
        this.authoritiesColumnName = authoritiesColumnName;
    }

    public void setDefaultRole(GrantedAuthority defaultRole)
    {
        this.defaultRole = defaultRole;
    }

    public DatabasePersonAttributeAndRoleDao(DataSource ds, String sql)
    {
        super(ds, sql);
    }

    @Override
    protected List<IPersonAttributes> parseAttributeMapFromResults(List<Map<String, Object>> queryResults,
                                                                   String queryUserName)
    {
        final List<IPersonAttributes> peopleAttributes = new ArrayList<IPersonAttributes>(queryResults.size());
        for (final Map<String, Object> queryResult : queryResults)
        {
            final Map<String, List<Object>> multivaluedQueryResult = MultivaluedPersonAttributeUtils
                .toMultivaluedMap(queryResult);

            Map<String, List<Object>> newAttrs = new HashMap<String, List<Object>>();
            newAttrs.putAll(multivaluedQueryResult);
            List<Object> roleAttributes = new ArrayList<Object>();
            if (defaultRole != null)
                roleAttributes.add(defaultRole);
            if (multivaluedQueryResult.containsKey(authoritiesColumnName))
            {
                for (Object object : multivaluedQueryResult.get(authoritiesColumnName))
                {
                    String roleName = (String)object;
                    if (convertToUpperCase)
                        roleName = roleName.toUpperCase();
                    roleName = rolePrefix + roleName;
                    roleAttributes.add(new GrantedAuthorityImpl(roleName));
                }
            }
            newAttrs.put(authoritiesColumnName, roleAttributes);

            final IPersonAttributes person;
            if (queryUserName != null)
                person = new CaseInsensitiveAttributeNamedPersonImpl(queryUserName, newAttrs);
            else
                person = new CaseInsensitiveAttributeNamedPersonImpl(this.getConfiguredUserNameAttribute(), newAttrs);

            peopleAttributes.add(person);
        }
        return peopleAttributes;
    }
}
