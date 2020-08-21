package com.example.gestorwebtemplatework;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.servlet.annotation.WebServlet;

import com.vaadin.ui.Upload.FailedListener;
import com.vaadin.ui.Upload.FinishedListener;
import com.vaadin.ui.Upload.ProgressListener;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.StartedListener;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.client.ui.formlayout.FormLayoutConnector;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.event.FieldEvents.FocusEvent;
import com.vaadin.event.FieldEvents.FocusListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.StartedEvent;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.client.ui.layout.Margins;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.event.Action;
import com.vaadin.event.Action.Handler;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.*;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Upload.SucceededListener;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;
import java.io.*;

import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
@Theme("gestorwebtemplatework")
public class GestorwebtemplateworkUI extends UI {
	
	ArrayList<String> allowedMimeTypes = new ArrayList<String>();

	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = GestorwebtemplateworkUI.class)
	public static class Servlet extends VaadinServlet {
	}

	@Override
	protected void init(VaadinRequest request) {
		
		allowedMimeTypes.add("image/jpeg");
		allowedMimeTypes.add("image/png");
		
		// Create an empty tab sheet.
		TabSheet tabsheet = new TabSheet();
		tabsheet.addStyleName(ValoTheme.TABSHEET_FRAMED);
		// Create a component to put in a tab and put
		// some content in it.
		final VerticalLayout myTabRoot = new VerticalLayout();

		// Add the component to the tab sheet as a new tab.
		tabsheet.addTab(myTabRoot);


		// Get the Tab holding the component and set its caption.
		tabsheet.getTab(myTabRoot).setCaption("Publications");
		
		//this will be used for adding further components
		//myTabRoot.addComponent();
		tabsheet.addFocusListener(new FocusListener() {
			@Override
			public void focus(FocusEvent event) {
				 myTabRoot.addComponent(new Label("Thank you for click in the tab"));				
			}
		});
		
		
		
		GridLayout layout = new GridLayout (15,25);
		// Layout containing relatively sized components must have
		// a defined size, here is fixed size.
		// Create an empty tab sheet.
		layout.addComponent(tabsheet,0,0,3,0);
		
		Label nameTextLabel = new Label("Nome");
		layout.addComponent(nameTextLabel,1,3,1,3);	
		TextField nameText = new TextField();
		nameText.setSizeFull();
		layout.addComponent(nameText,4,3,11,3);
		ArrayList referenceList = new ArrayList();
		referenceList.add("Dimitrios");
		referenceList.add("James");
		referenceList.add("Hari");
		referenceList.add("Sony");
		ArrayList participantsList = new ArrayList();
		participantsList.add("K1");
		participantsList.add("K2");
		participantsList.add("K3");
		Label referen = new Label("Referencia");
		layout.addComponent(referen,1,5,3,5);	
		ComboBox references = new ComboBox(null, referenceList);
		layout.addComponent(references,4,5,5,5);		
		references.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(ValueChangeEvent event) {
                // Will display 'null selected' when nullPerson is selected.
                Notification.show(event.getProperty().getValue() + " selected");
            }
        });

		Label tipoLabel = new Label("Tipo");
		layout.addComponent(tipoLabel,6,5,7,5);	
		ComboBox tipo = new ComboBox(null, referenceList);
		layout.addComponent(tipo,8,5,9,5);			
		
		Button button = new Button("Chat");
		button.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				layout.addComponent(new Label("Thank you for clicking"));
			}
		});
		layout.addComponent(button,11,5,11,5);
			
		ComboBox participants = new ComboBox("Participantes", participantsList);	
		layout.addComponent(participants,1,7,3,7);
		
		ListSelect selectData = new ListSelect("Multiple Participants");
		selectData.addItem("Participant1");
		selectData.addItem("Participant2");
		selectData.addItem("Participant3");
		selectData.addItem("Participant4");
		selectData.addItem("Participant5");
		selectData.addItem("Participant6");
		selectData.addItem("Participant7");

		selectData.setNullSelectionAllowed(false);
		selectData.setRows(5);
		selectData.setSizeFull();
		layout.addComponent(selectData,1,9,3,9);
		TextArea areaContents = new TextArea();
		layout.addComponent(areaContents,1,11,2,11);
		Button addButton = new Button("Additioner");
		layout.addComponent(addButton,1,13,2,13);
		
		TextField valueToCome = new TextField();
		layout.addComponent(valueToCome,1,15,2,15);
		// Create a date field with a custom parsing and a
		// custom error message for invalid format
		PopupDateField reunido = new PopupDateField("Suggestio Reunido") {
		    @Override
		    protected Date handleUnparsableDateString(String dateString)
		    {
		        // Try custom parsing
		        String fields[] = dateString.split("/");
		        GregorianCalendar c = null;
		        if (fields.length >= 3) {
		            try {
		                int year  = Integer.parseInt(fields[0]);
		                int month = Integer.parseInt(fields[1])-1;
		                int day   = Integer.parseInt(fields[2]);
		                c = new GregorianCalendar(year, month, day);
		                return c.getTime();
		            } catch (NumberFormatException e) {
		                e.getStackTrace().toString();
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
		layout.addComponent(reunido,1,17,2,17);
		// Create a date field with a custom parsing and a
		// custom error message for invalid format
		PopupDateField lectur = new PopupDateField("Confirmationo De Lectura") {
		    @Override
		    protected Date handleUnparsableDateString(String dateString)
		    {
		        // Try custom parsing
		        String fields[] = dateString.split("/");
		        GregorianCalendar c = null;
		        if (fields.length >= 3) {
		            try {
		                int year  = Integer.parseInt(fields[0]);
		                int month = Integer.parseInt(fields[1])-1;
		                int day   = Integer.parseInt(fields[2]);
		                c = new GregorianCalendar(year, month, day);
		                return c.getTime();
		            } catch (NumberFormatException e) {
		                e.getStackTrace().toString();
		            }
		        }
				return c.getTime();

		    }
		};
		        
		// Display only year, month, and day in slash-delimited format
		lectur.setDateFormat("yyyy/MM/dd");		

		layout.addComponent(lectur,1,19,2,19);
		

		layout.addComponent(new Button("Gravar"), 5, 20, 6, 20);
		layout.addComponent(new Button("Editar"), 7, 20, 8,20);
       
        // Create uploads directory
        File uploadDir = new File("C:/Attachments/456/");
        if (!uploadDir.exists() && !uploadDir.mkdir())
            layout.addComponent(new Label("ERROR: Could not create upload dir"));
        
        
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
        Upload upload = new Upload("Anexos relacionados (attach files):",uploader);
        upload.setReceiver(uploader);
        upload.addListener(uploader);
        layout.addComponent(upload,5,22,11,22);
		TextArea decriptionContents = new TextArea("Descriptiono");
		decriptionContents.setSizeFull();
		layout.addComponent(decriptionContents, 4, 6, 14, 19);

        layout.setVisible(true);
        layout.setMargin(true);
		setContent(layout);
		
		
	}

}