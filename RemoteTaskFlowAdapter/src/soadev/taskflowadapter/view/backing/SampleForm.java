package soadev.taskflowadapter.view.backing;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;

import javax.faces.event.ActionEvent;

import soadev.view.utils.ADFUtils;
import soadev.view.utils.JSFUtils;


public class SampleForm implements Serializable{
    private static final long serialVersionUID = 1L;
    private static String taskflowId  = "/WEB-INF/taskflows/sample/sample-fragments-task-flow.xml#sample-fragments-task-flow";
    private static long index = 1L;
    public SampleForm() {
        super();
    }
    
    public Map<String, Object> getSampleParameterMap(){
        Map<String, Object> parameterMap = new HashMap<String, Object>();
        parameterMap.put("enableContextualEvents", true);
        parameterMap.put("companyId", 1L);
        return parameterMap;
    }

    public void openDetail(ActionEvent actionEvent) {
        Map<String, Object> parameterMap = getSampleParameterMap();
        parameterMap.put("companyId", index++);
        JSFUtils.setRequestAttribute("parameterMap", parameterMap);
        Map<String, Object> payload = new HashMap<String, Object>();
        payload.put("taskFlowId", taskflowId);
        payload.put("title", "More detail...");
        payload.put("parameterMap", parameterMap);
        ADFUtils.fireEvent("launchActivityEventProducer", payload);
    }
}
