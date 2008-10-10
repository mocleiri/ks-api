/*
 * Copyright 2007 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.rules.repository.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;

import org.kuali.student.poc.common.ws.exceptions.AlreadyExistsException;
import org.kuali.student.poc.common.ws.exceptions.InvalidParameterException;
import org.kuali.student.poc.common.ws.exceptions.MissingParameterException;
import org.kuali.student.poc.common.ws.exceptions.OperationFailedException;
import org.kuali.student.rules.repository.RuleEngineRepository;
import org.kuali.student.rules.repository.dto.RuleSetDTO;
import org.kuali.student.rules.repository.exceptions.CategoryExistsException;
import org.kuali.student.rules.repository.exceptions.RuleSetTranslatorException;
import org.kuali.student.rules.repository.exceptions.RuleEngineRepositoryException;
import org.kuali.student.rules.repository.exceptions.RuleExistsException;
import org.kuali.student.rules.repository.exceptions.RuleSetExistsException;
import org.kuali.student.rules.repository.rule.Rule;
import org.kuali.student.rules.repository.rule.RuleSet;
import org.kuali.student.rules.repository.service.RuleAdapter;
import org.kuali.student.rules.repository.service.RuleRepositoryService;
import org.kuali.student.rules.rulemanagement.dto.BusinessRuleContainerDTO;
import org.kuali.student.rules.rulemanagement.dto.BusinessRuleInfoDTO;
import org.kuali.student.rules.translators.RuleSetTranslator;
import org.kuali.student.rules.translators.drools.RuleSetTranslatorDroolsImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
/**
 * This is a convenience interface for the rules repository interface.
 * 
 * @author Kuali Student Team (len.kuali@googlegroups.com)
 */
@WebService(endpointInterface = "org.kuali.student.rules.repository.service.RuleRepositoryService", 
			serviceName = "RuleRepositoryService", 
			portName = "RuleRepositoryService", 
			targetNamespace = "http://student.kuali.org/wsdl/brms/RuleRepository")
@Transactional
public class RuleRepositoryServiceImpl implements RuleRepositoryService {
    /** SLF4J logging framework */
    final static Logger logger = LoggerFactory.getLogger(RuleSetTranslatorDroolsImpl.class);
    
	private final static RuleAdapter ruleAdapter = RuleAdapter.getInstance();
	
	/** Drools rule repository */
    private RuleEngineRepository ruleEngineRepository;
    
    private RuleSetTranslator ruleSetTranslator;

    public RuleRepositoryServiceImpl() { }

	/**
     * Sets the rule engine repository.
	 * 
	 * @param ruleEngineRepository Rule engine repository
	 */
	public void setRuleEngineRepository(final RuleEngineRepository ruleEngineRepository) {
		this.ruleEngineRepository = ruleEngineRepository;
	}

	/**
	 * Sets the rule set translator.
	 * 
	 * @param ruleSetTranslator
	 */
	public void setRuleSetTranslator(final RuleSetTranslator ruleSetTranslator) {
		this.ruleSetTranslator = ruleSetTranslator;
	}

	/**
     * Creates a new category in the repository.
     * 
     * @param path Category path
     * @param name Category name
     * @param description Category description
     * @return True if category successfully created, otherwise false
     * @throws OperationFailedException Thrown if creating category fails or category already exists
     * @throws MissingParameterException Thrown if parameters are null or empty
     * @throws IllegalArgumentException If path and/or name is invalid
     */
    public Boolean createCategory(final String path, final String name, final String description) 
        throws OperationFailedException, MissingParameterException, InvalidParameterException {
    	if (path == null || path.isEmpty()) {
    		throw new MissingParameterException("Path cannot be null or empty");
    	} else if (name == null || name.isEmpty()) {
    		throw new MissingParameterException("Name cannot be null or empty");
    	}
    	try {
			return this.ruleEngineRepository.createCategory(path, name, description);
		} catch(CategoryExistsException e) {
			throw new OperationFailedException(e.getMessage());
		} catch(RuleEngineRepositoryException e) {
			throw new OperationFailedException(e.getMessage());
		} catch(IllegalArgumentException e) {
			throw new InvalidParameterException(e.getMessage());
		}
    }

