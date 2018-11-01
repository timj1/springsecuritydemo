package com.buutcamp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource secDataSource;

    //this method defines who are allowed to use our application
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.jdbcAuthentication().dataSource(secDataSource);

        /*
        User.UserBuilder users = User.withDefaultPasswordEncoder();

        auth.inMemoryAuthentication()
                .withUser(users.username("Dan").password("test123").roles("visitor"))
                .withUser(users.username("Miro").password("test123").roles("visitor","manager"));
                */
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()

                //defines URL's to have a role requirement
                .antMatchers("/").hasRole("visitor")
                .antMatchers("/manager").hasRole("manager")
                .and()

                ////allow people to login
                .formLogin()
                    .loginPage("/showLoginForm")
                    .loginProcessingUrl("/authenticateUser")
                    .permitAll()
                .and()

                //allow people to logout
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/logout.done").deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                    .permitAll()
                .and()
                //Customize the "forbidden" page
                .exceptionHandling()
                    .accessDeniedPage("/forbidden");
    }

    @Bean
    public UserDetailsManager userDetailsManager() {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager();
        jdbcUserDetailsManager.setDataSource(secDataSource);

        return jdbcUserDetailsManager;
    }




    /**
     *
     * --Database table and user creation--
     *
     * Create table security_demo.users (username varchar(50) primary key,
     * 								`password` varchar(68),
     * 								enabled tinyint(1));
     *
     * create table security_demo.authorities(
     * 	username varchar(50) references users(username),
     *     authority varchar(50));
     *
     *     alter table security_demo.authorities
     * 	add constraint foreign key (username) references security_demo.users(username);
     *
     *
     *
     *insert into security_demo.users values("Dan",
     * 	"{bcrypt}$2a$04$DCHOe9nWjT.iwL5ChrjhJ.okY3mR9iANdSji3Vies7D8yM8gWfluq",1);
     *
     * insert into security_demo.users values("Miro",
     * 	"{bcrypt}$2a$04$DCHOe9nWjT.iwL5ChrjhJ.okY3mR9iANdSji3Vies7D8yM8gWfluq",1);
     *
     * insert into security_demo.authorities value("Dan","ROLE_visitor");
     * insert into security_demo.authorities value("Miro","ROLE_visitor");
     * insert into security_demo.authorities value("Miro","ROLE_manager");
     *
     *
     */
}
