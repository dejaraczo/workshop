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


        while (page.getContentResource() != null && page.getContentResource().getChild(nodeName) == null && page.getParent() != null) {
            page = page.getParent();
        }

        Resource content = page.getContentResource().getChild(nodeName);
        if (content != null) {
            path = content.getPath();
        }
    }

    public String getPath() {
        return path;
    }
}
