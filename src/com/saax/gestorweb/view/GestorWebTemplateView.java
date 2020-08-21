/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saax.gestorweb.view;

/**
 *
 * @author Muthusankaranarayana
 */
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import com.saax.gestorweb.GestorMDI;
import java.util.ResourceBundle;
import com.saax.gestorweb.model.PostModel;
import com.saax.gestorweb.model.PostReferenceModel;
import com.saax.gestorweb.model.PostfollowerModel;
import com.saax.gestorweb.model.PoststatusModel;
import com.saax.gestorweb.model.PosttypeModel;
import com.saax.gestorweb.model.datamodel.Postfollower;
import com.saax.gestorweb.model.datamodel.Postreference;
import com.saax.gestorweb.model.datamodel.Poststatus;
import com.saax.gestorweb.model.datamodel.Posttype;
import com.saax.gestorweb.util.GestorWebImagens;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.Upload;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Upload.SucceededListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import java.util.logging.Level;
import java.util.logging.Logger;
import com.saax.gestorweb.model.datamodel.Post;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;

public class GestorWebTemplateView extends VerticalLayout {

    Post postDetails = new Post();
 
    // message resource
   
    private final transient ResourceBundle messages = ((GestorMDI) UI.getCurrent()).getMensagens();
    // image resource
    private final transient GestorWebImagens images = ((GestorMDI) UI.getCurrent()).getGestorWebImagens();

    // listener interface (presenter in MVP)
    private GestorWebTemplateViewListener listener;

    public void setListener(GestorWebTemplateViewListener listener) {
        this.listener = listener;
    }
   // message resource

 
    PostModel postModel = new PostModel();
    PostReferenceModel refModel = new PostReferenceModel();
    PosttypeModel typeModel = new PosttypeModel();
    PostfollowerModel follwerModel = new PostfollowerModel();

    ArrayList<String> allowedMimeTypes = new ArrayList<String>();

