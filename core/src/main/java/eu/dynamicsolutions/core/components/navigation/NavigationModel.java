package eu.dynamicsolutions.core.components.navigation;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.LinkedList;
import java.util.List;

@Model(adaptables = SlingHttpServletRequest.class)
public class NavigationModel {

    @Inject
    private PageManager pageManager;

    @ValueMapValue
    @Optional
    @Named("mainlink")
    private String mainLink;

    @ValueMapValue
    private String behavior;


    private List<Link> links = new LinkedList<>();

    @PostConstruct
    protected void init() {
        if ("sibling".equalsIgnoreCase(behavior)) {
            addLinks();
        }
    }

    private void addLinks() {
        Page page = pageManager.getContainingPage(mainLink);
        if (page != null) {
            Page parent = page.getParent();

            parent.listChildren().forEachRemaining(child ->
                    links.add(new Link(child.getName(), child.getPath() + ".html")));
        }
    }

    public List<Link> getLinks() {
        return links;
    }
}
