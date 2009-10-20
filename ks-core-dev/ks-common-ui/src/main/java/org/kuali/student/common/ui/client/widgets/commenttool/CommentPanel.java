/*
 * Copyright 2009 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 * 
 * http://www.osedu.org/licenses/ECL-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.kuali.student.common.ui.client.widgets.commenttool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.application.ApplicationContext;
import org.kuali.student.common.ui.client.configurable.mvc.HasReferenceId;
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.ToolView;
import org.kuali.student.common.ui.client.images.KSImages;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.service.CommentRpcService;
import org.kuali.student.common.ui.client.service.CommentRpcServiceAsync;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSDropDown;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSRichEditor;
import org.kuali.student.common.ui.client.widgets.KSStyles;
import org.kuali.student.common.ui.client.widgets.buttongroups.ConfirmCancelGroup;
import org.kuali.student.common.ui.client.widgets.buttongroups.OkGroup;
import org.kuali.student.common.ui.client.widgets.buttongroups.ButtonEnumerations.ConfirmCancelEnum;
import org.kuali.student.common.ui.client.widgets.buttongroups.ButtonEnumerations.OkEnum;
import org.kuali.student.common.ui.client.widgets.layout.HorizontalBlockFlowPanel;
import org.kuali.student.common.ui.client.widgets.layout.VerticalFlowPanel;
import org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract;
import org.kuali.student.common.ui.client.widgets.list.ListItems;
import org.kuali.student.common.ui.client.widgets.list.SelectionChangeHandler;
import org.kuali.student.core.comment.dto.CommentInfo;
import org.kuali.student.core.comment.dto.CommentTypeInfo;
import org.kuali.student.core.dto.MetaInfo;
import org.kuali.student.core.dto.RichTextInfo;
import org.kuali.student.core.dto.StatusInfo;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dev.js.rhino.Context;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class CommentPanel extends ToolView implements HasReferenceId{

	final static ApplicationContext context = Application.getApplicationContext();
	
	private String referenceId;
	private String referenceTypeKey;
	private String referenceType;
	private String referenceState;
    
	private CommentRpcServiceAsync commentServiceAsync = GWT.create(CommentRpcService.class);
	private static final String ALL = "All";
	private static final String ALL_DESC = "All Comments";
	private static final String ALL_NAME = "All";
    private VerticalFlowPanel layout = new VerticalFlowPanel();
    private VerticalFlowPanel commentList = new VerticalFlowPanel();
    private SectionTitle headerTitle = SectionTitle.generateH1Title("Proposal Comments");
    private SectionTitle createTitle = SectionTitle.generateH2Title("Leave a Comment");
    private VerticalFlowPanel createPanel = new VerticalFlowPanel();
    private SimplePanel commentEditorPanel = new SimplePanel();
    private KSRichEditor commentEditor = new KSRichEditor();
    private List<Comment> comments = new ArrayList<Comment>();
    private KSLabel loggedInAs = new KSLabel();
    private HorizontalBlockFlowPanel commentTypesPanel = new HorizontalBlockFlowPanel();
    private KSLabel seeComments = new KSLabel("See Comments from ");
    private KSLabel loadingComments = new KSLabel("Loading Comments");
    private KSDropDown commentTypes = new KSDropDown();
    private boolean showingEditButtons = true;
    private List<String> types = new ArrayList<String>();
    
    private Comparator<CommentInfo> commentInfoComparator = new Comparator<CommentInfo>(){

		@Override
		public int compare(CommentInfo comment1, CommentInfo comment2) {
			
			if(comment1.getMetaInfo().getCreateTime().after(comment2.getMetaInfo().getCreateTime())){
				return -1;
			}
			else if(comment1.getMetaInfo().getCreateTime().before(comment2.getMetaInfo().getCreateTime())){
				return 1;
			}
			else{
				//equal
				return 0;
			}
			
		}
    };
    
    private static DateTimeFormat formatter = DateTimeFormat.getMediumDateTimeFormat();
    
    private OkGroup buttonPanel = new OkGroup(new Callback<OkEnum>(){

        @Override
        public void exec(OkEnum result) {
            if(result == OkEnum.Ok){
                CommentInfo newComment = new CommentInfo();
                
                RichTextInfo text = new RichTextInfo();
                text.setFormatted(commentEditor.getHTML());
                text.setPlain(commentEditor.getText());
                newComment.setCommentText(text);
                newComment.setType("commentType." + referenceType + "." + referenceState);

                try {
					commentServiceAsync.addComment(referenceId, referenceTypeKey, newComment, new AsyncCallback<CommentInfo>(){

						@Override
						public void onFailure(Throwable caught) {
							caught.printStackTrace();
							
						}

						@Override
						public void onSuccess(CommentInfo result) {
							if(!(types.contains(result.getType()))){
								types.add(result.getType());
							}
							commentTypes.redraw();
							Comment comment = new Comment(result);
					        comments.add(0, comment);
					        commentList.insert(comment, 0);
					        commentEditor.setHTML("");
						}
					});
				} catch (Exception e) {
					e.printStackTrace();
				}

            }
            
        }
    });
    
    public CommentPanel(Enum<?> viewEnum, String viewName) {
		super(viewEnum, viewName);
	}
    
    @Override
    public void setReferenceId(String id) {
        this.referenceId = id;
    }
    
    @Override
    public String getReferenceId() {
        return referenceId;
    }
    
    @Override
	public String getReferenceTypeKey() {
		return referenceTypeKey;
	}

    @Override
	public void setReferenceTypeKey(String referenceTypeKey) {
		this.referenceTypeKey = referenceTypeKey;
	}

    @Override
	public String getReferenceType() {
		return referenceType;
	}

    @Override
	public void setReferenceType(String referenceType) {
		this.referenceType = referenceType;
	}

    @Override
	public String getReferenceState() {
		return referenceState;
	}

    @Override
	public void setReferenceState(String referenceState) {
		this.referenceState = referenceState;
	}
    
      
    @Override
    protected Widget createWidget() {
    	commentTypes.setBlankFirstItem(false);
    	commentTypes.setListItems(new ListItems(){

			@Override
			public List<String> getAttrKeys() {
				List<String> keys = new ArrayList<String>();
				/*keys.add("desc");
				keys.add("name");*/
				return keys;
			}

			@Override
			public String getItemAttribute(String id, String attrkey) {
				
				String result = id;
				/*String result = null;
				if(id.equals(ALL)){
					if(attrkey.equalsIgnoreCase("desc")){
						result = ALL_DESC;
					}
					else if(attrkey.equalsIgnoreCase("name")){
						result = ALL_NAME;
					}
				}
				else{
					CommentTypeInfo type = null;
					for(CommentTypeInfo i: types){
						if(i.getId().equals(id)){
							type = i;
							break;
						}
					}
					
					if(type != null){
						if(attrkey.equalsIgnoreCase("desc")){
							result = type.getDesc();
						}
						else if(attrkey.equalsIgnoreCase("name")){
							result = type.getName();
						}
					}
				}*/
				return result;
			}

			@Override
			public int getItemCount() {
				return types.size();
			}

			@Override
			public List<String> getItemIds() {
				List<String> ids = new ArrayList<String>();
				ids.add(ALL);
				for(String i: types){
					ids.add(i);
				}
				
				return ids;
			}

			@Override
			public String getItemText(String id) {
				String result = null;
				if(id.equals(ALL)){
					result = context.getMessage("allComments");
				}
				else{
					id = id.replace("commentType.", "");
					result = context.getMessage(id);
				}
				return result;
			}
    	});
    	
    	
    	commentTypes.selectItem(ALL);
    	commentTypes.addSelectionChangeHandler(new SelectionChangeHandler(){

			@Override
			public void onSelectionChange(KSSelectItemWidgetAbstract w) {
				refreshComments();
				
			}
    	});
    	
        buttonPanel.setButtonText(OkEnum.Ok, "Submit");
        //FIXME: get person logged in as
        loggedInAs.setText("PersonId Here");
        
        loggedInAs.addStyleName(KSStyles.KS_COMMENT_LOGIN_USER);
        createPanel.addStyleName(KSStyles.KS_COMMENT_CREATE_PANEL);
        commentEditor.addStyleName(KSStyles.KS_COMMENT_CREATE_EDITOR);
        
        layout.add(headerTitle);
        createPanel.add(createTitle);
        commentEditorPanel.setWidget(commentEditor);
        createPanel.add(commentEditorPanel);
        createPanel.add(loggedInAs);
        createPanel.add(buttonPanel);
        
        commentTypesPanel.add(seeComments);
        commentTypesPanel.add(commentTypes);
        createPanel.add(commentTypesPanel);
        
        layout.add(createPanel);
        layout.add(commentList);
        //refreshCommentTypes();
        return layout;
    }
    
    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        if(referenceId != null && !(referenceId.isEmpty())){
			buttonPanel.getButton(OkEnum.Ok).setEnabled(true);
		}
		else{
			buttonPanel.getButton(OkEnum.Ok).setEnabled(false);
		}
        refreshComments();
    }
    
