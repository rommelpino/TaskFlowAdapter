package soadev.taskflowadapter.view.helper;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;

import javax.faces.event.ActionEvent;

import oracle.adf.view.rich.component.rich.nav.RichCommandButton;
import oracle.adf.view.rich.context.AdfFacesContext;

import soadev.view.utils.JSFUtils;


public class TaskFlowAdapterHelper implements Serializable {
    private static final long serialVersionUID = 1L;

    public void launchActivity(String title, String taskFlowId,
                               Map<String, Object> params) {
        System.out.println("Launching activity");
        Map<String, Object> launchParameterMap = new HashMap<String, Object>();
        launchParameterMap.put("taskFlowId", taskFlowId);
        launchParameterMap.put("title", title);
        launchParameterMap.put("parameterMap", params);
        Map map = AdfFacesContext.getCurrentInstance().getPageFlowScope();
        map.put("launchParameterMap", launchParameterMap);
        RichCommandButton launcherButton = (RichCommandButton)JSFUtils.resolveExpression("#{viewScope.fragmentWrapper.launcherButton}");
        System.out.println("Launcher Button: " + launcherButton);
        assert launcherButton != null;
        ActionEvent event = new ActionEvent(launcherButton);
        event.queue();
    }
    
    public void produceEvent(){
        
    }
}