    /**
     * Removes a category.
     * 
     * @param categoryPath Category path
     * @throws OperationFailedException Thrown if removing category fails
     * @throws MissingParameterException Thrown if parameter is null or empty
     * @throws IllegalArgumentException If path is invalid
     */
    public void removeCategory(final String path) 
    	throws OperationFailedException, InvalidParameterException, MissingParameterException {
    	if (path == null || path.isEmpty()) {
    		throw new MissingParameterException("Path cannot be null or empty");
    	}

    	try {
			this.ruleEngineRepository.removeCategory(path);
		} catch (RuleEngineRepositoryException e) {
			throw new OperationFailedException(e.getMessage());
		} catch(IllegalArgumentException e) {
			throw new InvalidParameterException(e.getMessage());
		}
    }

    /**
     * Loads child categories from <code>path</code>.
     * 
     * @param categoryPath Category path
     * @return List of child category names
     * @throws OperationFailedException Thrown if fetching category fails
     * @throws MissingParameterException Thrown if parameter is null or empty
     * @throws IllegalArgumentException If path is invalid
     */
    public List<String> fetchCategories(final String path) 
    	throws OperationFailedException, MissingParameterException, InvalidParameterException {
    	if (path == null || path.isEmpty()) {
    		throw new MissingParameterException("Path cannot be null or empty");
    	}

    	try {
		    return this.ruleEngineRepository.loadChildCategories(path);
		} catch (RuleEngineRepositoryException e) {
			throw new OperationFailedException(e.getMessage());
		} catch(IllegalArgumentException e) {
			throw new InvalidParameterException(e.getMessage());
		}
	}
	
    /**
     * Creates, compiles and checks in a rule set into the repository.
     * 
     * @param ruleSet Rule set to create
     * @return New rule set
     * @throws RuleExistsException Thrown if a rule within the rule set already exists
     * @throws RuleSetExistsException Thrown if rule set already exists
     * @throws OperationFailedException Thrown if compiling a rule set fails
     * @throws InvalidParameterException Thrown if method parameters are invalid
     */
    public RuleSetDTO createRuleSet(final RuleSetDTO ruleSetDTO) 
		throws AlreadyExistsException, OperationFailedException, InvalidParameterException {
    	try {
	    	RuleSet ruleSet = ruleAdapter.getRuleSet(ruleSetDTO);
	        RuleSet newRuleSet = this.ruleEngineRepository.createRuleSet(ruleSet);
	        RuleSetDTO dto = ruleAdapter.getRuleSetDTO(newRuleSet);
	        return dto;
		} catch (RuleSetExistsException e) {
			throw new AlreadyExistsException(e.getMessage());
		} catch (RuleExistsException e) {
			throw new AlreadyExistsException(e.getMessage());
		} catch (RuleEngineRepositoryException e) {
			throw new OperationFailedException(e.getMessage());
		} catch(IllegalArgumentException e) {
			throw new InvalidParameterException(e.getMessage());
		}
    }

    /**
     * Deletes a rule set by uuid.
     * 
     * @param uuid Rule set uuid
     * @throws OperationFailedException Thrown if removing rule set fails
     * @throws InvalidParameterException Thrown if method parameters are invalid
     */
    public void removeRuleSet(final String uuid) 
    	throws OperationFailedException, InvalidParameterException {
    	try {
	    	this.ruleEngineRepository.removeRuleSet(uuid);
		} catch (RuleEngineRepositoryException e) {
			throw new OperationFailedException(e.getMessage());
		} catch(IllegalArgumentException e) {
			throw new InvalidParameterException(e.getMessage());
		}
    }

    /**
     * Checks that the <code>ruleSetName</code> matches the rule set name from 
     * the <code>ruleSetUUID</code>.
     * 
     * @param ruleSetUUID Rule set UUID
     * @param ruleSetName Rule set name
     * @throws InvalidParameterException Thrown if <code>ruleSetName</code>
     * does not match rule set name from the <code>ruleSetUUID</code>
     */
    private void checkRuleSetName(final String ruleSetUUID, final String ruleSetName)
    	throws InvalidParameterException {
    	RuleSet ruleSet = this.ruleEngineRepository.loadRuleSet(ruleSetUUID);
    	if(!ruleSetName.equals(ruleSet.getName())) {
    		throw new InvalidParameterException("ruleSetName '" + ruleSetName 
    				+ "' does not equal stored rule set name '" + ruleSet.getName() + "'");
    	}
    }

