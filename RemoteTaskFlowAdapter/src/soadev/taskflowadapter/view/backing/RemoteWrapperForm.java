package soadev.taskflowadapter.view.backing;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import java.net.URLDecoder;
import java.net.URLEncoder;

import java.util.Map;

import javax.annotation.PostConstruct;

import javax.faces.context.FacesContext;

import oracle.adf.controller.TaskFlowId;
import oracle.adf.view.rich.component.rich.nav.RichCommandButton;

import oracle.adf.view.rich.context.AdfFacesContext;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;


public class RemoteWrapperForm implements Serializable {
    private static final long serialVersionUID = 1L;
    private String remoteAppURL;

    public String returnTaskFlow() {
        String returnTaskFlowButtonId =
            (String)AdfFacesContext.getCurrentInstance().getPageFlowScope().get("returnTaskFlowButtonId");
        System.out.println("Returning: " + returnTaskFlowButtonId);
        String refreshScript =
            "function returnTaskFlow() { this.parent.submitButton('" +
            returnTaskFlowButtonId + "');} returnTaskFlow();";
        FacesContext fc = FacesContext.getCurrentInstance();
        ExtendedRenderKitService service =
            (ExtendedRenderKitService)Service.getRenderKitService(fc,
                                                                  ExtendedRenderKitService.class);
        service.addScript(fc, refreshScript);
        return "success";
    }

    private Map<String, Object> getPageFlowScope() {
        return AdfFacesContext.getCurrentInstance().getPageFlowScope();
    }

    public String getRemoteAppURL() throws Exception {
        if (remoteAppURL == null) {
            String remoteAppURL =
                (String)getPageFlowScope().get("remoteAppURL");
            remoteAppURL = URLDecoder.decode(remoteAppURL, "UTF-8");
        }
        return remoteAppURL;
    }

}
