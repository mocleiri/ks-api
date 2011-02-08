package org.kuali.student.lum.lu.ui.course.client.controllers;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.user.client.Window;
import org.kuali.student.common.ui.client.configurable.mvc.LayoutController;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.View;
import org.kuali.student.common.ui.client.util.WindowTitleUtils;
import org.kuali.student.common.ui.client.widgets.field.layout.element.SpanPanel;
import org.kuali.student.lum.lu.ui.course.client.views.CategoryManagementView;
import org.kuali.student.lum.lu.ui.course.client.views.CurriculumHomeView;
import org.kuali.student.lum.lu.ui.main.client.controllers.ApplicationController;
import org.kuali.student.lum.lu.ui.tools.client.configuration.CatalogBrowserController;
import org.kuali.student.lum.lu.ui.tools.client.configuration.CluSetsManagementController;
import org.kuali.student.lum.program.client.ProgramRegistry;
import org.kuali.student.lum.program.client.core.CoreManager;
import org.kuali.student.lum.program.client.credential.CredentialManager;
import org.kuali.student.lum.program.client.major.MajorManager;

/**
 * Curriculum home controller which controls the main LayoutController views of the lum application.  The
 * default view of this controller is the Curriculum Home Landing page.  The following views are views within
 * this controller's scope:<br>
 * 		COURSE_PROPOSAL<br>
        VIEW_COURSE<br>
        PROGRAM_VIEW<br>
        PROGRAM_EDIT<br>
        PROGRAM_CREATE<br>
        PROGRAM_VERSIONS<br>
        CLU_SETS<br>
        VARIATION_VIEW<br>
        VARIATION_EDIT<br>
        COURSE_CATALOG<br>
        LO_CATEGORIES<br>
        BACC_PROGRAM_VIEW<br>
        BACC_PROGRAM_EDIT<br>
        BACC_PROGRAM_VERSIONS<br>
        CORE_PROGRAM_VIEW<br>
        CORE_PROGRAM_EDIT<br>
        CORE_PROGRAM_VERSIONS<br>
 * These views can be accessed through links and searches provided by the CurriculumHomeView (the default view).
 * 
 * @author Kuali Student Team
 * @see CurriculumHomeView
 */
public class CurriculumHomeController extends LayoutController {

    private CurriculumHomeView home;
    private final SpanPanel panel = new SpanPanel();

    private CourseProposalController courseProposalController;
    private LayoutController viewCourseController;
    private LayoutController manageCluSetsController;
    private LayoutController browseCatalogController;
    private MajorManager majorManager = new MajorManager();
    private CredentialManager credentialManager = new CredentialManager();
    private CoreManager coreManager = new CoreManager();

    private abstract class RunAsyncGetView implements RunAsyncCallback {
        public void onFailure(Throwable reason) {
            Window.alert("Download failed.  Please try again.");
        }
    }

    public enum LUMViews {
        DEFAULT,
        COURSE_PROPOSAL,
        VIEW_COURSE,
        PROGRAM_VIEW,
        PROGRAM_EDIT,
        PROGRAM_SPEC_EDIT,
        PROGRAM_CREATE,
        PROGRAM_VERSIONS,
        CLU_SETS,
        VARIATION_VIEW,
        VARIATION_EDIT,
        COURSE_CATALOG,
        LO_CATEGORIES,
        BACC_PROGRAM_VIEW,
        BACC_PROGRAM_EDIT,
        BACC_PROGRAM_VERSIONS,
        CORE_PROGRAM_VIEW,
        CORE_PROGRAM_EDIT,
        CORE_PROGRAM_VERSIONS
    }

    public CurriculumHomeController(Controller controller, String name, Enum<?> viewType) {
        super(CurriculumHomeController.class.getName());
        super.setController(controller);
        super.setName(name);
        super.setViewEnum(viewType);
        this.setDefaultView(LUMViews.DEFAULT);
        this.initWidget(panel);
        setupDefaultView();
    }

    private void setupDefaultView() {
        home = new CurriculumHomeView(this, LUMViews.DEFAULT);
    }