    /**
     * Deletes a rule set snapshot. 
     * 
     * @param ruleSetName Rule set name
     * @param snapshotName Snapshot name
     * @throws OperationFailedException Thrown if removing snapshot fails or any other errors occur
     * @throws InvalidParameterException Thrown if method parameters are invalid
     */
    public void removeRuleSetSnapshot(final String ruleSetUUID, final String ruleSetName, final String snapshotName) 
    	throws OperationFailedException, InvalidParameterException {
    	try {
    		checkRuleSetName(ruleSetUUID, ruleSetName);
	    	this.ruleEngineRepository.removeRuleSetSnapshot(ruleSetName, snapshotName);
		} catch (RuleEngineRepositoryException e) {
			throw new OperationFailedException(e.getMessage());
		} catch(IllegalArgumentException e) {
			throw new InvalidParameterException(e.getMessage());
		}
    }
    
    /**
     * Updates a rule set in the repository and returns a new rule set.
     * 
     * @param ruleSet A rule set to update
     * @return An updated rule set
     * @throws RuleEngineRepositoryException
     */
    /*public RuleSetDTO updateRuleSet(RuleSetDTO ruleSetDTO) {
    	RuleSet ruleSet = ruleAdapter.getRuleSet(ruleSetDTO);
        RuleSet updatedRuleSet = this.ruleEngineRepository.updateRuleSet(ruleSet);
        RuleSetDTO dto = ruleAdapter.getRuleSetDTO(updatedRuleSet);
        return dto;
    }*/

    /**
     * Checks in a rule set into the repository.
     * Checkin rule set will create a new version of the rule set.
     * Rule set version is incremented by 1.
     * 
     * @param uuid Rule set uuid
     * @param comment Checkin comments
     * @return New version number
     * @throws OperationFailedException Thrown if checkin rule set fails
     * @throws InvalidParameterException Thrown if method parameters are invalid
     */
    public long checkinRuleSet(final String uuid, final String comment)
    	throws OperationFailedException, InvalidParameterException {
    	try {
	        return this.ruleEngineRepository.checkinRuleSet(uuid, comment);
		} catch (RuleEngineRepositoryException e) {
			throw new OperationFailedException(e.getMessage());
		} catch(IllegalArgumentException e) {
			throw new InvalidParameterException(e.getMessage());
		}
    }

    /**
     * Loads a rule by uuid.
     * 
     * @param uuid Rule uuid
     * @return A rule
     * @throws OperationFailedException Thrown if loading child categories fails
     * @throws InvalidParameterException Thrown if method parameters are invalid
     */
    /*public RuleDTO fetchRule(final String uuid) {
    	throws OperationFailedException, InvalidParameterException {
    	Rule rule = this.ruleEngineRepository.loadRule(uuid);
    	RuleDTO dto = ruleAdapter.getRuleDTO(rule);
    	return dto;
    }*/
    
    /**
     * Loads a rule set (including all rules) by UUID from the repository.
     * 
     * @param uuid Rule set uuid
     * @return A rule set
     * @throws OperationFailedException Thrown if loading rule set fails
     * @throws InvalidParameterException Thrown if method parameters are invalid
     */
    public RuleSetDTO fetchRuleSet(final String ruleSetUUID) 
    	throws OperationFailedException, InvalidParameterException {
        try {
	    	RuleSet ruleSet = this.ruleEngineRepository.loadRuleSet(ruleSetUUID);
	        RuleSetDTO dto = ruleAdapter.getRuleSetDTO(ruleSet);
	        return dto;
        } catch(RuleEngineRepositoryException e) {
        	throw new OperationFailedException(e.getMessage());
		} catch(IllegalArgumentException e) {
			throw new InvalidParameterException(e.getMessage());
		}
    }

    /**
     * Loads a list of rule sets by tag name.
     * 
     * @param category Category name
     * @return A list of rule sets
     * @throws OperationFailedException Thrown if loading rule set list fails
     */
    public List<RuleSetDTO> fetchRuleSetsByCategory(String category)
    	throws OperationFailedException {
        try {
	    	List<RuleSet> list = this.ruleEngineRepository.loadRuleSetsByCategory(category);
	        List<RuleSetDTO> dtoList = new ArrayList<RuleSetDTO>(list.size());
	    	for(RuleSet ruleSet : list) {
		    	RuleSetDTO dto = ruleAdapter.getRuleSetDTO(ruleSet);
		    	dtoList.add(dto);
	        }
	        return dtoList;
        } catch(RuleEngineRepositoryException e) {
        	throw new OperationFailedException(e.getMessage());
		}
    }
     
