package com.apps.ecom;

import com.apps.ecom.config.AppConstants;
import com.apps.ecom.entities.Role;
import com.apps.ecom.repositories.RoleRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class EcommerceAppApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(EcommerceAppApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper(){ return new ModelMapper(); }

	@Autowired
	private RoleRepo roleRepo;

	@Override
	public void run(String... args) throws Exception {
		try{
			Role role = new Role();
			role.setRoleId(AppConstants.ADMIN_USER);;
			role.setName("ADMIN_USER");

			Role role1 = new Role();
			role1.setRoleId(AppConstants.NORMAL_USER);
			role1.setName("NORMAL_USER");

			List<Role> roles = List.of(role, role1);
			List<Role> result = this.roleRepo.saveAll(roles);

			result.forEach(r -> {
				System.out.println(r.getName());
			});

		}catch (Exception e){
			e.printStackTrace();
		}
	}


}
