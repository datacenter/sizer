/**
 * 
 */
package com.cisco.acisizer;

import java.util.concurrent.TimeUnit;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.AbstractHttp11Protocol;
import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;

/**
 * @author Mahesh
 *
 */
@SpringBootApplication
@EnableAutoConfiguration(exclude = { LiquibaseAutoConfiguration.class })
public class CiscoaciApplication {

	public static void main(String[] args) {
		SpringApplication.run(CiscoaciApplication.class, args);
	}

	@Bean
	@Profile("dev")
	EmbeddedServletContainerCustomizer containerCustomizer(@Value("${keystorefile}") Resource keystoreFile,
			@Value("${keystorepass}") String keystorePass, @Value("${sslport}") String sslPort) throws Exception {

		final String absoluteKeystoreFile = keystoreFile.getFile().getAbsolutePath();
		final String keypass = keystorePass;
		if (sslPort == null) {
			sslPort = "8443";
		}

		final Integer sslPortVal = Integer.valueOf(sslPort);

		return new EmbeddedServletContainerCustomizer() {

			private Connector getSslConnector() {
				Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
				connector.setPort(sslPortVal);
				connector.setSecure(true);
				connector.setScheme("https");
				connector.setProperty("compression", "on");
				connector.setProperty("compressableMimeType",
						"text/html,text/xml,text/plain,application/json,application/xml,application/javascript");
				Http11NioProtocol proto = (Http11NioProtocol) connector.getProtocolHandler();
				proto.setSSLEnabled(true);
				proto.setKeystoreFile(absoluteKeystoreFile);
				proto.setSessionTimeout("3600");
				proto.setKeystorePass(keypass);
				proto.setKeystoreType("PKCS12");
				proto.setKeyAlias("tomcat");
				return connector;
			}

			@Override
			public void customize(ConfigurableEmbeddedServletContainer container) {
				TomcatEmbeddedServletContainerFactory tomcat = (TomcatEmbeddedServletContainerFactory) container;
				Connector connector = getSslConnector();
				connector.setProperty("compression", "on");
				connector.setProperty("compressableMimeType",
						"text/html,text/xml,text/plain,application/json,application/xml,application/javascript");
				tomcat.addAdditionalTomcatConnectors(connector);

				tomcat.addConnectorCustomizers(new TomcatConnectorCustomizer() {
					@Override
					public void customize(Connector connector) {
						AbstractHttp11Protocol httpProtocol = (AbstractHttp11Protocol) connector.getProtocolHandler();
						httpProtocol.setCompression("on");
						httpProtocol.setCompressionMinSize(64);
						connector.setProperty("compression", "on");
						connector.setProperty("compressableMimeType",
								"text/html,text/xml,text/plain,application/json,application/xml,application/javascript");
					}
				});

				tomcat.setSessionTimeout(60, TimeUnit.MINUTES);
				// tomcat.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND,
				// "/"));
			}
		};

	}

}