    /**
     * Loads a compiled rule set from the repository.
     * 
     * @param ruleSetUUID Rule set UUID
     * @return A compiled rule set as a byte array (e.g. <code>org.drools.rule.Package</code>)
     * @throws OperationFailedException Thrown if compiling a rule set fails
     * @throws InvalidParameterException Thrown if method parameters are invalid
     */
/*    public byte[] fetchCompiledRuleSet(final String ruleSetUUID) 
    	throws OperationFailedException, InvalidParameterException {
        try {
	        return this.ruleEngineRepository.loadCompiledRuleSetAsBytes(ruleSetUUID);
        } catch(RuleEngineRepositoryException e) {
        	throw new OperationFailedException(e.getMessage());
		} catch(IllegalArgumentException e) {
			throw new InvalidParameterException(e.getMessage());
		}
    }
*/
    /**
     * Creates a new rule set snapshot for deployment and stores it in the repository.
     * 
     * @param ruleSetName Rule set name
     * @param snapshotName Snapshot name
     * @param comment Comments for creating the snapshot
     * @return A new rule set which contains a new UUID
     * @throws OperationFailedException Thrown if rule set fails to compile or any other errors occur
     * @throws InvalidParameterException Thrown if method parameters are invalid
     */
    public RuleSetDTO createRuleSetSnapshot(
    		final String ruleSetUUID, 
    		final String ruleSetName, 
    		final String snapshotName, 
    		final String comment) 
    	throws OperationFailedException, InvalidParameterException {
        try {
    		checkRuleSetName(ruleSetUUID, ruleSetName);
        	RuleSet ruleSetSnapshot = this.ruleEngineRepository.createRuleSetSnapshot(ruleSetName, snapshotName, comment);
        	return ruleAdapter.getRuleSetDTO(ruleSetSnapshot);
        } catch(RuleEngineRepositoryException e) {
        	throw new OperationFailedException(e.getMessage());
		} catch(IllegalArgumentException e) {
			throw new InvalidParameterException(e.getMessage());
		}
    }

    /**
     * Rebuilds (recompiles) an existing rule set snapshot and stores it in the repository.
     * 
     * @param ruleSetName Rule set name
     * @param snapshotName Snapshot name
     * @param comment Comments for creating the snapshot
     * @return A new rule set which contains a new UUID
     * @throws OperationFailedException Thrown if rule set fails to compile or any other errors occur
     * @throws InvalidParameterException Thrown if method parameters are invalid
     */
    public RuleSetDTO rebuildRuleSetSnapshot(
    		final String ruleSetUUID, 
    		final String ruleSetName, 
    		final String snapshotName, 
    		final String comment) 
    	throws OperationFailedException, InvalidParameterException {
        try {
        	RuleSet ruleSet = this.ruleEngineRepository.loadRuleSet(ruleSetUUID);
        	if(!ruleSetName.equals(ruleSet.getName())) {
        		throw new InvalidParameterException("ruleSetName '" + ruleSetName 
        				+ "' does not equal stored rule set name '" + ruleSet.getName() + "'");
        	}
	        RuleSet ruleSetSnapshot = this.ruleEngineRepository.rebuildRuleSetSnapshot(ruleSetName, snapshotName, comment);
        	return ruleAdapter.getRuleSetDTO(ruleSetSnapshot);
        } catch(RuleEngineRepositoryException e) {
        	throw new OperationFailedException(e.getMessage());
		} catch(IllegalArgumentException e) {
			throw new InvalidParameterException(e.getMessage());
		}
    }

