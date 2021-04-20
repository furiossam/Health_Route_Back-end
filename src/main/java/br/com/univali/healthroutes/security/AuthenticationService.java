package br.com.univali.healthroutes.security;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.univali.healthroutes.api.user.dao.UserDao;
import br.com.univali.healthroutes.api.user.model.User;
import br.com.univali.healthroutes.security.model.HealthRoutesUserDetails;
import br.com.univali.healthroutes.security.service.TokenAuthenticationService;

@Service
public class AuthenticationService implements UserDetailsService {

	@Autowired
	private UserDao userRepository;

	@Override
	public HealthRoutesUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = this.userRepository.findByCpf(username);
		Map<String, Object> extraData = new HashMap<String, Object>();
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

		extraData.put(TokenAuthenticationService.USER_ID, user.getId_user());
		
		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		HealthRoutesUserDetails priceComparisonUserDetails = new HealthRoutesUserDetails(user.getCpf(), user.getLogin().getPassword(), authorities,
				extraData);
		return priceComparisonUserDetails;
	}

}