    public GestorWebTemplateView() {

        setSizeFull();

        final GridLayout layout = new GridLayout(15, 25);
	// Layout containing relatively sized components must have
        // a defined size, here is fixed size.
        // Create an empty tab sheet.

        Label nameTextLabel = new Label(messages.getString("GestorWebTemplateView.nameTextLabel.name"));
        layout.addComponent(nameTextLabel, 1, 3, 1, 3);
        final TextField nameText = new TextField();
        nameText.setSizeFull();
        // Display the current length interactively in the counter
        nameText.addTextChangeListener(new TextChangeListener() {
            public void textChange(TextChangeEvent event) {
                int len = event.getText().length();
               postDetails.setPostname(nameText.getValue());
            }
        });

        
        layout.addComponent(nameText, 4, 3, 11, 3);
 	List<Postreference> postRef = (List<Postreference>) refModel.findByALL();
	ArrayList referenceList = new ArrayList();
	for (Postreference resultElement : postRef) {
            referenceList.add(resultElement.getPostreference());
	}

        ArrayList followersList = new ArrayList();
        List<Postfollower> postFollower = (List<Postfollower>) follwerModel.findAll();
        for (Postfollower resultElement : postFollower) {
            followersList.add(resultElement.getPostfollowerId());
        }
        Label referen = new Label(messages.getString("GestorWebTemplateView.referen"));
        layout.addComponent(referen, 1, 5, 3, 5);
        ComboBox references = new ComboBox(null, referenceList);
        layout.addComponent(references, 4, 5, 5, 5);
        references.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(ValueChangeEvent event) {
                // Will display 'null selected' when nullPerson is selected.
                Notification.show(event.getProperty().getValue() + " selected");
                postDetails.getPostreference().setPostreference(event.getProperty().getValue().toString());
            }
        });

        List<Posttype> typeDet = (List<Posttype>)typeModel.findAll();
        ArrayList typeList = new ArrayList();
        for (Posttype resultElement : typeDet) {
            typeList.add(resultElement.getPosttype());
        }
   
        Label tipoLabel = new Label(messages.getString("GestorWebTemplateView.tipoLabel"));
        layout.addComponent(tipoLabel, 6, 5, 7, 5);
        final ComboBox tipo = new ComboBox(null, typeList);
 
        tipo.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(ValueChangeEvent event) {
                // Will display 'null selected' when nullPerson is selected.
                Notification.show(event.getProperty().getValue() + " selected");
                postDetails.getPosttype().setPosttype(tipo.getConvertedValue().toString());
            }
        });
        
        layout.addComponent(tipo, 8, 5, 9, 5);

        Button chat = new Button(messages.getString("GestorWebTemplateView.chat"));
        chat.addClickListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                Logger.getLogger(GestorWebTemplateView.class.getName()).log(Level.INFO, null, "The chat button clicked");
            }
        });
        layout.addComponent(chat, 11, 5, 11, 5);

        Label status = new Label(messages.getString("GestorWebTemplateView.status"));
        PoststatusModel statusModel = new PoststatusModel();
        layout.addComponent(status, 1, 6, 3, 6);
        List<Poststatus> statusData = statusModel.findAll();
        ArrayList statusList = new ArrayList();
        for (Poststatus resultElement : statusData) {
            statusList.add(resultElement.getPoststatus());
        }
        ComboBox statusVal = new ComboBox(null, statusList);
        layout.addComponent(statusVal, 4, 6, 5, 6);
        statusVal.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(ValueChangeEvent event) {
                // Will display 'null selected' when nullPerson is selected.
                postDetails.getPoststatus().setPoststatus(event.getProperty().toString());
                Notification.show(event.getProperty().getValue() + " selected");
            }
        });

        final ComboBox participants = new ComboBox(messages.getString("GestorWebTemplateView.participants"), followersList);
        layout.addComponent(participants, 1, 7, 3, 7);
        // Here we need the participants list
        participants.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(ValueChangeEvent event) {
                // Will display 'null selected' when nullPerson is selected.
                postDetails.getPostfollowers().add(Postfollower(participants.getData().toString()));
                Notification.show(event.getProperty().getValue() + " selected");
            }
        });


        ListSelect selectData = new ListSelect(messages.getString("GestorWebTemplateView.selectData"));
        selectData.addItems(followersList);
        selectData.setNullSelectionAllowed(false);
        selectData.setRows(5);
        selectData.setSizeFull();
        layout.addComponent(selectData, 1, 9, 3, 9);
        final TextArea areaContents = new TextArea();
        areaContents.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(ValueChangeEvent event) {
                // Will display 'null selected' when nullPerson is selected.
                postDetails.setDescription(areaContents.getValue());
                Notification.show(event.getProperty().getValue() + " selected");
            }
        });
        
        layout.addComponent(areaContents, 1, 11, 2, 11);
        Button addButton = new Button(messages.getString("GestorWebTemplateView.addButton"));
        layout.addComponent(addButton, 1, 13, 2, 13);

        TextField valueToCome = new TextField();
        layout.addComponent(valueToCome, 1, 15, 2, 15);
	// Create a date field with a custom parsing and a
        // custom error message for invalid format
        PopupDateField reunido = new PopupDateField(messages.getString("GestorWebTemplateView.reunido")) {
            @Override
            protected Date handleUnparsableDateString(String dateString) {
                // Try custom parsing
                String fields[] = dateString.split("/");
                GregorianCalendar c = null;
                if (fields.length >= 3) {
                    try {
                        int year = Integer.parseInt(fields[0]);
                        int month = Integer.parseInt(fields[1]) - 1;
                        int day = Integer.parseInt(fields[2]);
                        c = new GregorianCalendar(year, month, day);
                        postDetails.setRequiredmeeting(new Date(dateString));
                        return c.getTime();
                    } catch (NumberFormatException e) {
                        Logger.getLogger(GestorWebTemplateView.class.getName()).log(Level.SEVERE, null, e);
                        throw new RuntimeException(e);
                    }
                }
                return c.getTime();

            }
        };

        // Display only year, month, and day in slash-delimited format
        reunido.setDateFormat("yyyy/MM/dd");

		// Don't be too tight about the validity of dates
        // on the client-side
        reunido.setLenient(true);
        layout.addComponent(reunido, 1, 17, 2, 17);
		// Create a date field with a custom parsing and a
        // custom error message for invalid format
        PopupDateField lectur = new PopupDateField(messages.getString("GestorWebTemplateView.lectur")) {
            @Override
            protected Date handleUnparsableDateString(String dateString) {
                // Try custom parsing
                String fields[] = dateString.split("/");
                GregorianCalendar c = null;
                if (fields.length >= 3) {
                    try {
                        int year = Integer.parseInt(fields[0]);
                        int month = Integer.parseInt(fields[1]) - 1;
                        int day = Integer.parseInt(fields[2]);
                        postDetails.setDeadlinereaddate(new Date(dateString));
                        c = new GregorianCalendar(year, month, day);
                        return c.getTime();
                    } catch (NumberFormatException e) {
                        Logger.getLogger(GestorWebTemplateView.class.getName()).log(Level.SEVERE, null, e);
                        throw new RuntimeException(e);
                    }
                }
                return c.getTime();

            }
        };

        // Display only year, month, and day in slash-delimited format
        lectur.setDateFormat("yyyy/MM/dd");

        layout.addComponent(lectur, 1, 19, 2, 19);
        final TextArea decriptionContents = new TextArea(messages.getString("GestorWebTemplateView.decriptionContents"));
        decriptionContents.setSizeFull();
        layout.addComponent(decriptionContents,4, 7, 14, 19);
        // Here the description contents to be added
        
        postDetails.setDescription(decriptionContents.getValue().toString());
        Button gravar = new Button(messages.getString("GestorWebTemplateView.gravar"));
        Button editor = new Button(messages.getString("GestorWebTemplateView.editor"));
        gravar.addClickListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                // the gravar value to be passed
                postModel.addPost(postDetails);
                decriptionContents.focus();
                // committing will be done here
            }
        });
        editor.addClickListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                // the value of the editor to be passed here the functionality for the gravar to be implemented
            }
        });
        layout.addComponent(gravar, 5, 20, 6, 20);
        layout.addComponent(editor, 7, 20, 8, 20);

        // Create uploads directory
        File uploadDir = new File("C:/Attachments/456/");
        if (!uploadDir.exists() && !uploadDir.mkdir()) {
            layout.addComponent(new Label("ERROR: Could not create upload dir"));
        }

        // Implement both receiver that saves upload in a file and
        // listener for successful upload
        class ImageUploader implements Receiver, SucceededListener {

            private static final long serialVersionUID = -1276759102490466761L;

            public File file;

            public OutputStream receiveUpload(String filename, String mimeType) {
                // Create upload stream
                FileOutputStream fos = null; // Output stream to write to
                try {
                    // Open the file for writing.
                    file = new File("C:/Attachments/456/" + filename);
                    fos = new FileOutputStream(file);
                } catch (final java.io.FileNotFoundException e) {
                    Notification.show("Error", "\nFile Upload failed: ", Type.ERROR_MESSAGE);
                    return null;
                }
                return fos; // Return the output stream to write to
            }

            public void uploadSucceeded(SucceededEvent event) {
                // Show the uploaded file in the image viewer
                System.out.println(" Successfully uploaded the file");
            }
        };
        final ImageUploader uploader = new ImageUploader();
        Upload upload = new Upload(messages.getString("GestorWebTemplateView.upload"), uploader);
        upload.setReceiver(uploader);
        upload.addListener(uploader);
        layout.addComponent(upload, 4, 22, 14, 22);
        layout.setVisible(true);
        layout.setMargin(true);
        addComponent(layout);

    }

    private Postfollower Postfollower(String toString) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
