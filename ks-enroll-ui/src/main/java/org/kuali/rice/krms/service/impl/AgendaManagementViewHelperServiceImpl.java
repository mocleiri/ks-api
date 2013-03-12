package org.kuali.rice.krms.service.impl;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.uif.component.Component;
import org.kuali.rice.krad.uif.container.Container;
import org.kuali.rice.krad.uif.container.PageGroup;
import org.kuali.rice.krad.uif.util.ComponentFactory;
import org.kuali.rice.krad.uif.view.View;
import org.kuali.rice.krms.api.KrmsConstants;
import org.kuali.rice.krms.api.repository.RuleManagementService;
import org.kuali.rice.krms.api.repository.agenda.AgendaDefinition;
import org.kuali.rice.krms.api.repository.agenda.AgendaItemDefinition;
import org.kuali.rice.krms.api.repository.agenda.AgendaTreeDefinition;
import org.kuali.rice.krms.api.repository.agenda.AgendaTreeEntryDefinitionContract;
import org.kuali.rice.krms.api.repository.agenda.AgendaTreeRuleEntry;
import org.kuali.rice.krms.api.repository.rule.RuleDefinition;
import org.kuali.rice.krms.api.repository.type.KrmsTypeDefinition;
import org.kuali.rice.krms.api.repository.type.KrmsTypeRepositoryService;
import org.kuali.rice.krms.api.repository.typerelation.TypeTypeRelation;
import org.kuali.rice.krms.builder.ComponentBuilder;
import org.kuali.rice.krms.dto.AgendaEditor;
import org.kuali.rice.krms.dto.TemplateInfo;
import org.kuali.rice.krms.service.AgendaManagementViewHelperService;
import org.kuali.rice.krms.service.TemplateRegistry;
import org.kuali.rice.krms.tree.RulePreviewTreeBuilder;
import org.kuali.rice.krms.tree.RuleViewTreeBuilder;
import org.kuali.rice.krms.util.AgendaBuilder;
import org.kuali.student.enrollment.class1.krms.dto.RuleEditor;
import org.kuali.student.enrollment.class1.krms.form.AgendaManagementForm;
import org.kuali.student.enrollment.class2.courseoffering.service.decorators.PermissionServiceConstants;
import org.kuali.student.enrollment.uif.service.impl.KSViewHelperServiceImpl;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AgendaManagementViewHelperServiceImpl extends KSViewHelperServiceImpl implements AgendaManagementViewHelperService {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(AgendaManagementViewHelperServiceImpl.class);

    private transient RuleManagementService ruleManagementService;
    private transient KrmsTypeRepositoryService krmsTypeRepositoryService;

    private RuleViewTreeBuilder viewTreeBuilder;
    private Map<String, List<String>> typeRelationsMap;  // TODO: Create AgendaTypeInfo and RuleTypeInfo objects instead to also capture description.

    @Override
    protected void addCustomContainerComponents(View view, Object model, Container container) {
        if ("KRMS-AgendaManageRequisites-Page".equals(container.getId())) {

            AgendaBuilder builder = new AgendaBuilder(view);
            builder.setTypeRelationsMap(this.getTypeRelationsMap());
            List<Component> components = new ArrayList<Component>();

            //Retrieve the current editing proposition if exists.
            List<AgendaEditor> agendas = ((AgendaManagementForm) model).getAgendas();
            for (AgendaEditor agenda : agendas) {
                components.add(builder.buildAgenda(agenda));
            }
            container.setItems(components);
        }
    }

    public List<AgendaEditor> getAgendaEditors() {
        List<AgendaEditor> agendas = new ArrayList<AgendaEditor>();

        //TODO: get all agendas linked to a course offering
        agendas.add(this.getAgendaEditor("10064"));
        agendas.add(this.getAgendaEditor("10002"));

        return agendas;
    }

    private AgendaEditor getAgendaEditor(String agendaId) {
        AgendaDefinition agenda = this.getRuleManagementService().getAgenda(agendaId);
        AgendaEditor agendaEditor = new AgendaEditor(agenda);

        AgendaTreeDefinition agendaTree = this.getRuleManagementService().getAgendaTree(agendaId);
        agendaEditor.setRuleEditors(getRuleEditorsFromTree(agendaTree.getEntries()));

        return agendaEditor;
    }

    private List<RuleEditor> getRuleEditorsFromTree(List<AgendaTreeEntryDefinitionContract> agendaTreeEntries) {

        List<RuleEditor> rules = new ArrayList<RuleEditor>();
        for (AgendaTreeEntryDefinitionContract treeEntry : agendaTreeEntries) {
            if (treeEntry instanceof AgendaTreeRuleEntry) {
                AgendaTreeRuleEntry treeRuleEntry = (AgendaTreeRuleEntry) treeEntry;
                AgendaItemDefinition agendaItem = this.getRuleManagementService().getAgendaItem(treeEntry.getAgendaItemId());

                if (agendaItem.getRuleId() != null) {
                    RuleDefinition rule = this.getRuleManagementService().getRule(treeRuleEntry.getRuleId());
                    RuleEditor ruleEditor = new RuleEditor(rule);
                    //TODO: Set refreshNl to true to use natural language.
                    ruleEditor.setPreviewTree(this.getViewTreeBuilder().buildTree(ruleEditor, false));
                    rules.add(ruleEditor);
                }

                if (treeRuleEntry.getIfTrue() != null) {
                    rules.addAll(getRuleEditorsFromTree(treeRuleEntry.getIfTrue().getEntries()));
                }
            }

            // TODO: Check for sub agendas, not required for course offering.
        }

        return rules;
    }

    /**
     * Setup a map with all the type information required to build an agenda management page.
     * @return
     */
    private Map<String, List<String>> getTypeRelationsMap(){
        if (typeRelationsMap == null){
            typeRelationsMap = new HashMap<String, List<String>>();

            // Get the super type.
            KrmsTypeDefinition requisitesType = this.getKrmsTypeRepositoryService().getTypeByName(PermissionServiceConstants.KS_SYS_NAMESPACE, "kuali.krms.agenda.type.course");

            // Get all agenda types linked to super type.
            List<TypeTypeRelation> agendaRelationships = this.getKrmsTypeRepositoryService().findTypeTypeRelationsByFromType(requisitesType.getId());
            for (TypeTypeRelation agendaRelationship : agendaRelationships){

                // Get all rule types for each agenda type
                List<TypeTypeRelation> ruleRelationships = this.getKrmsTypeRepositoryService().findTypeTypeRelationsByFromType(agendaRelationship.getToTypeId());
                List<String> ruleTypes = new ArrayList<String>();
                for (TypeTypeRelation ruleRelationship : ruleRelationships){

                    // Add rule types to list.
                    ruleTypes.add(ruleRelationship.getToTypeId());
                }
                typeRelationsMap.put(agendaRelationship.getToTypeId(), ruleTypes);

                // TODO: also set the instructional text and rule type description on the info objects.
            }
        }
        return typeRelationsMap;
    }

    private RuleViewTreeBuilder getViewTreeBuilder() {
        if (viewTreeBuilder == null) {
            viewTreeBuilder = new RulePreviewTreeBuilder();
            viewTreeBuilder.setRuleManagementService(this.getRuleManagementService());
        }
        return viewTreeBuilder;
    }

    public RuleManagementService getRuleManagementService() {
        if (ruleManagementService == null) {
            ruleManagementService = (RuleManagementService) GlobalResourceLoader.getService(new QName(KrmsConstants.Namespaces.KRMS_NAMESPACE_2_0, "ruleManagementService"));
        }
        return ruleManagementService;
    }

    public KrmsTypeRepositoryService getKrmsTypeRepositoryService() {
        if (krmsTypeRepositoryService == null) {
            krmsTypeRepositoryService = (KrmsTypeRepositoryService) GlobalResourceLoader.getService(new QName(KrmsConstants.Namespaces.KRMS_NAMESPACE_2_0, "krmsTypeRepositoryService"));
        }
        return krmsTypeRepositoryService;
    }
}
