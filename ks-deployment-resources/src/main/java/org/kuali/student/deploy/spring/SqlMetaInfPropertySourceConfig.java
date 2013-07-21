package org.kuali.student.deploy.spring;

import java.util.List;

import org.kuali.common.util.config.spring.SmartProjectPropertySourceConfig;
import org.kuali.student.deploy.config.MetaInfConstants;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SqlMetaInfPropertySourceConfig extends SmartProjectPropertySourceConfig {

	@Override
	protected List<String> getConfigIds() {
		return MetaInfConstants.METAINF_SQL_CONFIG_IDS;
	}

}
