package soadev.taskflowadapter.view.backing;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;

import oracle.adf.controller.ControllerContext;
import oracle.adf.controller.TaskFlowId;
import oracle.adf.controller.internal.metadata.NamedParameter;
import oracle.adf.controller.internal.metadata.TaskFlowInputParameter;
import oracle.adf.view.rich.component.rich.nav.RichCommandButton;
import oracle.adf.view.rich.context.AdfFacesContext;

import org.apache.myfaces.trinidad.util.ComponentReference;

import soadev.view.utils.TaskFlowUtils;


public class TaskFlowAdapterForm implements Serializable {
    private static final long serialVersionUID = 1L;
    private ComponentReference<RichCommandButton> returnButton;
    private String remoteTaskFlowURL;

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
        return returnButton == null ? null : returnButton.getComponent();
    }

    public String getRemoteTaskFlowURL() throws Exception {
        if (remoteTaskFlowURL == null) {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("returnTaskFlowButtonId",
                       getReturnButton().getClientId(FacesContext.getCurrentInstance()));
            params.putAll(getThisTaskFlowParameterMap());
            Map map = (Map)params.remove("parameterMap");
            params.put("parameters", buildParameterString(map));
            remoteTaskFlowURL =
                    ControllerContext.getInstance().getTaskFlowURL(false,
                                                                   TaskFlowId.parse("/WEB-INF/taskflows/soadev/RemoteWrapper.xml#RemoteWrapper"),
                                                                   params);
            remoteTaskFlowURL = "/"+remoteTaskFlowURL+"&_adf.ctrl-state=13sn8t8l0t_659";
            System.out.println(remoteTaskFlowURL);
        }
        return remoteTaskFlowURL;
    }

    public Map<String, Object> getThisTaskFlowParameterMap() {
        Map<String, Object> result = new HashMap<String, Object>();
        Map<String, TaskFlowInputParameter> inputParamsMap =
            TaskFlowUtils.getCurrentTaskFlowInputParameters();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Application application = facesContext.getApplication();
        for (TaskFlowInputParameter paramDef : inputParamsMap.values()) {
            NamedParameter namedParameter = (NamedParameter)paramDef;
            String paramName = namedParameter.getName();
            String paramExpression = namedParameter.getValueExpression();
            Object paramValue =
                application.evaluateExpressionGet(facesContext, paramExpression,
                                                  Object.class);
            result.put(paramName, paramValue);
        }
        return result;
    }

    private String buildParameterString(Map<String, Object> parameterMap) {
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
}
