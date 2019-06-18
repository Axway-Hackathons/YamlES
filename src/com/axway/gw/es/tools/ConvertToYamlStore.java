package com.axway.gw.es.tools;

import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.vordel.es.EntityStore;
import com.vordel.es.EntityStoreFactory;

public class ConvertToYamlStore {

	private final static Logger LOGGER = Logger.getLogger(ConvertToYamlStore.class.getName());
	
	TypeManager typeManager = null;
	EntityManager entityManager = null;
	EntityStore es = null;
	
	public ConvertToYamlStore(String url, Properties credentials) {
		es = EntityStoreFactory.createESForURL(url);
        es.connect(url, credentials);
        typeManager = new TypeManager(es);
        entityManager = new EntityManager(es);
      }
	
	
	public void dumpTypesAsYaml(String location) throws JsonGenerationException, JsonMappingException, IOException {
		LOGGER.info("Dumping types to " + location);
		typeManager.writeTypes(new File(location));
	}
	
	public void dumpEntitesAsYaml(String location) throws JsonGenerationException, JsonMappingException, IOException {
		LOGGER.info("Dumping entities to " + location);
		entityManager.writeEntities(new File(location));
	}
	
	//private static final String ES_TO_LOAD = "file:/C:/vordel/Axway-7.6.2/policystudio/configuration/org.eclipse.osgi/bundles/43/1/.cp/system/conf/templates/config/VordelGateway/entityStores/FactoryConfiguration-VordelGateway/PrimaryStore.xml";
//	private static final String ES_TO_LOAD = "federated:file:/C:/vordel/Axway-7.6.2/policystudio/configuration/org.eclipse.osgi/bundles/43/1/.cp/system/conf/templates/config/VordelGateway/entityStores/FactoryConfiguration-VordelGateway/configs.xml";
//	private static final String WHERE_TO_WRITE = "C:\\vordel\\es\\.types\\";
//	private static final String WHERE_TO_WRITE_ENTITIES = "C:\\vordel\\es\\root\\";

	private static String ES_TO_LOAD;
	private static String WHERE_TO_WRITE;
	private static String WHERE_TO_WRITE_ENTITIES;

	public static void main(String[] args) {
		if (args.length < 3) {
			LOGGER.info("You must provide where the existing fed file, where to write types and entities!!!!");
			System.exit(1);
		} else {
			ES_TO_LOAD = "federated:file:/" + args[0];
			WHERE_TO_WRITE = args[1];
			WHERE_TO_WRITE_ENTITIES = args[2];
		}
		try {
			ConvertToYamlStore converter = new ConvertToYamlStore(ES_TO_LOAD, new Properties());
			converter.dumpTypesAsYaml(WHERE_TO_WRITE);
			converter.dumpEntitesAsYaml(WHERE_TO_WRITE_ENTITIES);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
