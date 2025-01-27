package com.example.gestionhoteles;

import com.example.gestionhoteles.security.JWTAuthorizationFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@SpringBootApplication
public class GestionHotelesApplication {
		public static void main(String[] args) {
			SpringApplication.run(GestionHotelesApplication.class, args);
		}

		@EnableWebSecurity
		@Configuration
		class WebSecurityConfig extends WebSecurityConfigurerAdapter {
			private static final String[] AUTH_WHITELIST = { //SWAGGER
					// -- Swagger UI v2
					"/v2/api-docs",
					"/swagger-resources",
					"/swagger-resources/**",
					"/configuration/ui",
					"/configuration/security",
					"/swagger-ui.html",
					"/webjars/**",
					// -- Swagger UI v3 (OpenAPI)
					"/v3/api-docs/**",
					"/swagger-ui/**",
					"/doc/**"
					// other public endpoints of your API may be appended to this array
			};
			@Override
			protected void configure(HttpSecurity http) throws Exception {
				http.csrf().disable()
						.addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
						.authorizeRequests()
						// PERMITE QUE LA BUSQUEDA DE HOTEL POR LOCALIDAD O CATEGORIA Y LA BUSQUEDA
						// DE UN HOTEL POR TAMAÑO Y PRECIO NO REQUIERAN NINGUN AUTENTIFICACION
						.antMatchers(HttpMethod.GET, "/api/hotel/get/localidad/{localidad}").permitAll()
						.antMatchers(HttpMethod.GET, "/api/hotel/get/categoria/{categoria}").permitAll()
						.antMatchers(HttpMethod.GET, "/api/habitacion/get/personalizado/{idHotel}/{tamanoMin}/{tamanoMax}/{precioMin}/{precioMax}").permitAll()
						.antMatchers(HttpMethod.POST, "/api/loginUser").permitAll()
						.antMatchers(AUTH_WHITELIST).permitAll() //SWAGGER

						// TODAS LAS PETICIONES RESTANTES QUE NO SE ESPECIFIQUEN COMO .permitAll() REQUERIRAN AUTENTIFICACION
						.anyRequest().authenticated();
			}
		}
	}