    /**
     * Loads a compiled rule set snapshot from the repository.
     * 
     * @param ruleSetName Rule set name
     * @param snapshotName Snapshot name
     * @return Compiled rule set as a byte array (e.g. <code>org.drools.rule.Package</code>)
     * @throws OperationFailedException Thrown if loading a snapshots fails
     * @throws InvalidParameterException Thrown if method parameters are invalid
     */
/*    public byte[] fetchCompiledRuleSetSnapshot(final String ruleSetName, final String snapshotName) 
    	throws OperationFailedException, InvalidParameterException {
        try {
	        return this.ruleEngineRepository.loadCompiledRuleSetSnapshotAsBytes(ruleSetName, snapshotName);
        } catch(RuleEngineRepositoryException e) {
        	throw new OperationFailedException(e.getMessage());
		} catch(IllegalArgumentException e) {
			throw new InvalidParameterException(e.getMessage());
		}
    }
*/
    /**
     * Loads a rule set snapshot.
     * 
     * @param ruleSetName Rule set name
     * @param snapshotName Rule set's snapshot name
     * @return A rule set snapshot
     * @throws OperationFailedException Thrown if loading a snapshots fails
     * @throws InvalidParameterException Thrown if method parameters are invalid
     */
    public RuleSetDTO fetchRuleSetSnapshot(final String ruleSetName, final String snapshotName) 
    	throws OperationFailedException, InvalidParameterException {
        try {
	        RuleSet ruleSet = this.ruleEngineRepository.loadRuleSetSnapshot(ruleSetName, snapshotName);
	        RuleSetDTO dto = ruleAdapter.getRuleSetDTO(ruleSet);
	        return dto;
        } catch(RuleEngineRepositoryException e) {
        	throw new OperationFailedException(e.getMessage());
		} catch(IllegalArgumentException e) {
			throw new InvalidParameterException(e.getMessage());
		}
    }

    /**
     * Loads a list of rule set snapshots by tag name.
     * 
     * @param category Category name
     * @return A list of rule sets
     */
    public List<RuleSetDTO> fetchRuleSetSnapshotsByCategory(String category)
		throws OperationFailedException {
        try {
	        List<RuleSet> list = this.ruleEngineRepository.loadRuleSetSnapshotsByCategory(category);
	        List<RuleSetDTO> dtoList = new ArrayList<RuleSetDTO>(list.size());
	    	for(RuleSet ruleSet : list) {
		    	RuleSetDTO dto = ruleAdapter.getRuleSetDTO(ruleSet);
		    	dtoList.add(dto);
	        }
	        return dtoList;
        } catch(RuleEngineRepositoryException e) {
        	throw new OperationFailedException(e.getMessage());
		}
    }

    /**
     * Creates a new status if it doesn't already exists.
     * 
     * @param name Status name
     * @return New status uuid
     * @throws OperationFailedException Thrown if creating status fails
     * @throws InvalidParameterException Thrown if method parameters are invalid
     */
    public String createState(final String name)
    	throws OperationFailedException, InvalidParameterException {
        try {
	    	return this.ruleEngineRepository.createStatus(name);
        } catch(RuleEngineRepositoryException e) {
        	throw new OperationFailedException(e.getMessage());
		} catch(IllegalArgumentException e) {
			throw new InvalidParameterException(e.getMessage());
		}
    }
    
    /**
     * Loads all states.
     * 
     * @return Array of all states (statuses)
     * @throws OperationFailedException Thrown if loading states fails
     */
    public String[] fetchStates()
    	throws OperationFailedException {
        try {
	    	return this.ruleEngineRepository.loadStates();
        } catch(RuleEngineRepositoryException e) {
        	throw new OperationFailedException(e.getMessage());
        }
    }

    /**
     * Removes a status from the repository.
     * 
     * @param uuid Status name
     * @throws OperationFailedException Thrown if removing status fails
     * @throws InvalidParameterException Thrown if method parameters are invalid
     */
    public void removeState(final String name)
    	throws OperationFailedException, InvalidParameterException {
        try {
	    	this.ruleEngineRepository.removeStatus(name);
        } catch(RuleEngineRepositoryException e) {
        	throw new OperationFailedException(e.getMessage());
		} catch(IllegalArgumentException e) {
			throw new InvalidParameterException(e.getMessage());
		}
    }

    /**
     * Changes a rule set status by uuid.
     * 
     * @param uuid Rule set uuid
     * @param newState New rule set status
     * @throws OperationFailedException Thrown if changing rule set status fails
     * @throws InvalidParameterException Thrown if method parameters are invalid
     */
    public void changeRuleSetState(final String ruleSetUUID, final String newState)
    	throws OperationFailedException, InvalidParameterException {
        try {
	    	this.ruleEngineRepository.changeRuleSetStatus(ruleSetUUID, newState);
        } catch(RuleEngineRepositoryException e) {
        	throw new OperationFailedException(e.getMessage());
		} catch(IllegalArgumentException e) {
			throw new InvalidParameterException(e.getMessage());
		}
    }