/*   public void refreshCommentTypes(){
    	
    	
    	
    	try {
			commentServiceAsync.getCommentTypesForReferenceType(referenceTypeKey,  new AsyncCallback<List<CommentTypeInfo>>(){

				@Override
				public void onFailure(Throwable caught) {
					caught.printStackTrace();
					
				}

				@Override
				public void onSuccess(List<CommentTypeInfo> result) {
					types = result;
					commentTypes.redraw();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
    }*/
    
    
    public void refreshComments(){
        commentList.clear();
        commentList.add(loadingComments);
        //rpc call to get all current comments and populate into comment list
    	try {
    		if(commentTypes.getSelectedItem().equals(ALL)){
				commentServiceAsync.getComments(referenceId, referenceTypeKey, new AsyncCallback<List<CommentInfo>>(){
	
						@Override
						public void onFailure(Throwable caught) {
							caught.printStackTrace();
							commentList.remove(loadingComments);
						}
		
						@Override
						public void onSuccess(List<CommentInfo> result) {
							if(result != null){
								comments.clear();
								types.clear();
								Collections.sort(result, commentInfoComparator);
								for(CommentInfo c: result){
									if(!(types.contains(c.getType()))){
										types.add(c.getType());
									}
									
									Comment commentWidget = new Comment(c);
									comments.add(commentWidget);
									commentList.add(commentWidget);
								}
								commentTypes.redraw();
							}
							
							commentList.remove(loadingComments);
							//refreshCommentTypes();
						}
					});
	    		}
    		else{
    			//must be showing specific comment type
				commentServiceAsync.getCommentsByType(referenceId, referenceTypeKey, commentTypes.getSelectedItem(), new AsyncCallback<List<CommentInfo>>(){
					
					@Override
					public void onFailure(Throwable caught) {
						caught.printStackTrace();
						commentList.remove(loadingComments);
					}
	
					@Override
					public void onSuccess(List<CommentInfo> result) {
						if(result != null){
							comments.clear();
							types.clear();
							Collections.sort(result, commentInfoComparator);
							for(CommentInfo c: result){
								if(!(types.contains(c.getType()))){
									types.add(c.getType());
								}
								
								Comment commentWidget = new Comment(c);
								comments.add(commentWidget);
								commentList.add(commentWidget);
							}
							commentTypes.redraw();
						}
						
						commentList.remove(loadingComments);
						//refreshCommentTypes();
					}
				});

    		}
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        
    }
    
    private void showCommentsEditActions(boolean show){
    	if(show){
    		//FIXME do a check to see if the current person can edit for each comment in the list then show the actions, else
    		//dont show them
    		if(!showingEditButtons){
	    		createPanel.setVisible(show);
	    		commentEditorPanel.setWidget(commentEditor);
	    		for(Comment c: comments){
	        		c.showEditActions(show);
	        	}
	    		showingEditButtons = true;
    		}
    	}
    	else{
    		if(showingEditButtons){
	    		createPanel.setVisible(show);
	    		commentEditorPanel.remove(commentEditor);
	    		
	    		for(Comment c: comments){
	        		c.showEditActions(show);
	        	}
	    		showingEditButtons = false;
    		}
    	}
    	
    }
    
    private KSRichEditor editor = new KSRichEditor();
    
    private class Comment extends Composite{
        
        private SimplePanel content = new SimplePanel();
        private VerticalFlowPanel editLayout = new VerticalFlowPanel();
        private VerticalFlowPanel viewLayout = new VerticalFlowPanel();
        private HorizontalBlockFlowPanel header = new HorizontalBlockFlowPanel();
        private HorizontalBlockFlowPanel headerTextContainer = new HorizontalBlockFlowPanel();
        private HorizontalBlockFlowPanel footer = new HorizontalBlockFlowPanel();
        private HorizontalBlockFlowPanel editActions = new HorizontalBlockFlowPanel();
        
        Image edit = KSImages.INSTANCE.editComment().createImage();
        Image delete = KSImages.INSTANCE.deleteComment().createImage();
        
        private KSLabel name = new KSLabel();
        private HTML commentText = new HTML();
        private KSLabel datePosted = new KSLabel();
        private KSLabel dateModified = new KSLabel();
        
        private CommentInfo info;
        
        public Comment(CommentInfo info){
            this.info = info;
            edit.addClickHandler(new ClickHandler(){
                @Override
                public void onClick(ClickEvent event) {
                	editLayout.clear();
                    //editActions.setVisible(false);
                	showCommentsEditActions(false);
                    content.addStyleName(KSStyles.KS_COMMENT_CONTAINER_IN_USE);
                    editor.setHTML(Comment.this.info.getCommentText().getFormatted());
                    
                    ConfirmCancelGroup buttonPanel = new ConfirmCancelGroup(new Callback<ConfirmCancelEnum>(){
                        @Override
                        public void exec(ConfirmCancelEnum result) {
                            switch(result){
                                case CONFIRM:
                                	
                                    Comment.this.info.getCommentText().setFormatted(CommentPanel.this.editor.getHTML());
                                    Comment.this.info.getCommentText().setPlain(CommentPanel.this.editor.getText());
                                    
                                	try {
										commentServiceAsync.updateComment(referenceId, referenceTypeKey, Comment.this.info, new AsyncCallback<CommentInfo>(){

											@Override
											public void onFailure(Throwable caught) {
												caught.printStackTrace();
												
											}

											@Override
											public void onSuccess(CommentInfo result) {
												Comment.this.info = result;
												content.removeStyleName(KSStyles.KS_COMMENT_CONTAINER_IN_USE);
												editor.setText("");
												editor.removeFromParent();
			                                    setupViewLayout();
											}
											
										});
									} catch (Exception e) {
										e.printStackTrace();
									}
                                	
                                    break;
                                case CANCEL:
                                	content.removeStyleName(KSStyles.KS_COMMENT_CONTAINER_IN_USE);
                                	editor.setText("");
                                	editor.removeFromParent();
                                    setupViewLayout();
                                    break;
                            }
                            
                        }
                    });
                    
                    buttonPanel.addStyleName(KSStyles.KS_COMMENT_INLINE_EDIT);
                    buttonPanel.setContent(editor);
                    editLayout.add(header);
                    editLayout.add(buttonPanel);
                    content.setWidget(editLayout);
                }
            });
            
            delete.addClickHandler(new ClickHandler(){
                @Override
                public void onClick(ClickEvent event) {
                	try {
						commentServiceAsync.removeComment(Comment.this.info.getId(), referenceId, referenceTypeKey, new AsyncCallback<StatusInfo>(){

							@Override
							public void onFailure(Throwable caught) {
								caught.printStackTrace();
								
							}

							@Override
							public void onSuccess(StatusInfo result) {
								if(result.getSuccess()){
									Window.alert("Your comment was deleted successfully");
								}
								
								refreshComments();
								
							}
							
						});
					} catch (Exception e) {
						e.printStackTrace();
					}
                }
            });
            
            setupDefaultStyles();
            headerTextContainer.add(name);
            headerTextContainer.add(datePosted);
            editActions.add(edit);
            editActions.add(delete);
            header.add(headerTextContainer);
            header.add(editActions);
            footer.add(dateModified);
            setupViewLayout();

            this.initWidget(content);
        }
        
        private void setupDefaultStyles(){
        	content.addStyleName(KSStyles.KS_COMMENT_CONTAINER);
        	header.addStyleName(KSStyles.KS_COMMENT_HEADER);
        	headerTextContainer.addStyleName(KSStyles.KS_COMMENT_HEADER_LEFT);
        	footer.addStyleName(KSStyles.KS_COMMENT_FOOTER);
        	edit.addStyleName(KSStyles.KS_COMMENT_IMAGE_BUTTON);
        	delete.addStyleName(KSStyles.KS_COMMENT_IMAGE_BUTTON);
        	editActions.addStyleName(KSStyles.KS_COMMENT_IMAGE_BUTTON_PANEL);
        	name.addStyleName(KSStyles.KS_COMMENT_NAME);
        	commentText.addStyleName(KSStyles.KS_COMMENT_TEXT);
        	datePosted.addStyleName(KSStyles.KS_COMMENT_DATE_CREATED);
        	dateModified.addStyleName(KSStyles.KS_COMMENT_DATE_MODIFIED);
        	editor.setStyleName(KSStyles.KS_COMMENT_INLINE_EDIT_EDITOR);
        }
        
        private void setupViewLayout(){
            
            viewLayout.clear();
            viewLayout.add(header);
            viewLayout.add(commentText);
            viewLayout.add(footer);  
            
            //FIXME: this will actually call the person service to get person info
            name.setText(info.getMetaInfo().getCreateId());
            
            if(info.getCommentText() != null){
                commentText.setHTML(info.getCommentText().getFormatted());
            }
            if(info.getMetaInfo().getCreateTime() != null){
                datePosted.setText(" " + formatter.format(info.getMetaInfo().getCreateTime()));
            }
            if(info.getMetaInfo().getUpdateTime() != null && !(info.getMetaInfo().getUpdateTime().equals(info.getMetaInfo().getCreateTime()))){
                dateModified.setText("Last Modified: " + formatter.format(info.getMetaInfo().getUpdateTime()));
            }
            
            content.setWidget(viewLayout);
            
            
            showCommentsEditActions(true);
        }
        
        public void showEditActions(boolean show){
        	editActions.setVisible(show);
        }
        
    }
}
