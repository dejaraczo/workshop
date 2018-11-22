package eu.dynamicsolutions.core.components.inherited;

import com.day.cq.wcm.api.Page;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

@Model(adaptables = SlingHttpServletRequest.class)
public class InheritedModel {

    @Inject
    private Page currentPage;

    @Inject
    private String nodeName;

    private String path;

    @PostConstruct
    protected void init() {
        findNodePath();
    }

    private void findNodePath() {
        Page page = currentPage;
        Resource inheritedResource = null;
        while (inheritedResource == null && page.getParent() != null) {
            Resource content = page.getContentResource();
            if (content != null) {
                inheritedResource = page.getContentResource().getChild(nodeName);
            }
            page = page.getParent();
        }
        if (inheritedResource != null) {
            path = inheritedResource.getPath();
        }
    }

    public String getPath() {
        return path;
    }

    public String getNodeName() {
        return nodeName;
    }
}
