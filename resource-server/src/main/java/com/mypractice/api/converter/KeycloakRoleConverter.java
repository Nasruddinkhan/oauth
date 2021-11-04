package com.mypractice.api.converter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
@Component
public class KeycloakRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

	@SuppressWarnings("unchecked")
	@Override
	public Collection<GrantedAuthority> convert(Jwt jwt) {
		// TODO Auto-generated method stub
		Map<String, Object> realmAcess = jwt.getClaim("realm_access");
		System.out.println(realmAcess);
		if(Objects.isNull(realmAcess) || realmAcess.isEmpty())
			return new ArrayList<>();
	 	return ((List<String>) realmAcess.get("roles"))
	 		.stream()
	 		.map(roleName -> "ROLE_"+roleName)
	 		.map(SimpleGrantedAuthority::new)
	 		.collect(Collectors.toList());
	}

}