    /**
     * Log rule set information
     * 
     * @param ruleSet Ruleset to log
     * @param msg A logging message
     */
    private void log(RuleSet ruleSet, String msg) {
		if (logger.isInfoEnabled()) {
			StringBuilder sb = new StringBuilder();
			sb.append("\n"+msg);
			sb.append("\nRuleSet.uuid:                "+ruleSet.getUUID());
			sb.append("\nRuleSet.name:                "+ruleSet.getName());
			sb.append("\nRuleSet.status:              "+ruleSet.getStatus());
			sb.append("\nRuleSet.snapshotName:        "+ruleSet.getSnapshotName());
			sb.append("\nRuleSet.version:             "+ruleSet.getVersionNumber());
			sb.append("\nRuleSet.versionSnapshotUUID: "+ruleSet.getVersionSnapshotUUID());
			logger.info(sb.toString());
		}
		if (logger.isDebugEnabled()) {
			logger.info(ruleSet.getContent());
		}
    }

    /**
     * Updates ruleset with new rules from <code>ruleSet1</code> and 
     * returns the updated ruleset.
     *  
     * @param ruleSet1 Ruleset of new rules
     * @return Updated ruleset
     * @throws RuleEngineRepositoryException
     */
    private RuleSet updateRuleSet(RuleSet ruleSet1) 
    	throws RuleEngineRepositoryException {
		//RuleSet ruleSet2 = this.ruleEngineRepository.loadRuleSetByName(ruleSet1.getName());
		RuleSet ruleSet2 = this.ruleEngineRepository.loadRuleSet(ruleSet1.getUUID());
		// Add headers
		ruleSet2.clearHeaders();
		for(String header : ruleSet1.getHeaderList()) {
			ruleSet2.addHeader(header);
		}
		// Add rules
		ruleSet2.clearRules();
		for(Rule rule : ruleSet1.getRules()) {
			ruleSet2.addRule(rule);
		}

		log(ruleSet2, "***** Update RuleSet *****");
		
		// Update rule set
		return this.ruleEngineRepository.updateRuleSet(ruleSet2);
    }

    /**
     * Generates and creates or updates a rule set (rule engine specific source code) 
     * from a <code>BusinessRuleContainerDTO</code>.
     *  
     * @param businessRuleContainer A container of business rules
     * @return A rule set
	 * @throws RuleSetTranslatorException Thrown if translating a rule set fails
     * @throws OperationFailedException Thrown if generating ruleset fails
     * @throws InvalidParameterException Thrown if method parameters are invalid
     */
    public RuleSetDTO generateRuleSet(final BusinessRuleContainerDTO container) 
    	throws OperationFailedException, MissingParameterException, InvalidParameterException {
		if (container == null) {
			throw new MissingParameterException("businessRuleContainer is null");
		} else if (container.getBusinessRules() == null || container.getBusinessRules().isEmpty()) {
			throw new InvalidParameterException("Container contains no business rules");
		}
		
    	RuleSet ruleSet1 = null;
    	try {
    		BusinessRuleInfoDTO  businessRule = container.getBusinessRules().get(0);
    		ruleSet1 = this.ruleSetTranslator.translate(businessRule);
		} catch(IllegalArgumentException e) {
			throw new InvalidParameterException(e.getMessage());
        } catch(RuleSetTranslatorException e) {
        	throw new OperationFailedException(e.getMessage());
        } catch(RuleEngineRepositoryException e) {
        	throw new OperationFailedException(e.getMessage());
        }
        
		boolean ruleSetExists = false;
		if (ruleSet1.getUUID() != null) {
			ruleSetExists = this.ruleEngineRepository.containsRuleSet(ruleSet1.getUUID());
		}

		try {
    		if (ruleSetExists) {
    			// Update rule set
    			ruleSet1 = updateRuleSet(ruleSet1);
    		} else {
				log(ruleSet1, "***** Create RuleSet *****");
    			// Create new rule set
        		ruleSet1 = this.ruleEngineRepository.createRuleSet(ruleSet1);
    		}
		} catch(IllegalArgumentException e) {
			throw new InvalidParameterException(e.getMessage());
		} catch(RuleSetTranslatorException e) {
        	throw new OperationFailedException("Ruleset translation error: " + e.getMessage());
        } catch(RuleSetExistsException e) {
        	throw new OperationFailedException(e.getMessage());
        } catch(RuleExistsException e) {
        	throw new OperationFailedException(e.getMessage());
        } catch(RuleEngineRepositoryException e) {
        	throw new OperationFailedException(e.getMessage());
		}

		RuleSetDTO dto = ruleAdapter.getRuleSetDTO(ruleSet1);
    	return dto;
    }

}
