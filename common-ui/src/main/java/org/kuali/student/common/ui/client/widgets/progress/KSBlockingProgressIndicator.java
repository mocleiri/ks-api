package org.kuali.student.common.ui.client.widgets.progress;

import java.util.LinkedList;

import org.kuali.student.common.ui.client.widgets.KSImage;
import org.kuali.student.common.ui.client.widgets.KSModalPopupPanel;
import org.kuali.student.common.ui.client.widgets.KSStyles;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * @author Bsmith
 *
 */
public class KSBlockingProgressIndicator{
	private static LinkedList<BlockingTask> tasks = new LinkedList<BlockingTask>();
	
	private static final VerticalPanel mainPanel = new VerticalPanel();
	private static final VerticalPanel listPanel = new VerticalPanel();
	
	private static KSModalPopupPanel popupIndicator = new KSModalPopupPanel();
	
	private static boolean initialized = false;
	
	public static void initialize(){
		
		
		mainPanel.add(listPanel);
		
		popupIndicator.addHeader("Processing...");
		
		popupIndicator.add(mainPanel);
		setupDefaultStyle();
		initialized = true;
	}
	
	public static void addTask(BlockingTask task) {
		tasks.add(task);
		updateIndicator();
	}
	
	public static void removeTask(BlockingTask task) {
		tasks.remove(task);
		if (tasks.isEmpty()) {
			hideIndicator();
		} else {
			updateIndicator();
		}
		
	}

	private static void updateIndicator() {
		
		showIndicator();
		listPanel.clear();
		KSImage kSImage = new KSImage("images/twiddler3.gif");
		for(BlockingTask task: tasks){
			HorizontalPanel taskPanel = new HorizontalPanel();
			taskPanel.add(new Label(task.getDescription()));
			taskPanel.add(kSImage);
			taskPanel.addStyleName(KSStyles.KS_BLOCKING_TASK_ITEM);
			listPanel.add(taskPanel);
		}
		
	}
	
	private static void showIndicator(){
		if(!initialized){
			initialize();
		}
		popupIndicator.show();
	}
	
	private static void hideIndicator() {
		popupIndicator.hide();
	}
	
	private static  void setupDefaultStyle(){
		popupIndicator.addStyleName(KSStyles.KS_BLOCKING_TASK_WINDOW);
		listPanel.addStyleName(KSStyles.KS_BLOCKING_TASK_LIST);
		mainPanel.addStyleName(KSStyles.KS_BLOCKING_TASK_MAIN);
		mainPanel.addStyleName(KSStyles.KS_MOUSE_NORMAL);
	}

}
