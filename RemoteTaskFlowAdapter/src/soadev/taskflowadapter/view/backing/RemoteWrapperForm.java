package soadev.taskflowadapter.view.backing;

import java.io.Serializable;

import javax.faces.context.FacesContext;

import oracle.adf.view.rich.context.AdfFacesContext;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;


public class RemoteWrapperForm implements Serializable {
    private static final long serialVersionUID = 1L;

    public String returnTaskFlow() {
        String returnTaskFlowButtonId =
            (String)AdfFacesContext.getCurrentInstance().getPageFlowScope().get("returnTaskFlowButtonId");
        System.out.println("Returning: " + returnTaskFlowButtonId);
        String refreshScript =
            "function returnTaskFlow() { this.parent.submitButton('" +
            returnTaskFlowButtonId + "');} returnTaskFlow();";
        FacesContext fc = FacesContext.getCurrentInstance();
        ExtendedRenderKitService service =
            Service.getRenderKitService(fc, ExtendedRenderKitService.class);
        service.addScript(fc, refreshScript);
        return "success";
    }

}
