package org.licket.demo.view;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.licket.core.model.LicketComponentModel.ofModelObject;
import static org.licket.core.view.LicketComponentView.fromComponentClass;
import static org.licket.core.view.LicketComponentView.internalTemplateView;
import org.licket.core.module.application.LicketComponentModelReloader;
import org.licket.core.module.application.LicketRemote;
import org.licket.core.view.LicketComponentView;
import org.licket.core.view.form.AbstractLicketForm;
import org.licket.core.view.form.LicketInput;
import org.licket.core.view.link.LicketFormSubmitButton;
import org.licket.demo.model.Contact;
import org.licket.demo.service.ContactsService;

/**
 * @author activey
 */
public class AddContactForm extends AbstractLicketForm<Contact> {

    private final ContactsService contactsService;

    public AddContactForm(String id, ContactsService contactsService, LicketRemote remoteCommunication,
                          LicketComponentModelReloader modelReloader) {
        super(id, Contact.class, ofModelObject(new Contact()), internalTemplateView(), remoteCommunication, modelReloader);
        this.contactsService = checkNotNull(contactsService, "Contacts service has to be not null!");

        add(new LicketInput("name"));
        add(new LicketInput("description"));
        add(new LicketFormSubmitButton("add"));
    }

    @Override
    protected void onSubmit() {
        contactsService.addContact(getComponentModel().get());

        clearInput();
    }

    private void clearInput() {
        setComponentModelObject(new Contact());
    }
}
