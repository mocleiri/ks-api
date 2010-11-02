package org.kuali.student.lum.program.client;

import com.google.gwt.event.shared.HandlerManager;
import org.kuali.student.common.ui.client.application.ViewContext;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.lum.program.client.bacc.edit.BaccEditController;
import org.kuali.student.lum.program.client.bacc.view.BaccViewController;
import org.kuali.student.lum.program.client.core.edit.CoreEditController;
import org.kuali.student.lum.program.client.core.view.CoreViewController;
import org.kuali.student.lum.program.client.events.MajorViewEvent;
import org.kuali.student.lum.program.client.major.edit.MajorEditController;
import org.kuali.student.lum.program.client.major.view.MajorViewController;
import org.kuali.student.lum.program.client.variation.edit.VariationEditController;
import org.kuali.student.lum.program.client.variation.view.VariationViewController;

/**
 * @author Igor
 */
public class ProgramManager {

    private MajorViewController majorViewController;

    private MajorEditController majorEditController;

    private VariationViewController variationViewController;

    private VariationEditController variationEditController;

    private CoreViewController coreViewController;

    private CoreEditController coreEditController;

    private BaccViewController baccViewController;

    private BaccEditController baccEditController;

    protected DataModel programModel;

    private ViewContext viewContext = new ViewContext();

    private static HandlerManager eventBus = new HandlerManager(null);

    public ProgramManager() {
        programModel = new DataModel();
    }

    public MajorViewController getProgramViewController() {
        programModel.resetRoot();
        getMajorViewController();
        eventBus.fireEvent(new MajorViewEvent());
        return majorViewController;
    }


    public VariationViewController getVariationViewController() {
        String name = getMajorViewController().getProgramName();
        DataModel variationModel = new DataModel();
        variationModel.setDefinition(programModel.getDefinition());
        variationModel.setRoot(ProgramRegistry.getData());
        variationViewController = new VariationViewController(name, variationModel, viewContext, eventBus, majorViewController);
        return variationViewController;
    }

    public VariationEditController getVariationEditController() {
        String name = getMajorEditController().getProgramName();
        DataModel variationModel = new DataModel();
        variationModel.setDefinition(programModel.getDefinition());
        variationModel.setRoot(ProgramRegistry.getData());
        variationEditController = new VariationEditController(name, variationModel, viewContext, eventBus, majorEditController);
        return variationEditController;
    }

    public MajorEditController getProgramEditController() {
        programModel.resetRoot();
        return getMajorEditController();
    }

    public static HandlerManager getEventBus() {
        return eventBus;
    }

    private MajorEditController getMajorEditController() {
        if (majorEditController == null) {
            majorEditController = new MajorEditController("Programs", programModel, viewContext, eventBus);
        }
        return majorEditController;
    }

    private MajorViewController getMajorViewController() {
        if (majorViewController == null) {
            majorViewController = new MajorViewController("Programs", programModel, viewContext, eventBus);
        }
        return majorViewController;
    }

    public CoreViewController getCoreViewController() {
        programModel.resetRoot();
        if (coreViewController == null) {
            coreViewController = new CoreViewController("CoreProgram", programModel, viewContext, eventBus);
        }
        return coreViewController;
    }

    public CoreEditController getCoreEditController() {
        programModel.resetRoot();
        if (coreEditController == null) {
            coreEditController = new CoreEditController("CoreProgram", programModel, viewContext, eventBus);
        }
        return coreEditController;
    }

    public BaccViewController getBaccViewController() {
        programModel.resetRoot();
        if (baccViewController == null) {
            baccViewController = new BaccViewController("BaccProgram", programModel, viewContext, eventBus);
        }
        return baccViewController;
    }

    public BaccEditController getBaccEditController() {
        programModel.resetRoot();
        if (baccEditController == null) {
            baccEditController = new BaccEditController("BaccProgram", programModel, viewContext, eventBus);
        }
        return baccEditController;
    }
}
