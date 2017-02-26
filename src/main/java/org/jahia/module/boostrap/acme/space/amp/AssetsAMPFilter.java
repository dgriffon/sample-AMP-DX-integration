package org.jahia.module.boostrap.acme.space.amp;

import org.apache.commons.lang.StringUtils;
import org.jahia.services.render.RenderContext;
import org.jahia.services.render.Resource;
import org.jahia.services.render.URLGenerator;
import org.jahia.services.render.filter.AbstractFilter;
import org.jahia.services.render.filter.RenderChain;

/**
 * Render Filter that cleans up the StaticAssetFilter for AMP rendering
 * it also adds links to amphtml / canonical in header section when necessary
 */
public class AssetsAMPFilter extends AbstractFilter{

    private final static String MARKER = "##LineToRemove##";
    @Override
    public String execute(String previousOut, RenderContext renderContext, Resource resource, RenderChain chain) throws Exception {
        String out = super.execute(previousOut, renderContext, resource, chain);

        // Build the amp links that will be used in the header
        URLGenerator url = renderContext.getURLGenerator();
        String resourceUrl = url.getServer() + url.getContext() + url.getBase() + resource.getNode().getPath();
        String ampLink = "<link rel=\"ampType\" href=\"" + resourceUrl + "TEMPLATE.html\" />";

        // Remove unsuported scripts and link lines added by the staticAssetFilter
        if (StringUtils.equals(resource.getTemplate(), "amp")) {
            String[] lines = out.split(System.getProperty("line.separator"));
            for (int i = 0; i < lines.length; i++) {
                if (StringUtils.contains(lines[i], "contextJsParameters")) {
                    lines[i] = lines[i - 1] = lines[i + 1] = MARKER;
                } else if (StringUtils.contains(lines[i], "<link id=\"staticAssetCSS")) {
                    lines[i] = MARKER;
                }
            }
            StringBuilder result = new StringBuilder();
            for (String s : lines) {
                if (!StringUtils.equals(s, MARKER)) {
                    result.append(s).append(System.getProperty("line.separator"));
                }
            }
            out =  result.toString();
            // Set the values for AMP Link
            ampLink = StringUtils.replace(ampLink, "ampType", "canonical");
            ampLink = StringUtils.replace(ampLink, "TEMPLATE", "");
        } else {
            // Set the values for AMP Link
            ampLink = StringUtils.replace(ampLink, "ampType", "amphtml");
            ampLink = StringUtils.replace(ampLink, "TEMPLATE", ".amp");
        }
        // Add amp link in the header section.
        out = StringUtils.replace(out, "</head>", ampLink + "\n</head>");
        return out;
    }
}
