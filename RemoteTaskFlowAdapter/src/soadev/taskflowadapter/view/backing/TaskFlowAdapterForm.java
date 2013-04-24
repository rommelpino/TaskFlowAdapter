package soadev.taskflowadapter.view.backing;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;

import java.net.MalformedURLException;
import java.net.URI;

import java.net.URISyntaxException;

import java.net.URL;
import java.net.URLEncoder;

import java.util.Map;

import javax.faces.context.FacesContext;

import oracle.adf.view.rich.component.rich.nav.RichCommandButton;
import oracle.adf.view.rich.component.rich.output.RichInlineFrame;
import oracle.adf.view.rich.context.AdfFacesContext;

import org.apache.myfaces.trinidad.util.ComponentReference;

public class TaskFlowAdapterForm implements Serializable {
    private static final long serialVersionUID = 1L;
    private ComponentReference<RichCommandButton> returnButton;
    private String remoteTaskFlowURL;
    private ComponentReference<RichInlineFrame> inlineFrame;

    public TaskFlowAdapterForm() {
        super();
    }

    public void setReturnButton(RichCommandButton returnButton) {
        if (this.returnButton == null) {
            this.returnButton =
                    ComponentReference.newUIComponentReference(returnButton);
        }
    }

    public RichCommandButton getReturnButton() {
        return returnButton == null? null: returnButton.getComponent();
    }

    public String getRemoteTaskFlowURL() throws Exception {
        if (remoteTaskFlowURL == null) {
            StringBuilder sb = new StringBuilder();
            sb.append("/faces/adf.task-flow?_id=RemoteWrapper&_document=WEB-INF/taskflows/soadev/RemoteWrapper.xml");
            sb.append("&returnTaskFlowButtonId=");
            sb.append(getReturnButton().getClientId(FacesContext.getCurrentInstance()));
            sb.append("&remoteAppURL=");
            String remoteAppURL =
                (String)getPageFlowScope().get("remoteAppURL");
            remoteAppURL = URLEncoder.encode(remoteAppURL, "UTF-8");
            sb.append(remoteAppURL);
            sb.append("&taskFlowId=");
            String taskFlowId = (String)getPageFlowScope().get("taskFlowId");
            taskFlowId = URLEncoder.encode(taskFlowId, "UTF-8");
            sb.append(taskFlowId);
            sb.append("&parameters=");
            sb.append(buildParameterString());
            remoteTaskFlowURL = sb.toString();
            System.out.println(remoteTaskFlowURL);
        }
        return remoteTaskFlowURL;
    }

    private String buildParameterString() {
        Map<String, Object> parameterMap =
            (Map<String, Object>)AdfFacesContext.getCurrentInstance().getPageFlowScope().get("parameterMap");
        if (parameterMap == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Object> entry : parameterMap.entrySet()) {
            sb.append(entry.getKey());
            sb.append(":");
            sb.append(entry.getValue());
            sb.append(";");
        }
        return sb.toString();
    }

    private Map<String, Object> getPageFlowScope() {
        return AdfFacesContext.getCurrentInstance().getPageFlowScope();
    }

    public void setInlineFrame(RichInlineFrame inlineFrame) {
        if(this.inlineFrame == null){
            this.inlineFrame = ComponentReference.newUIComponentReference(inlineFrame);
        }
    }

    public RichInlineFrame getInlineFrame() {
        return inlineFrame == null? null : inlineFrame.getComponent();
    }
}