    @Override
    public <V extends Enum<?>> void getView(V viewType, final Callback<View> callback) {
        //this is done so the views can have delayed loading

        switch ((LUMViews) viewType) {
            case DEFAULT:
                callback.exec(home);
                break;
            case COURSE_PROPOSAL:
                GWT.runAsync(new RunAsyncGetView() {
                    @Override
                    public void onSuccess() {
                        callback.exec(getCourseProposalController());
                    }
                });
                break;
            case VIEW_COURSE:
                GWT.runAsync(new RunAsyncGetView() {
                    @Override
                    public void onSuccess() {
                        callback.exec(getViewCourseController());
                    }
                });
                break;
            case PROGRAM_VIEW:
                GWT.runAsync(new RunAsyncGetView() {
                    @Override
                    public void onSuccess() {
                        if (ProgramRegistry.isCreateNew()) {
                            ProgramRegistry.setCreateNew(false);
                            majorManager = new MajorManager();
                        }
                        callback.exec(majorManager.getProgramViewController());
                    }
                });
                break;
            case PROGRAM_EDIT:
                GWT.runAsync(new RunAsyncGetView() {
                    @Override
                    public void onSuccess() {
                        if (ProgramRegistry.isCreateNew()) {
                            ProgramRegistry.setCreateNew(false);
                            majorManager = new MajorManager();
                        }
                        callback.exec(majorManager.getProgramEditController());
                    }
                });
                break;
            case PROGRAM_SPEC_EDIT:
                GWT.runAsync(new RunAsyncGetView() {
                    @Override
                    public void onSuccess() {
                        if (ProgramRegistry.isCreateNew()) {
                            ProgramRegistry.setCreateNew(false);
                            majorManager = new MajorManager();
                        }
                        callback.exec(majorManager.getProgramSpecEditController());
                    }
                });
                break;                
            case PROGRAM_CREATE:
                GWT.runAsync(new RunAsyncGetView() {
                    @Override
                    public void onSuccess() {
                        majorManager = new MajorManager();
                        callback.exec(majorManager.getProgramEditController());
                    }
                });
                break;
            case PROGRAM_VERSIONS:
                GWT.runAsync(new RunAsyncGetView() {
                    @Override
                    public void onSuccess() {
                        callback.exec(majorManager.getProgramVersionsController());
                    }
                });
                break;
            case CLU_SETS:
                GWT.runAsync(new RunAsyncGetView() {
                    @Override
                    public void onSuccess() {
                        callback.exec(getCluSetsController());
                    }
                });
                break;
            case COURSE_CATALOG:
                GWT.runAsync(new RunAsyncGetView() {
                    @Override
                    public void onSuccess() {
                        callback.exec(getBrowseCatalogController());
                    }
                });
                break;
            case VARIATION_VIEW:
                GWT.runAsync(new RunAsyncGetView() {
                    @Override
                    public void onSuccess() {
                        callback.exec(majorManager.getVariationViewController());
                    }
                });
                break;
            case VARIATION_EDIT:
                GWT.runAsync(new RunAsyncGetView() {
                    @Override
                    public void onSuccess() {
                        callback.exec(majorManager.getVariationEditController());
                    }
                });
                break;
            case CORE_PROGRAM_VIEW:
                GWT.runAsync(new RunAsyncGetView() {
                    @Override
                    public void onSuccess() {
                        if (ProgramRegistry.isCreateNew()) {
                            ProgramRegistry.setCreateNew(false);
                            coreManager = new CoreManager();
                        }
                        callback.exec(coreManager.getViewController());
                    }
                });
                break;
            case CORE_PROGRAM_EDIT:
                GWT.runAsync(new RunAsyncGetView() {
                    @Override
                    public void onSuccess() {
                        callback.exec(coreManager.getEditController());
                    }
                });
                break;
            case CORE_PROGRAM_VERSIONS:
                GWT.runAsync(new RunAsyncGetView() {
                    @Override
                    public void onSuccess() {
                        callback.exec(coreManager.getProgramVersionsController());
                    }
                });
                break;
            case BACC_PROGRAM_VIEW:
                GWT.runAsync(new RunAsyncGetView() {
                    @Override
                    public void onSuccess() {
                        if (ProgramRegistry.isCreateNew()) {
                            ProgramRegistry.setCreateNew(false);
                            credentialManager = new CredentialManager();
                        }
                        callback.exec(credentialManager.getBaccViewController());
                    }
                });
                break;
            case BACC_PROGRAM_EDIT:
                GWT.runAsync(new RunAsyncGetView() {
                    @Override
                    public void onSuccess() {
                        callback.exec(credentialManager.getBaccEditController());
                    }
                });
                break;
            case BACC_PROGRAM_VERSIONS:
                GWT.runAsync(new RunAsyncGetView() {
                    @Override
                    public void onSuccess() {
                        callback.exec(credentialManager.getProgramVersionsController());
                    }
                });
                break;
            case LO_CATEGORIES:
                GWT.runAsync(new RunAsyncGetView() {
                    @Override
                    public void onSuccess() {
                        callback.exec(getCategoryManagementController());
                    }
                });
                break;
            default:
                callback.exec(home);
        }
    }


    private View getCategoryManagementController() {
        return new CategoryManagementView(this, "Learning Objective Categories", LUMViews.LO_CATEGORIES);
    }

    private CourseProposalController getCourseProposalController() {
        courseProposalController = new CourseProposalController();
        return courseProposalController;
    }

    private LayoutController getViewCourseController() {
        if (viewCourseController == null) {
            viewCourseController = new ViewCourseParentController();
        }
        return this.viewCourseController;
    }

    private LayoutController getCluSetsController() {
        manageCluSetsController = new CluSetsManagementController();
        return manageCluSetsController;
    }

    private LayoutController getBrowseCatalogController() {
        browseCatalogController = new CatalogBrowserController(this);
        return browseCatalogController;
    }

    @Override
    protected void hideView(View view) {
        ApplicationController.getApplicationViewContainer().clear();
    }

    @Override
    protected void renderView(View view) {
        ApplicationController.getApplicationViewContainer().add(view.asWidget());
    }

    @Override
    public Enum<?> getViewEnumValue(String enumValue) {
        return LUMViews.valueOf(enumValue);
    }

    @Override
    public void updateModel() {
        // No model needed here
    }

    public <V extends Enum<?>> void showView(V viewType, Callback<Boolean> onReadyCallback) {
        if (viewType == LUMViews.DEFAULT) {
            WindowTitleUtils.setContextTitle(name);
        }
        super.showView(viewType, onReadyCallback);
    }
}
