package org.licket.core.view.list;

import static java.lang.String.format;
import static org.licket.core.view.ComponentContainerView.internal;
import org.licket.core.model.LicketModel;
import org.licket.core.view.LicketComponent;
import org.licket.core.view.container.AbstractLicketContainer;
import org.licket.core.view.render.ComponentRenderingContext;

import java.util.Optional;

public abstract class AbstractLicketList<T> extends AbstractLicketContainer<String> {

    private Class<T> elementClass;

    public AbstractLicketList(String id, LicketModel<String> enclosingComponentPropertyModel, Class<T> elementClass) {
        super(id, internal(), enclosingComponentPropertyModel);
        this.elementClass = elementClass;
        // TODO analyze element class provided and check its properties against passed enclosingComponentPropertyModel
    }

    @Override
    protected final void onRenderContainer(ComponentRenderingContext renderingContext) {
        Optional<LicketComponent<?>> parent = traverseUp(component -> component instanceof AbstractLicketContainer);
        if (!parent.isPresent()) {
            return;
        }
        AbstractLicketContainer parentContainer = (AbstractLicketContainer) parent.get();
        renderingContext.onSurfaceElement(element -> {
            // TODO refactor
            String firstPart = "model";
            if (!parentContainer.getComponentContainerView().isExternalized()) {
                firstPart = parentContainer.getId();
            }
            element.addAttribute("*ngFor",
                    format("let %s of %s.%s", getId(), firstPart, getComponentModel().get()));
        });
    }
}
