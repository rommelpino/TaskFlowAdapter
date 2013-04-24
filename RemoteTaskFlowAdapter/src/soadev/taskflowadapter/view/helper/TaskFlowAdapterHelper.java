package soadev.taskflowadapter.view.helper;

import java.io.Serializable;

import java.util.Map;

import oracle.adf.view.rich.context.AdfFacesContext;

import soadev.view.utils.JSFUtils;

public class TaskFlowAdapterHelper implements Serializable {
    private static final long serialVersionUID = 1L;

    public void launchActivity(String title, String taskFlowId,
                               Map<String, Object> parameterMap) {
        Map map = AdfFacesContext.getCurrentInstance().getPageFlowScope();
        map.put("title", title);
        map.put("launchTaskFlowId", taskFlowId);
        map.put("launchParameterMap", parameterMap);
        JSFUtils.handleNavigation(null, "launchActivity");
    }
}
