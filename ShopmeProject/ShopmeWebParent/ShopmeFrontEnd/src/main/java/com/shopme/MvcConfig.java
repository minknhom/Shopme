package com.shopme;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		String dirName = "user-photos";
		Path userPhotosDir = Paths.get(dirName);

		String userPhotosPath = userPhotosDir.toFile().getAbsolutePath();

		registry.addResourceHandler("/" + dirName + "/**").addResourceLocations("file:/" + userPhotosPath + "/");
		
		
		String dirCategory = "../category-images";
		Path categoryImageDir = Paths.get(dirCategory);
		
		String categoryImagePath = categoryImageDir.toFile().getAbsolutePath();

		registry.addResourceHandler("/category-images/**").addResourceLocations("file:/" + categoryImagePath + "/");
		
		String dirBrand = "../brand-images";
		Path brandImageDir = Paths.get(dirBrand);
		
		String brandImagePath = brandImageDir.toFile().getAbsolutePath();

		registry.addResourceHandler("/brand-images/**").addResourceLocations("file:/" + brandImagePath + "/");
		
		
		String dirProduct = "../product-images";
		Path productImageDir = Paths.get(dirProduct);
		
		String productImagePath = productImageDir.toFile().getAbsolutePath();

		registry.addResourceHandler("/product-images/**").addResourceLocations("file:/" + productImagePath + "/");
	}

}
