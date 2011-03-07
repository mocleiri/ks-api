package org.kuali.student.krms.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.kuali.rice.krms.engine.Agenda;
import org.kuali.rice.krms.engine.Asset;
import org.kuali.rice.krms.engine.AssetResolver;
import org.kuali.rice.krms.engine.ComparisonOperator;
import org.kuali.rice.krms.engine.Context;
import org.kuali.rice.krms.engine.ContextProvider;
import org.kuali.rice.krms.engine.EngineResults;
import org.kuali.rice.krms.engine.ExecutionOptions;
import org.kuali.rice.krms.engine.LogicalOperator;
import org.kuali.rice.krms.engine.Proposition;
import org.kuali.rice.krms.engine.Rule;
import org.kuali.rice.krms.engine.SelectionCriteria;
import org.kuali.rice.krms.engine.impl.AgendaTree;
import org.kuali.rice.krms.engine.impl.BasicAgenda;
import org.kuali.rice.krms.engine.impl.BasicContext;
import org.kuali.rice.krms.engine.impl.BasicRule;
import org.kuali.rice.krms.engine.impl.ComparableTermBasedProposition;
import org.kuali.rice.krms.engine.impl.CompoundProposition;
import org.kuali.rice.krms.engine.impl.ProviderBasedEngine;
import org.kuali.rice.krms.engine.impl.ResultLogger;
import org.kuali.student.core.statement.service.StatementService;
import org.kuali.student.lum.course.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;

public class KRMSTest {
	private static final ResultLogger LOG = ResultLogger.getInstance();
	
    @Autowired
    CourseService courseService;
    @Autowired
    StatementService statementService;
    
	@Test
	public void integrationTest() {

		// build a simple rule
//		ComparableTerm term1 = new SimpleComparableTerm<Integer>(Integer.valueOf(100));
//		ResolvableComparableTerm<Integer> resolvableComparableTerm = new ResolvableComparableTerm<Integer>(testResolver.getOutput());
		
		Proposition prop1 = new ComparableTermBasedProposition(ComparisonOperator.GREATER_THAN, totalCostAsset, Integer.valueOf(1));
		Proposition prop2 = new ComparableTermBasedProposition(ComparisonOperator.LESS_THAN, totalCostAsset, Integer.valueOf(1000));
		Proposition prop3 = new ComparableTermBasedProposition(ComparisonOperator.GREATER_THAN, totalCostAsset, Integer.valueOf(1000));
		CompoundProposition compoundProp1 = new CompoundProposition(LogicalOperator.AND, Arrays.asList(prop1, prop2, prop3));
		
		Rule rule = new BasicRule("InBetween",compoundProp1, null);
		
		AgendaTree agendaTree = new AgendaTree(Arrays.asList(rule), null, null, null); 
		Agenda agenda = new BasicAgenda("test", new HashMap<String, String>(), agendaTree);
		
		Map<String, String> contextQualifiers = new HashMap<String, String>();
		contextQualifiers.put("docTypeName", "Proposal");
		
		List<AssetResolver<?>> testResolvers = new ArrayList<AssetResolver<?>>();
		testResolvers.add(testResolver);
		
		Context context = new BasicContext(contextQualifiers, Arrays.asList(agenda), testResolvers);
		ContextProvider contextProvider = new ManualContextProvider(context);
		
		SelectionCriteria selectionCriteria = SelectionCriteria.createCriteria("test", contextQualifiers, Collections.EMPTY_MAP);
		
		ProviderBasedEngine engine = new ProviderBasedEngine();
		engine.setContextProvider(contextProvider);
		
		// Set execution options to log execution
		HashMap<String, String> xOptions = new HashMap<String, String>();
		xOptions.put(ExecutionOptions.LOG_EXECUTION.toString(), Boolean.toString(true));
		
		LOG.init();
		EngineResults results = engine.execute(selectionCriteria, new HashMap<Asset, Object>(), xOptions);
		
	}
	
	private static final Asset totalCostAsset = new Asset("totalCost","Integer");
	
	private static final AssetResolver<Integer> testResolver = new AssetResolver<Integer>(){
		
		@Override
		public int getCost() { return 1; }
		
		@Override
		public Asset getOutput() { return totalCostAsset; }
		
		@Override
		public Set<Asset> getPrerequisites() { return Collections.emptySet(); }
		
		@Override
		public Integer resolve(Map<Asset, Object> resolvedPrereqs) {
			return 5;
		}
	};

}
