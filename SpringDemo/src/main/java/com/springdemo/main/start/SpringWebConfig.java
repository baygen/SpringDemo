package com.springdemo.main.start;
 
import com.mongodb.MongoClient;
import com.springdemo.entity.manager.BasicDAO;
import com.springdemo.entity.manager.DAOManager;
import com.springdemo.manager.CompanysManager;
import com.springdemo.manager.ManagerCRUD;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
 
@EnableWebMvc
@Configuration
@ComponentScan({ "com.springdemo.main" })
public class SpringWebConfig extends WebMvcConfigurerAdapter {
 
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}
	
	@Bean
	public InternalResourceViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/WEB-INF/views/jsp/");
		viewResolver.setSuffix(".jsp");
                viewResolver.setExposeContextBeansAsAttributes(true);
		return viewResolver;
	}
        
        @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
 
        @Bean
    public MongoDbFactory mongoDbFactory() throws Exception{
        return new SimpleMongoDbFactory(new MongoClient("localhost"), "testCompany");
    }
    
    @Bean
    public MongoTemplate mongoTemplate() throws Exception{
        return new MongoTemplate(mongoDbFactory());
    }
    
    @Bean
    public BasicDAO dbService() throws Exception{
        return new DAOManager(mongoTemplate());
    }
    
    @Bean
    public CompanysManager nodeManager() throws Exception{
        return new CompanysManager(dbService());
    }
}